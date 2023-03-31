package edu.palermo.master.ti.bi.usd.dto;

import lombok.Data;

@Data
public class USDRecord {
    private String date;
    private Double last;
    private Double opening;
    private Double max;
    private Double min;
    private Float vol;
    private Float var;
}
