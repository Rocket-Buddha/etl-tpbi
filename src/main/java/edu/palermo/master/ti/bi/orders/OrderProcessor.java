package edu.palermo.master.ti.bi.orders;

import edu.palermo.master.ti.bi.customers.entities.Customer;
import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.orders.entities.Order;
import edu.palermo.master.ti.bi.usd.USDRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

import static edu.palermo.master.ti.bi.utils.FormatUtils.*;

@Component
public class OrderProcessor implements ItemProcessor<OrderRecord, Order> {

	@Autowired
	private HashMap<Long, Customer> customerCache;

	@Autowired
	private USDRepositoryInterface usdRepository;

	private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

	@Override
	public Order process(OrderRecord item) {

		final LocalDateTime createdDate
				= formatLocalDateTime(item.getCreatedDate());

		final Double usdPrice
				= usdRepository.getPriceByDate(createdDate.toLocalDate());

		final Double usdParallelPrice
				= usdRepository.getParallelPriceByDate(createdDate.toLocalDate());

		final Double total = Double.valueOf(item.getTotal());
		final Double totalShipping = Double.valueOf(item.getTotalShipping());

		final Order order = Order.builder()
				.orderId(Long.valueOf(item.getOrderId()))
				.batchId(item.getBatchId())
				.createdDate(createdDate)
				.updatedDate(formatLocalDateTime(item.getUpdatedDate()))
				.submittedDate(formatLocalDateTime(item.getSubmittedDate()))
				.deliveryDate(formatLocalDateTime(item.getDeliveryDate()))
				.customer(formatCustomer(item.getCustomerId(), customerCache))
				.site(formatSite(item.getSiteCode()))
				.total(Double.valueOf(item.getTotal()))
				.totalUSD(total / usdPrice)
				.usdOfficialListing(usdPrice)
				.usdParallelListing(usdParallelPrice)
				.totalShipping(totalShipping)
				.totalShippingUSD(totalShipping / usdPrice)
				.trackingCode(item.getTrackingCode())
				.orderStatus(item.getOrderStatus())
				.gmvEnabled(Boolean.valueOf(item.getGmvEnabled()))
				.orderNumber(item.getOrderNumber())
				.shippingByTracking(Double.valueOf(item.getShippingByTracking()))
				.build();

		//log.info(String.valueOf(order));

		return order;
	}
}
