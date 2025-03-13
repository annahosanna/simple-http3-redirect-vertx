package example;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
// import io.vertx.

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class VertxServer extends VerticleBase {

  public static void main(String[] args) {
   //  VertxApplication.main(new String[] { Server.class.getName() });
	  Vertx vertx = Vertx.vertx();
	  vertx.deployVerticle(new VertxServer());
  }

  @Override
  public Future<?> start() throws Exception {
    Router router = Router.router(vertx);

    String responseText = new String("Just a test");
    // String headerStartText = new String("Alt-Svc");
    String headerMiddleText = new String("h3=");
    String headerEndText = new String(":8443");
    String quote = new String("\"");
    // Some really weird problem where I couldn't escape quotes
    String concatValue = new String(headerMiddleText.concat(quote).concat(headerEndText).concat(quote)); 
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", String.valueOf(responseText.length()))
          .putHeader("Alt-Svc", concatValue)
          .end(responseText);
      });

    return vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
