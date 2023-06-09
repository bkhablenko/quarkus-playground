package com.github.bkhablenko.tvmaze;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class TvmazeRestClientTest {

  private static final long GAME_OF_THRONES_SHOW_ID = 82;

  @RestClient
  TvmazeRestClient tvmazeRestClient;

  @DisplayName("getShowById")
  @Nested
  class GetShowByIdTest {

    @DisplayName("should return show on success")
    @Test
    public void shouldReturnShowOnSuccess() throws Exception {
      var show = tvmazeRestClient.getShowById(GAME_OF_THRONES_SHOW_ID);

      assertThat(show.id(), is(GAME_OF_THRONES_SHOW_ID));
      assertThat(show.name(), is("Game of Thrones"));

      var expectedImageUrl = new URL("https://static.tvmaze.com/uploads/images/original_untouched/190/476117.jpg");
      assertThat(show.image().original(), is(expectedImageUrl));
    }

    @DisplayName("should throw exception if not found")
    @Test
    public void shouldThrowExceptionIfNotFound() {
      var nonExistentShowId = 0;
      assertThrows(ClientWebApplicationException.class, () -> tvmazeRestClient.getShowById(nonExistentShowId));
    }
  }
}
