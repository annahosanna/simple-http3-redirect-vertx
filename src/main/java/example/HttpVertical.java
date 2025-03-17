package example;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

// import io.vertx.

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HttpVertical extends VerticleBase {

  // Http
  @Override
  public Future<?> start() throws Exception {
    Router router = Router.router(vertx);

    String responseText = new String("Http Upgrade Response");
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", String.valueOf(responseText.length()))
          // .putHeader("Alt-Svc", concatValue)
          .putHeader("Strict-Transport-Security", "max-age=60000")
          .putHeader("Upgrade-Insecure-Requests", "1")
          .setStatusCode(301)
          .putHeader(
            "Location",
            routingContext.request().absoluteURI().replace("http", "https")
          )
          .end(responseText);
      });

    vertx.createHttpServer().requestHandler(router).listen(8080);
    return super.start();
  }
}
