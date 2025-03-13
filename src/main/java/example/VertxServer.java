package example;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.web.Router;
import io.vertx.launcher.application.VertxApplication;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class VertxServer extends VerticleBase {

  public static void main(String[] args) {
    VertxApplication.main(new String[] { Server.class.getName() });
  }

  @Override
  public Future<?> start() throws Exception {
    Router router = Router.router(vertx);

    String responseText = new String("Just a test");
    // String headerStartText = new String("Alt-Svc");
    String headerMiddleText = new String("h3=");
    String headerEndText = new String(":8443");
    String quote = new String("\"");
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", responseText.length())
          .putHeader(
            "Alt-Svc",
            headerMiddleText.concat(quote).concat(headerEndText).concat(quote)
          )
          .end("Just a test");
      });

    return vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
