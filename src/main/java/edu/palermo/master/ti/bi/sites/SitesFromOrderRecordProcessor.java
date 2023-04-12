package edu.palermo.master.ti.bi.sites;

import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.sites.entities.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SitesFromOrderRecordProcessor implements ItemProcessor<OrderRecord, Site> {

	@Autowired
	private HashMap<String, Site> sitesCache;
	private static final Logger log = LoggerFactory.getLogger(SitesFromOrderRecordProcessor.class);

	@Override
	public Site process(OrderRecord item) {
		if(sitesCache.containsKey(item.getSiteCode())) {
			return null;
		}
		final Site site = Site.builder()
				.siteCode(item.getSiteCode())
				.longitude(Double.valueOf(item.getLongitude()))
				.latitude(Double.valueOf(item.getLatitude()))
				.build();
		sitesCache.put(item.getSiteCode(), site);
		//log.info(String.valueOf(site));
		return site;
	}
}
