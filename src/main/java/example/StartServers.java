package example;

import io.vertx.core.*;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.*;
import io.vertx.launcher.application.*;
import io.netty.resolver.dns.macos.*;
import io.vertx.launcher.application.VertxApplication;
import io.vertx.core.dns.AddressResolverOptions;

public class StartServers extends VerticleBase {

  public static void main(String[] args) {
	  VertxApplication.main(new String[]{StartServers.class.getName()});
    // This looks like its calling main
	  // -Dvertx.disableDnsResolver
	  AddressResolverOptions addressResolverOptions = new AddressResolverOptions();
	  
	  VertxOptions vertxOptions = new VertxOptions().setAddressResolverOptions(addressResolverOptions);;
    Vertx vertx = Vertx.vertx(vertxOptions);
    System.out.println("Deploying Verticles");
    vertx
      .deployVerticle("example.HttpVertical")
      .onComplete(res -> {
        if (res.succeeded()) {
          System.out.println("HTTP Deployment id is: " + res.result());
        } else {
          System.out.println("HTTP Deployment failed!");
          System.out.println(res.cause());
        }
      });
    vertx
      .deployVerticle("example.HttpsVertical")
      .onComplete(res -> {
        if (res.succeeded()) {
          System.out.println("HTTPS Deployment id is: " + res.result());
        } else {
          System.out.println("HTTPS Deployment failed!");
          System.out.println(res.cause());
        }
      });
    System.out.println("Deployed Verticles");
  }
}
