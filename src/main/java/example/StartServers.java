package example;

import io.vertx.core.*;
import io.vertx.core.*;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.launcher.application.VertxApplication;
import io.vertx.launcher.application.VertxApplication;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class DeployExample extends VerticleBase {

  public static void main(String[] args) {
    VertxApplication.main(new String[] { DeployExample.class.getName() });
  }

  @Override
  public Future<?> start() throws Exception {
    System.out.println("Deploying HTTP Verticle");
    vertx.deployVerticle("example.HttpVertical");
    System.out.println("Deployed HTTP Verticle");
    System.out.println("Deploying HTTPS Verticle");
    vertx
      .deployVerticle("example.HttpVertical")
      .compose(deploymentID -> {
        System.out.println(
          "Other verticle deployed ok, deploymentID = " + deploymentID
        );

        return vertx
          .undeploy(deploymentID)
          .onSuccess(res2 -> {
            System.out.println("Verticles were indeployed");
          });
      });

    // Deploy specifying some config
    JsonObject config = new JsonObject().put("foo", "bar");
    vertx.deployVerticle(
      "io.vertx.example.core.verticle.deploy.OtherVerticle",
      new DeploymentOptions().setConfig(config)
    );

    // Deploy 10 instances
    vertx.deployVerticle(
      "io.vertx.example.core.verticle.deploy.OtherVerticle",
      new DeploymentOptions().setInstances(10)
    );

    // Deploy it as a worker verticle
    vertx.deployVerticle(
      "io.vertx.example.core.verticle.deploy.OtherVerticle",
      new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER)
    );

    return super.start();
  }
}
