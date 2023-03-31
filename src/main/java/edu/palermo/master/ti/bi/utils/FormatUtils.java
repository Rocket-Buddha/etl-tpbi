package edu.palermo.master.ti.bi.utils;

import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
import edu.palermo.master.ti.bi.customers.entities.Customer;
import edu.palermo.master.ti.bi.sites.entities.Site;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FormatUtils {

    public static LocalDateTime formatLocalDateTime(final String date) {

        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(date).matches()) {
            return LocalDate.parse(date).atStartOfDay();
        } else if (date.lastIndexOf('.') == -1) {
            return LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (date.length() - date.lastIndexOf('.') - 1 == 3) {
            return LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        return LocalDateTime.parse(date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"));
    }

    public static LocalDate formatLocalDate(final String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static BusinessType formatBusinessType(final String bt) {
        switch (bt) {
            case "1":
                return BusinessType.builder()
                        .businessTypeId(1L)
                        .build();
            case "2":
                return BusinessType.builder()
                        .businessTypeId(2L)
                        .build();
            case "3":
                return BusinessType.builder()
                        .businessTypeId(3L)
                        .build();
            case "4":
                return BusinessType.builder()
                        .businessTypeId(4L)
                        .build();
            case "5":
                return BusinessType.builder()
                        .businessTypeId(5L)
                        .build();
            case "6":
                return BusinessType.builder()
                        .businessTypeId(6L)
                        .build();
            case "7":
                return BusinessType.builder()
                        .businessTypeId(7L)
                        .build();
            case "8":
                return BusinessType.builder()
                        .businessTypeId(8L)
                        .build();
            default:
                return BusinessType.builder()
                        .businessTypeId(9L)
                        .build();
        }
    }

    public static Customer formatCustomer(final String customerId,
                                          HashMap<Long, Customer> customerCache) {
        final Long cid = Long.parseLong(customerId);
        return Customer.builder()
                .customerId(customerCache.containsKey(cid) ? cid : 0)
                .build();
    }

    public static Site formatSite(final String siteCode) {
        return Site.builder()
                .siteCode(siteCode)
                .build();
    }
}
