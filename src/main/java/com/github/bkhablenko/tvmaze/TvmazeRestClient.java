package com.github.bkhablenko.tvmaze;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.tvmaze.com", configKey = "tvmaze")
public interface TvmazeRestClient {

  @GET
  @Path("/shows/{showId}")
  TvmazeShow getShowById(@PathParam("showId") long showId);
}
