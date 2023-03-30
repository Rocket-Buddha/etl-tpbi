package edu.palermo.master.ti.bi.sites.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Site {
    private String siteCode;
    private Double latitude;
    private Double longitude;
}
