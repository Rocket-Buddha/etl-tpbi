package edu.palermo.master.ti.bi.customers;

import edu.palermo.master.ti.bi.customers.dto.CustomerRecord;
import edu.palermo.master.ti.bi.customers.entities.Customer;
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
public class CustomersConfiguration {

    public static final String CUSTOMERS_RECORD_ITEM_READER = "customersRecordItemReader";
    @Autowired
    private DataSource dataSource;
    @Value("${readers.customers.path}")
    private String path;
    @Value("${readers.customers.delimiter}")
    private String delimiter;
    @Value("${readers.customers.lines-to-skip}")
    private int linesToSkip;
    @Value("${readers.customers.query}")
    private String query;

    @Bean
    public FlatFileItemReader<CustomerRecord> customerReader() {
        return new FlatFileItemReaderBuilder<CustomerRecord>()
                .name(CUSTOMERS_RECORD_ITEM_READER)
                .resource(new ClassPathResource(path))
                .linesToSkip(linesToSkip)
                .delimited()
                .delimiter(delimiter)
                .names("customer_id",
                        "email_address",
                        "name",
                        "business_type_id",
                        "site_code",
                        "archived",
                        "is_key_account",
                        "date_updated",
                        "date_created")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CustomerRecord>() {{
                    setTargetType(CustomerRecord.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Customer> customerWriter() {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(query)
                .dataSource(dataSource)
                .build();
    }
}
