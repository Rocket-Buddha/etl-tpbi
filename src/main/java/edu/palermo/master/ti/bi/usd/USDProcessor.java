package edu.palermo.master.ti.bi.usd;

import edu.palermo.master.ti.bi.usd.dto.USDRecord;
import edu.palermo.master.ti.bi.usd.entities.USD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static edu.palermo.master.ti.bi.utils.FormatUtils.formatLocalDate;

@Component
public class USDProcessor implements ItemProcessor<USDRecord, USD> {

	private static final Logger log = LoggerFactory.getLogger(USDProcessor.class);

	@Override
	public USD process(USDRecord item) {
		final USD businessType = USD.builder()
				.date(formatLocalDate(item.getDate()))
				.price((item.getMax() + item.getMin())/2)
				.build();

		//log.info(String.valueOf(businessType));

		return businessType;
	}
}
