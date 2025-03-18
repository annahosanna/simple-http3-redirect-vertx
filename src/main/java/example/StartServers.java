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

  public static void main(String[] args) {
    // This looks like its calling main
	    Vertx vertx = Vertx.vertx();
	    vertx.deployVerticle(new StartServers());
  // VertxApplication.main(new String[] { DeployExample.class.getName() });
  }

  @Override
  public Future<?> start() throws Exception {
    System.out.println("Deploying HTTP Verticle");
    vertx.deployVerticle("example.HttpVertical");
    System.out.println("Deployed HTTP Verticle");
    System.out.println("Deploying HTTPS Verticle");
    vertx.deployVerticle("example.HttpsVertical");
    System.out.println("Deployed HTTPS Verticle");

    return super.start();
  }
}
