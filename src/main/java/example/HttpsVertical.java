package example;

import io.vertx.core.AbstractVerticle;
//import io.vertx.core.net.
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.KeyCertOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;

public class HttpsVertical extends VerticleBase {

  @Override
  public Future<?> start() throws Exception {
    super.start();

    HttpServerOptions secureOptions = new HttpServerOptions();
    secureOptions.setUseAlpn(true);
    secureOptions.setSsl(true);
    secureOptions.setKeyCertOptions(new PemKeyCertOptions().setKeyPath("key.pem").setCertPath("certs.pem"));
    Router router = Router.router(vertx);

    String responseText = new String("Https Response");
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", String.valueOf(responseText.length()))
   //       .putHeader(
   //         "Alt-Svc",
   //         "h3=\"localhost:8443\", quic=\"localhost:8443\""
   //       )
   //       .putHeader("Application-Protocol", "h3,quic,h2,http/1.1")
          .end(responseText);
      });

    return vertx
      .createHttpServer(secureOptions)
      .requestHandler(router)
      .listen(8443, "localhost");
  }
}
