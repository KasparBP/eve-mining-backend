package com.bitzero.evemining.vo;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Character {
    private Integer id;
    private Integer allianceId;
    private Integer corporationId;
    private ZonedDateTime birthday;
    private String gender;
    private String name;
    private Portrait portrait;
}
