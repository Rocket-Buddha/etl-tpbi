package edu.palermo.master.ti.bi.orders.entities;

import edu.palermo.master.ti.bi.customers.entities.Customer;
import edu.palermo.master.ti.bi.sites.entities.Site;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Long orderId;
    private String batchId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime submittedDate;
    private LocalDateTime deliveryDate;
    private Customer customer;
    private Site site;
    private Double total;
    private Double totalUSD;
    private Double totalShipping;
    private Double totalShippingUSD;
    private Double usdOfficialListing;
    private Double usdParallelListing;
    private String trackingCode;
    private String orderStatus;
    private Boolean gmvEnabled;
    private String orderNumber;
    private Double shippingByTracking;
}
