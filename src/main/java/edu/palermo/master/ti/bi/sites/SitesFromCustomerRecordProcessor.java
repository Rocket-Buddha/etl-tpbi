package edu.palermo.master.ti.bi.sites;

import edu.palermo.master.ti.bi.customers.dto.CustomerRecord;
import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.sites.entities.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SitesFromCustomerRecordProcessor implements ItemProcessor<CustomerRecord, Site> {

	private static final Logger log = LoggerFactory.getLogger(SitesFromCustomerRecordProcessor.class);

	@Override
	public Site process(CustomerRecord item) {
		final Site site = Site.builder()
				.siteCode(item.getSiteCode())
				.build();

		//log.info(String.valueOf(site));

		return site;
	}
}
