package com.bitzero.evemining.client;

import lombok.Data;
import org.openapitools.client.model.GetCharactersCharacterIdOk;
import org.openapitools.client.model.GetCharactersCharacterIdPortraitOk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;

@Data
@Component
public class CharacterClient {
    @Value("${esidatasource}")
    private String esiDataSource;
    private final CoreClient coreClient;

    public ResponseEntity<GetCharactersCharacterIdOk> getCharacterInfo(Integer characterId, String ifNoneMatch) {
        Map<String, Object> pathParams = Map.of("character_id", characterId);
        Map<String, String> headerParams = null;
        if (ifNoneMatch != null) {
            headerParams = Collections.singletonMap("If-None-Match", ifNoneMatch);
        }
        return coreClient.perform("/v4/characters/{character_id}/", HttpMethod.GET, pathParams, headerParams,
                defaultQueryParams(esiDataSource), null, GetCharactersCharacterIdOk.class, true);
    }

    public ResponseEntity<GetCharactersCharacterIdPortraitOk> getCharacterPortrait(Integer characterId, String ifNoneMatch) {
        Map<String, Object> pathParams = Map.of("character_id", characterId);
        Map<String, String> headerParams = null;
        if (ifNoneMatch != null) {
            headerParams = Collections.singletonMap("If-None-Match", ifNoneMatch);
        }
        return coreClient.perform("/v4/characters/{character_id}/portrait/", HttpMethod.GET, pathParams, headerParams,
                defaultQueryParams(esiDataSource), null, GetCharactersCharacterIdPortraitOk.class, true);
    }

    private static MultiValueMap<String, String> defaultQueryParams(String datasource) {
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("datasource", Collections.singletonList(datasource));
        return queryParams;
    }

}
