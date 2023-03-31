package edu.palermo.master.ti.bi.usd;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface USDRepositoryInterface {

    public Double getPriceByDate(LocalDate dateParameter);
}
