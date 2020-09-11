package com.bitzero.evemining.controller;

import com.bitzero.evemining.client.CharacterClient;
import com.bitzero.evemining.mapping.CharacterMapper;
import com.bitzero.evemining.vo.Character;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.GetCharactersCharacterIdOk;
import org.openapitools.client.model.GetCharactersCharacterIdPortraitOk;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bitzero.evemining.Constants.CHARACTER_PATH;

@RequestMapping(CHARACTER_PATH)
@RestController(value = CHARACTER_PATH)
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterClient characterClient;

    @GetMapping
    public Character character(@AuthenticationPrincipal OAuth2User principal) {
        Integer characterID = principal.getAttribute("CharacterID");
        GetCharactersCharacterIdOk characterInfo = characterClient.getCharacterInfo(characterID, null).getBody();
        GetCharactersCharacterIdPortraitOk portrait = characterClient.getCharacterPortrait(characterID, null).getBody();
        return CharacterMapper.characterFrom(characterInfo, portrait);
    }

}
