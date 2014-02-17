package com.mycompany.myproject.test.integration.java;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

public class RelativeSymlinkTest extends TestVerticle {

  @Test
  public void testRelativeSymlink() {
    container.logger().info("in testRelativeSymlink()");
    vertx.eventBus().send("ping-address", "link", new Handler<Message<String>>() {
      @Override
      public void handle(Message<String> reply) {
        assertEquals("repos.txt", reply.body());

        testComplete();
      }
    });
  }

  // Deploys module as usual
  @Override
  public void start() {
    initialize();
    container.deployModule(System.getProperty("vertx.modulename"), new AsyncResultHandler<String>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
        if (asyncResult.failed()) {
          container.logger().error(asyncResult.cause());
        }
        assertTrue(asyncResult.succeeded());
        assertNotNull("deploymentID should not be null", asyncResult.result());
        startTests();
      }
    });
  }

}
