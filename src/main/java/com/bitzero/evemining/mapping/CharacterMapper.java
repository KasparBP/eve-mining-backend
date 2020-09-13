package com.bitzero.evemining.mapping;

import com.bitzero.evemining.vo.Portrait;
import org.openapitools.client.model.GetCharactersCharacterIdOk;
import org.openapitools.client.model.GetCharactersCharacterIdPortraitOk;

public class CharacterMapper {

    public static com.bitzero.evemining.vo.Character characterFrom(GetCharactersCharacterIdOk charIdOk,
                                          GetCharactersCharacterIdPortraitOk charIdPortraitOk) {
        if (charIdOk == null) {
            return null;
        }
        return com.bitzero.evemining.vo.Character.builder()
                .allianceId(charIdOk.getAllianceId())
                .corporationId(charIdOk.getCorporationId())
                .name(charIdOk.getName())
                .birthday(charIdOk.getBirthday().toZonedDateTime())
                .gender(charIdOk.getGender().toString())
                .portrait(portraitFrom(charIdPortraitOk))
                .build();
    }

    private static Portrait portraitFrom(GetCharactersCharacterIdPortraitOk charIdPortraitOk) {
        if (charIdPortraitOk == null) {
            return null;
        }
        return Portrait.builder()
                .px64(charIdPortraitOk.getPx64x64())
                .px128(charIdPortraitOk.getPx128x128())
                .px256(charIdPortraitOk.getPx256x256())
                .px512(charIdPortraitOk.getPx512x512())
                .build();
    }

}
