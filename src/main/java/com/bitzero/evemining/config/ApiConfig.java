package com.bitzero.evemining.config;

import com.bitzero.evemining.client.CoreClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DateFormat;
import java.util.TimeZone;

@Configuration
public class ApiConfig {

    @Bean
    DateFormat esiDateFormat() {
        DateFormat dateFormat = new org.openapitools.client.RFC3339DateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat;
    }

    @Bean
    ObjectMapper objectMapper(DateFormat esiDateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(esiDateFormat);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNullableModule jnm = new JsonNullableModule();
        mapper.registerModule(jnm);
        return mapper;
    }


    @Bean
    CoreClient coreClient(WebClient webClient, ObjectMapper objectMapper, DateFormat esiDateFormat,
                          @Value("${evemining.useragent}") String userAgent,
                          @Value("${evemining.esibasepath}") String basePath) {
        return new CoreClient(webClient, objectMapper, esiDateFormat, userAgent, basePath);
    }
}
