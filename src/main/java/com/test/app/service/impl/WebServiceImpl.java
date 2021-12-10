package com.test.app.service.impl;

import com.test.app.entity.Users;
import com.test.app.service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class WebServiceImpl implements WebService {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceImpl.class);

  private WebClient localApiClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();
  }

  @Override
  public Boolean isValid(String key) {
    WebClient.ResponseSpec response =
        localApiClient()
            .post()
            .uri("/key/validate")
            .body(BodyInserters.fromFormData("key", key))
            .retrieve();
    return Optional.of(response.toBodilessEntity().block().getStatusCode().is2xxSuccessful()).get();
  }

  @Override
  public Users getUser(String key) {
    WebClient.ResponseSpec response =
        localApiClient()
            .post()
            .uri("/key/user")
            .body(BodyInserters.fromFormData("key", key))
            .retrieve();
    Users map = response.bodyToMono(Users.class).block();
    LOGGER.info("username:{}", map.getUsername());
    return map;
  }
}
