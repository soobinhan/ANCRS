import com.google.gson.Gson;
import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CommandLine.Command(name = "emitter", version = "v0.1b",
footer = "Copyright(c) 2018", description =
        "Simulate news article from collection/scrapers. Emits news articles " +
        "to the NewsAlerts service to collect and analyze.")
public class Main implements Runnable{

    @CommandLine.Option(names = { "--help" }, usageHelp = true,
            description = "Print a synopsis of the options.")
    private static boolean helpRequested = false;

    @CommandLine.Option(names = {"-apikey" }, description =
            "Specify NewsApi api key.")
    private static String API_KEY = "09118fbd443842d89c546b9cff5fb687";

    @CommandLine.Option(names = {"-interval" }, description =
            "Specify article emission interval.")
    private static int POLL_INTERVAL = 10000; // in ms

    @CommandLine.Option(names = {"-D" }, description =
            "Specify if the receiver is deployed on GCP.")
    private static boolean deployed = false;

    private static final NewsApi newsApi = new NewsApi(API_KEY);

    private static final CloseableHttpClient httpClient =
            HttpClients.createDefault();

    private static final String receiverLocalUrl =
            "http://localhost:8080/_ah/api/newsAlerts/v1/upload/article/";

    private static final String receiverDeployedUrl =
            "http://ame>ancrs-202523.appspot.com/" +
                    "_ah/api/newsAlerts/v1/upload/article/";

    private static final Gson gson = new Gson();

    private static List<Article> poll() throws InterruptedException {
        System.out.println("[poll] polling news articles...");
        RequestBuilder rb = new RequestBuilder();
        rb.setFrom(new Date(System.currentTimeMillis() - POLL_INTERVAL));
        rb.setTo(new Date());
        rb.setLanguage("en");
        ApiArticlesResponse resp = newsApi.sendTopRequest(rb);
        if (!resp.status().equals("ok")){
            throw new RuntimeException("failed poll request");
        }
        return resp.articles();
    }

    private static StatusLine sendArticles() throws Exception {
        System.out.println("[send] sending articles...");
        List<Article> recentArticles = poll();
        HttpPost httppost = deployed ? new HttpPost(receiverDeployedUrl) :
                new HttpPost(receiverLocalUrl);
        String jsonString = gson.toJson(recentArticles);
        StringEntity entity = new StringEntity(jsonString);
        httppost.setEntity(entity);
        HttpResponse response = httpClient.execute(httppost);
        return response.getStatusLine();
    }

    @Override
    public void run() {
        while (true){
            try {
//                poll();
                StatusLine resp = sendArticles();
                if (resp.getStatusCode() != 0){
                    System.out.println("Terminating...");
                    System.out.println(resp);
                    break;
                }
                Thread.sleep(POLL_INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CommandLine.run(new Main(), System.out, CommandLine.Help.Ansi.OFF, args);
    }
}
