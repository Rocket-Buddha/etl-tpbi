package edu.palermo.master.ti.bi.orders;

import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.orders.entities.Order;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class OrdersConfiguration {

    public static final String ORDERS_RECORD_ITEM_READER = "ordersTypeRecordItemReader";
    @Autowired
    private DataSource dataSource;
    @Value("${readers.orders.path}")
    private String path;
    @Value("${readers.orders.delimiter}")
    private String delimiter;
    @Value("${readers.orders.lines-to-skip}")
    private int linesToSkip;
    @Value("${readers.orders.query}")
    private String query;

    @Bean
    public FlatFileItemReader<OrderRecord> ordersReader() {
        return new FlatFileItemReaderBuilder<OrderRecord>()
                .name(ORDERS_RECORD_ITEM_READER)
                .resource(new ClassPathResource(path))
                .linesToSkip(linesToSkip)
                .delimited()
                .delimiter(delimiter)
                .names("batch_id",
                        "created_date",
                        "updated_date",
                        "submitted_date",
                        "delivery_date",
                        "customer_id",
                        "order_id",
                        "site_code",
                        "total",
                        "total_shipping",
                        "tracking_code",
                        "order_status",
                        "gmv_enabled",
                        "order_number",
                        "shipping_by_tracking",
                        "latitude",
                        "longitude"
                )
                .fieldSetMapper(new BeanWrapperFieldSetMapper<OrderRecord>() {{
                    setTargetType(OrderRecord.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Order> ordersWriter() {
        return new JdbcBatchItemWriterBuilder<Order>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(false)
                .sql(query)
                .dataSource(dataSource)
                .build();
    }
}
