package edu.palermo.master.ti.bi.customers.entities;
import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
import edu.palermo.master.ti.bi.sites.entities.Site;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {
    private Long customerId;
    private String emailAddress;
    private String name;
    private BusinessType businessType;
    private Site defaultSite;
    private Boolean archived;
    private Boolean isKeyAccount;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateCreated;
}
