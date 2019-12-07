package com.apulbere.bountylaw.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class BountyVerticle extends AbstractVerticle {

    private final Map<String, String> bountyDatabase = Map.of("42", "800$");

    @Override
    public void start() {
        vertx.eventBus().<JsonObject>consumer("bounty", msg -> {
            JsonObject json = msg.body();
            String bountyAmount = bountyDatabase.get(json.getString("id"));
            if(bountyAmount != null) {
                msg.reply(new JsonObject().put("reward", bountyAmount));
            } else {
                msg.fail(0, "nu such bounty");
            }
        });
    }
}
