package com.bitzero.evemining.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Portrait {
    private String px64;
    private String px128;
    private String px256;
    private String px512;
}
