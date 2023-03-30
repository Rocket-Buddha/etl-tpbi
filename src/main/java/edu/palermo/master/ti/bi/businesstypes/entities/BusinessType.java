package edu.palermo.master.ti.bi.businesstypes.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessType {
    private Long businessTypeId;
    private String initcap;
    private Boolean active;
}
