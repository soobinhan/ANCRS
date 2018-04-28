package edu.cornell.cs.cs5412.ancrs;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import edu.cornell.cs.cs5412.ancrs.models.Article;
import edu.cornell.cs.cs5412.ancrs.models.Message;
import edu.cornell.cs.cs5412.ancrs.models.SubscribeRequest;

/**
 * Defines v1 of the NewsAlert API
 */
@Api(
        name = "newsAlerts",
        version = "v1",
        // You can add additional SCOPES as a comma separated list of values
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class NewsService {
    @ApiMethod(name = "health", path="health", httpMethod="get")
    public Message health(){
        return Message.builder().message("OK").build();
    }

    @ApiMethod(name = "sub", path="sub", httpMethod="post")
    public Message subscribe(SubscribeRequest sub){
        throw new UnsupportedOperationException();
    }

    @ApiMethod(name = "unsub", path="unsub", httpMethod="get")
    public Message unsubscribe(@Named("phoneNumber") String phoneNumber){
        throw new UnsupportedOperationException();
    }

    // TODO: this will be a private endpoint not visible to clients
    @ApiMethod(name = "uploadArticle", path="upload/article", httpMethod = "post")
    public Message uploadArticle(Article article) {
        throw new UnsupportedOperationException();
    }
}
