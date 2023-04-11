package edu.palermo.master.ti.bi.usd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class USDRepository implements USDRepositoryInterface {

    @Autowired
    @Qualifier("h2JdbcTemplate")
    private JdbcTemplate h2JdbcTemplate;

    @Value("${readers.usd.price-query}")
    private String query;

    @Value("${readers.usd.price-parallel-query}")
    private String parallelQuery;

    @Override
    public Double getPriceByDate(LocalDate dateParameter) {

        final Map<String, Object> defaultPrice = new HashMap<>() {{
            put("PRICE", BigDecimal.ZERO);
        }};

        return Double.valueOf(String.valueOf(h2JdbcTemplate.queryForList(query, dateParameter).stream().findFirst()
                .orElse(defaultPrice).get("PRICE")));
    }

    @Override
    public Double getParallelPriceByDate(LocalDate dateParameter) {
        final Map<String, Object> defaultPrice = new HashMap<>() {{
            put("PRICE", BigDecimal.ZERO);
        }};

        return Double.valueOf(String.valueOf(h2JdbcTemplate.queryForList(parallelQuery, dateParameter).stream().findFirst()
                .orElse(defaultPrice).get("PRICE")));
    }
}
