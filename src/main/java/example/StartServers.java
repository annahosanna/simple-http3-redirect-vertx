package example;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
// import io.vertx.

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class StartServers {

  public static void main(String[] args) {
   //  VertxApplication.main(new String[] { Server.class.getName() });
	  Vertx vertx = Vertx.vertx();
	  vertx.deployVerticle(new HttpsVertical());
	  vertx.deployVerticle(new HttpVertical());
  }
}
