package com.apulbere.bountylaw;

import com.apulbere.bountylaw.verticle.BountyVerticle;
import com.apulbere.bountylaw.verticle.ServerVerticle;
import io.vertx.core.AbstractVerticle;

public class App extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(App.class);
    }

    @Override
    public void start() {
        vertx.deployVerticle(ServerVerticle.class.getName());
        vertx.deployVerticle(BountyVerticle.class.getName());
    }
}
