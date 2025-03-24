package example;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import java.lang.Integer;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class HttpVertical extends VerticleBase {

  @Override
  public Future<?> start() throws Exception {
    super.start();
    Router router = Router.router(vertx);

    String responseText = new String("Http Upgrade Response");
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", String.valueOf(responseText.length()))
          .putHeader("Strict-Transport-Security", "max-age=60000")
          .putHeader("Upgrade-Insecure-Requests", "1")
          // Respond Upgrade Required
          .setStatusCode(426)
          .putHeader(
            "Location",
            routingContext.request().absoluteURI().replace("http", "https")
          )
          .end(responseText);
      });
    SocketAddress bindSocketAddress = SocketAddress.inetSocketAddress(
      8080,
      "127.0.0.1"
    );
    return vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(bindSocketAddress);
  }

  private byte[] ip4AddressToByteArray(String dottedDecimal) {
    try {
      String[] parts = dottedDecimal.split("\\.");
      if (parts.length != 4) {
        byte[] returnBytes = { 0, 0, 0, 0 };
        return returnBytes;
      } else {
        byte[] returnBytes = {
          (byte) Integer.parseInt(parts[0]),
          (byte) Integer.parseInt(parts[1]),
          (byte) Integer.parseInt(parts[2]),
          (byte) Integer.parseInt(parts[3]),
        };
        return returnBytes;
      }
    } catch (Exception e) {
      byte[] returnBytes = { 0, 0, 0, 0 };
      return returnBytes;
    }
  }
}
