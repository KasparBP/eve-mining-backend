package com.bitzero.evemining.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

@Data
public class CoreClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final DateFormat esiDateFormat;
    private final String basePath;
    private final String userAgent;

    public <T> ResponseEntity<T> perform(String path, HttpMethod method,
                                         Map<String, Object> pathParams,
                                         Map<String, String> headersParams,
                                         MultiValueMap<String, String> queryParams,
                                         T body, Class<T> clazz, boolean authorized) {
        return prepareRequest(path, method, pathParams, headersParams, queryParams, body, authorized)
                .retrieve()
                .toEntity(clazz)
                .block();
    }


    public <T> ResponseEntity<List<T>> performList(String path, HttpMethod method,
                                                   Map<String, Object> pathParams,
                                                   Map<String, String> headersParams,
                                                   MultiValueMap<String, String> queryParams,
                                                   Object body,
                                                   ParameterizedTypeReference<T> elementTypeRef, boolean authorized) {
        return prepareRequest(path, method, pathParams, headersParams, queryParams, body, authorized)
                .retrieve()
                .toEntityList(elementTypeRef)
                .block();
    }

    private WebClient.RequestBodySpec prepareRequest(String path, HttpMethod method,
                                                     Map<String, Object> pathParams,
                                                     Map<String, String> headersParams,
                                                     MultiValueMap<String, String> queryParams,
                                                     Object body, boolean authorized) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(basePath).path(path);
        if (queryParams != null) {
            builder.queryParams(queryParams);
        }
        final WebClient.RequestBodySpec requestBuilder = webClient
                .method(method).uri(builder.build(false).toUriString(), pathParams);
        requestBuilder.accept(MediaType.APPLICATION_JSON);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("User-Agent", userAgent);
        if (headersParams != null) {
            headersParams.forEach(requestBuilder::header);
        }
        if (body != null) {
            requestBuilder.body(BodyInserters.fromValue(body));
        }
        if (authorized) {
            requestBuilder.attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
                    .clientRegistrationId("evesso"));
        }
        return requestBuilder;
    }
}
