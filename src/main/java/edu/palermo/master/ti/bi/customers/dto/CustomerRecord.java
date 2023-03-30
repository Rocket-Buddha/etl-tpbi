package edu.palermo.master.ti.bi.customers.dto;

import lombok.Data;

@Data
public class CustomerRecord {
    private String customerId;
    private String emailAddress;
    private String name;
    private String businessTypeId;
    private String siteCode;
    private String archived;
    private String isKeyAccount;
    private String dateUpdated;
    private String dateCreated;
}
