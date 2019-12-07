package com.apulbere.bountylaw.verticle;

import com.apulbere.bountylaw.controller.BountyController;
import io.vertx.core.AbstractVerticle;
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;

public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() {
        VertxResteasyDeployment deployment = new VertxResteasyDeployment();
        deployment.start();
        deployment.getRegistry().addPerInstanceResource(BountyController.class);

        vertx.createHttpServer()
                .requestHandler(new VertxRequestHandler(vertx, deployment))
                .listen(8080);
    }
}