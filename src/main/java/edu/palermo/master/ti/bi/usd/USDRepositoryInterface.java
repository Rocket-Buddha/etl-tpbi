package edu.palermo.master.ti.bi.usd;

import java.time.LocalDate;

public interface USDRepositoryInterface {

    public Double getPriceByDate(LocalDate dateParameter);
    public Double getParallelPriceByDate(LocalDate dateParameter);
}
