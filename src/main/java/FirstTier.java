import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.datamodels.Source;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import io.github.ccincharge.newsapi.responses.ApiSourcesResponse;

import java.util.Date;
import java.util.List;

import static spark.Spark.get;

/**
 * first tier API
 *
 * GET /news get the latest and hottest news
 * GET /sources get the list of sources we draw from
 */
public class FirstTier {

    private static final String API_KEY = "09118fbd443842d89c546b9cff5fb687";
    private static final NewsApi newsApi = new NewsApi(API_KEY);
    private static final int POLL_INTERVAL = 10000; // in ms

    /**
     * Get the new news articles in the past minute
     *
     * run this in a new thread.
     * @return
     * @throws InterruptedException
     */
    public static List<Article> poll() throws InterruptedException {
        System.out.println("polling news articles");
        RequestBuilder rb = new RequestBuilder();
        rb.setFrom(new Date(System.currentTimeMillis() - POLL_INTERVAL));
        rb.setTo(new Date());
        rb.setLanguage("en");
        ApiArticlesResponse resp = newsApi.sendTopRequest(rb);
        List<Article> articles = resp.articles();
        return resp.articles();
    }

    public static void main(String[] args) {

        // start the stream of data
        new Thread(() -> {
            while (true){
                try {
                    poll();
                    Thread.sleep(POLL_INTERVAL); // 60sk
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Something went terribly wrong with " +
                            "the news stream");
                    break;
                }
            }
        }).start();

        // http://localhost:4567/<>
        get("/news", (req, res) -> {
            //TODO
            String region = req.queryParams("region");
            String sentiment = req.queryParams("sentiment");
            String howOld = req.queryParams("howOld");
            return "WIP";
        });

        get("/sources", (req, res)-> {
            RequestBuilder reqBuilder = new RequestBuilder();
            reqBuilder.setLanguage("en");
            ApiSourcesResponse resp = newsApi.sendSourcesRequest(reqBuilder);
            List<Source> sources = resp.sources();
            return sources.toString();
        });
    }
}
