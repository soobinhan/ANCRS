import io.github.ccincharge.newsapi.NewsApi;

import static spark.Spark.get;

/**
 * first tier REST API
 *
 * For now, a single endpoint:
 *
 * GET /news get the latest and hottest news
 */
public class Main {
    public static void main(String[] args) {
        // http://localhost:4567/<>
        get("/news", (req, res) -> {
            //TODO
            String region = req.queryParams("region");
            String sentiment = req.queryParams("sentiment");
            String howOld = req.queryParams("howOld");
            return "WIP";
        });
    }
}
