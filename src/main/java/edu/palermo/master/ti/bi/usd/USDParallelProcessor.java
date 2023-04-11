package edu.palermo.master.ti.bi.usd;

import edu.palermo.master.ti.bi.usd.dto.USDParallelRecord;
import edu.palermo.master.ti.bi.usd.entities.USD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static edu.palermo.master.ti.bi.utils.FormatUtils.formatLocalDate;

@Component
public class USDParallelProcessor implements ItemProcessor<USDParallelRecord, USD> {

	private static final Logger log = LoggerFactory.getLogger(USDParallelProcessor.class);

	@Override
	public USD process(USDParallelRecord item) {
		final USD parallelUsd = USD.builder()
				.date(formatLocalDate(item.getFecha()))
				.price(item.getInformal())
				.build();

		//log.info(String.valueOf(businessType));

		return parallelUsd;
	}
}
