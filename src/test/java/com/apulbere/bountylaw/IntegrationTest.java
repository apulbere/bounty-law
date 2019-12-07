package com.apulbere.bountylaw;

import com.apulbere.bountylaw.verticle.BountyVerticle;
import com.apulbere.bountylaw.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class IntegrationTest {

    Vertx vertx;

    @Before
    public void before(TestContext context) {
        vertx = Vertx.vertx();

        vertx.exceptionHandler(context.exceptionHandler());
        vertx.deployVerticle(new BountyVerticle());
        vertx.deployVerticle(new ServerVerticle());
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void foundBounty(TestContext context) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        client.getNow(8080, "localhost", "/bounty/42", resp -> {
            resp.exceptionHandler(context.exceptionHandler());
            context.assertEquals(200, resp.statusCode());
            resp.bodyHandler(body -> {
                JsonObject jsonObject = (JsonObject) body.toJson();
                context.assertEquals("800$", jsonObject.getString("reward"));
                client.close();
                async.complete();
            });
        });
    }

    @Test
    public void bountyNotFound(TestContext context) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        client.getNow(8080, "localhost", "/bounty/89", resp -> {
            resp.exceptionHandler(context.exceptionHandler());
            context.assertEquals(500, resp.statusCode());
            client.close();
            async.complete();
        });
    }

}
