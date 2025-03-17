package example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.KeyCertOptions;
//import io.vertx.core.net.
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.web.Router;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import io.vertx.core.net.PemKeyCertOptions;


public class HttpsVertical extends VerticleBase {

	  public HttpsVertical( ){
	  }

	  // Http
	  // @Override
	  public void start(Future<Void> future) throws Exception {

    // Vertx vertx = Vertx.vertx();

    HttpServerOptions secureOptions = new HttpServerOptions();
    secureOptions.setUseAlpn(true);
    secureOptions.setSsl(true);

    // The other option is io.vertx.core.net.PemKeyCertOptions
    PemKeyCertOptions pemKeyCertOptions = new PemKeyCertOptions();
    pemKeyCertOptions.addCertPath("")
    .addKeyPath("");
    // pkcs8 pem and x509 pem
    
    // Which allows you to specify the path public and private key pem files
    // Or to place the same data as is stored in the pem files into a Buffer
    
    JksOptions keyStoreOptions = new JksOptions();
    keyStoreOptions.setPath("path/to/keystore.jks"); // Replace with your keystore path
    keyStoreOptions.setPassword("your_keystore_password"); // Replace with your keystore password

   // KeyCertOptions keyCertOptions =  secureOptions.getKeyCertOptions();
// KeyManagerFactory keyManagerFactory = keyCertOptions.getKeyManagerFactory(vertx);
    
    secureOptions.setKeyCertOptions(pemKeyCertOptions);
    // secureOptions.setKeyStoreOptions(keyStoreOptions);

    JksOptions trustStoreOptions = new JksOptions();
    trustStoreOptions.setPath("path/to/truststore.jks"); // Replace with your truststore path
    trustStoreOptions.setPassword("your_truststore_password"); // Replace with your truststore password

   
    // Data type is TrustOptions, but I guess jksoptions must be a type of trustoption
    secureOptions.setTrustOptions(trustStoreOptions);
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
          .putHeader("Alt-Svc", "h3=\"localhost:8443\", quic=\"localhost:8443\"")
        .putHeader("Application-Protocol", "h3,quic,h2,http/1.1")
        .end(responseText);
      });

    
    vertx
      .createHttpServer(secureOptions)
      .requestHandler(router)
      .listen(8443); // Use port 8443 for HTTPS
  }
}
