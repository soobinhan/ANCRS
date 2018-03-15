import io.github.ccincharge.newsapi.NewsApi;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        // http://localhost:4567/hello
        get("/hello", (req, res) -> "Hello World");
    }
}
