package com.apulbere.bountylaw.controller;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class BountyController {

    @GET
    @Path("/bounty/{bountyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void get(@Suspended final AsyncResponse asyncResponse,  @Context Vertx vertx, @PathParam("bountyId") String bountyId) {
        vertx.eventBus().<JsonObject>send("bounty", new JsonObject().put("id", bountyId), msg -> {
            if (msg.succeeded()) {
                JsonObject json = msg.result().body();
                asyncResponse.resume(json.encode());
            } else {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        });
    }
}