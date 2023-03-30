package edu.palermo.master.ti.bi.customers;

import edu.palermo.master.ti.bi.customers.dto.CustomerRecord;
import edu.palermo.master.ti.bi.customers.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static edu.palermo.master.ti.bi.utils.FormatUtils.*;

@Component
public class CustomerProcessor implements ItemProcessor<CustomerRecord, Customer> {

    @Autowired
    private HashMap<Long, Customer> customerCache;

    private static final Logger log = LoggerFactory.getLogger(CustomerProcessor.class);

    @Override
    public Customer process(CustomerRecord item) {

        final Long customerID = Long.valueOf(item.getCustomerId());

        final Customer customer = Customer.builder()
                .customerId(customerID)
                .emailAddress(item.getEmailAddress())
                .name(item.getName())
                .businessType(formatBusinessType(item.getBusinessTypeId()))
                .defaultSite(formatSite(item.getSiteCode()))
                .archived(Boolean.valueOf(item.getArchived()))
                .isKeyAccount(Boolean.valueOf(item.getIsKeyAccount()))
                .dateUpdated(formatDate(item.getDateUpdated()))
                .dateCreated(formatDate(item.getDateCreated()))
                .build();

        customerCache.put(customerID, customer);

        //log.info(String.valueOf(customer));

        return customer;
    }


}
