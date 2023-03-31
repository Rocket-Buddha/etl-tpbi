package edu.palermo.master.ti.bi.orders;

import edu.palermo.master.ti.bi.customers.entities.Customer;
import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.orders.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

import static edu.palermo.master.ti.bi.utils.FormatUtils.*;

@Component
public class OrderProcessor implements ItemProcessor<OrderRecord, Order> {

	@Autowired
	private HashMap<Long, Customer> customerCache;

	private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

	@Override
	public Order process(OrderRecord item) {
		final Order order = Order.builder()
				.orderId(Long.valueOf(item.getOrderId()))
				.batchId(item.getBatchId())
				.createdDate(formatLocalDateTime(item.getCreatedDate()))
				.updatedDate(formatLocalDateTime(item.getUpdatedDate()))
				.submittedDate(formatLocalDateTime(item.getSubmittedDate()))
				.deliveryDate(formatLocalDateTime(item.getDeliveryDate()))
				.customer(formatCustomer(item.getCustomerId(), customerCache))
				.site(formatSite(item.getSiteCode()))
				.total(Double.valueOf(item.getTotal()))
				.totalShipping(Double.valueOf(item.getTotalShipping()))
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
