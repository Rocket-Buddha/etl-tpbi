package edu.palermo.master.ti.bi.businesstypes;

import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
import edu.palermo.master.ti.bi.businesstypes.dto.BusinessTypeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BusinessTypeProcessor implements ItemProcessor<BusinessTypeRecord, BusinessType> {

	private static final Logger log = LoggerFactory.getLogger(BusinessTypeProcessor.class);

	@Override
	public BusinessType process(BusinessTypeRecord item) {
		final BusinessType businessType = BusinessType.builder()
				.initcap(item.getInitcap())
				.businessTypeId(Long.valueOf(item.getBusinessTypeId()))
				.active(Boolean.valueOf(item.getActive()))
				.build();

		//log.info(String.valueOf(businessType));

		return businessType;
	}
}
