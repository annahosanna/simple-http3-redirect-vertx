package example;

import io.vertx.core.AbstractVerticle;
//import io.vertx.core.net.
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.KeyCertOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;

// https://vertx.io/blog/whats-new-in-vert-x-5/
// VerticleBase replaces AbstractVerticle in Vertx 5

// https://github.com/vert-x3/vertx-examples/tree/5.x/core-examples#graceful-shutdown

public class HttpsVertical extends VerticleBase {

	private HttpServer server;


  // Http
  @Override
  public Future<?> start() throws Exception {
    // Vertx vertx = Vertx.vertx();

    HttpServerOptions secureOptions = new HttpServerOptions();
    secureOptions.setUseAlpn(true);
    secureOptions.setSsl(true);
    secureOptions.setKeyCertOptions(new PemKeyCertOptions().setKeyPath("key.pem").setCertPath("certs.pem"));
    secureOptions.setPort(8443);
    // The other option is io.vertx.core.net.PemKeyCertOptions
    // PemKeyCertOptions pemKeyCertOptions = new PemKeyCertOptions();
    // pemKeyCertOptions.addCertPath("certs.pem").addKeyPath("key.pem");
    // pkcs8 pem and x509 pem
    // The x509 pem should look like:
    // -----BEGIN CERTIFICATE-----
    // MIIDezCCAmOgAwIBAgIEZOI/3TANBgkqhkiG9w0BAQsFADBuMRAwDgYDVQQGEwdV
    // ...
    // +tmLSvYS39O2nqIzzAUfztkYnUlZmB0l/mKkVqbGJA==
    // -----END CERTIFICATE-----

    // Which allows you to specify the path public and private key pem files
    // Or to place the same data as is stored in the pem files into a Buffer

    // JksOptions keyStoreOptions = new JksOptions();
    // keyStoreOptions.setPath("path/to/keystore.jks"); // Replace with your keystore path
    // keyStoreOptions.setPassword("your_keystore_password"); // Replace with your keystore password

    // KeyCertOptions keyCertOptions =  secureOptions.getKeyCertOptions();
    // KeyManagerFactory keyManagerFactory = keyCertOptions.getKeyManagerFactory(vertx);

    // secureOptions.setKeyCertOptions(pemKeyCertOptions);
 
    // secureOptions.setKeyStoreOptions(keyStoreOptions);

    // JksOptions trustStoreOptions = new JksOptions();
    // trustStoreOptions.setPath("path/to/truststore.jks"); // Replace with your truststore path
    // trustStoreOptions.setPassword("your_truststore_password"); // Replace with your truststore password

    // Data type is TrustOptions, but I guess jksoptions must be a type of trustoption
    // secureOptions.setTrustOptions(pemKeyCertOptions);
    // Might be hard to use localhost for certs, but editing the host file could
    // trick a hostname into being localhost
    Router router = Router.router(vertx);

    String responseText = new String("Https Response");
    router
      .route()
      .handler(routingContext -> {
        routingContext
          .response()
          .putHeader("Content-Type", "text/plain")
          .putHeader("Content-Length", String.valueOf(responseText.length()))
          .putHeader(
            "Alt-Svc",
            "h3=\"localhost:8443\", quic=\"localhost:8443\""
          )
          .putHeader("Application-Protocol", "h3,quic,h2,http/1.1")
          .end(responseText);
      });

    server = vertx.createHttpServer(secureOptions).requestHandler(router);
    server.listen();
//    .onComplete(ar -> {
//        if (ar.failed()) {
//          System.out.println("Failing to start the STOMP server : " + ar.cause().getMessage());
//        } else {
//          System.out.println("Ready to receive STOMP frames");
//        }
//      });
    return super.start();
  }
}
