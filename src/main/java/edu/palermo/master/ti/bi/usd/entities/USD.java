package edu.palermo.master.ti.bi.usd.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class USD {
    private LocalDate date;
    private Double price;
}
