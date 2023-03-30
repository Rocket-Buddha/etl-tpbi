package edu.palermo.master.ti.bi.orders.dto;

import lombok.Data;

@Data
public class OrderRecord {
    private String batchId;
    private String createdDate;
    private String updatedDate;
    private String submittedDate;
    private String deliveryDate;
    private String customerId;
    private String orderId;
    private String siteCode;
    private String total;
    private String totalShipping;
    private String trackingCode;
    private String orderStatus;
    private String gmvEnabled;
    private String orderNumber;
    private String shippingByTracking;
    private String latitude;
    private String longitude;
}
