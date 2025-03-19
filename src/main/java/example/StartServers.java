package example;

import io.vertx.core.*;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.*;
// import io.vertx.launcher.application.VertxApplication;

// import io.vertx.launcher.application.VertxApplication;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class StartServers extends VerticleBase {
// public Vertx vertx = Vertx.vertx(); 
  public static void main(String[] args) {
    // This looks like its calling main
	    Vertx vertx = Vertx.vertx();
	    System.out.println("Deploying Verticles");
	    vertx.deployVerticle("example.HttpVertical")
	    .onComplete(res -> {
	        if (res.succeeded()) {
	          System.out.println("HTTP Deployment id is: " + res.result());
	        } else {
	          System.out.println("HTTP Deployment failed!");
	          System.out.println(res.cause());
	        }
	      });
	    vertx.deployVerticle("example.HttpsVertical")
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
