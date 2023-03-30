package edu.palermo.master.ti.bi.sites;

import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.sites.entities.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SitesFromOrderRecordProcessor implements ItemProcessor<OrderRecord, Site> {

	private static final Logger log = LoggerFactory.getLogger(SitesFromOrderRecordProcessor.class);

	@Override
	public Site process(OrderRecord item) {
		final Site site = Site.builder()
				.siteCode(item.getSiteCode())
				.longitude(Double.valueOf(item.getLongitude()))
				.latitude(Double.valueOf(item.getLatitude()))
				.build();

		//log.info(String.valueOf(site));

		return site;
	}
}
