package edu.palermo.master.ti.bi.core;

import javax.sql.DataSource;

import edu.palermo.master.ti.bi.businesstypes.dto.BusinessTypeRecord;
import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
import edu.palermo.master.ti.bi.customers.dto.CustomerRecord;
import edu.palermo.master.ti.bi.customers.entities.Customer;
import edu.palermo.master.ti.bi.orders.dto.OrderRecord;
import edu.palermo.master.ti.bi.orders.entities.Order;
import edu.palermo.master.ti.bi.sites.entities.Site;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@Configuration
public class BatchConfiguration {

    public static final String ETL_JOB = "etlJob";
    public static final String LOAD_BUSINESS_TYPES_STEP = "loadBusinessTypesStep";
    public static final String LOAD_SITES_FROM_ORDERS_STEP = "loadSitesFromOrdersStep";
    public static final String LOAD_SITES_FROM_CUSTOMERS_STEP = "loadSitesFromCustomersStep";
    public static final String LOAD_CUSTOMERS_STEP = "loadCustomersStep";
    public static final String LOAD_ORDERS_STEP = "loadOrdersStep";
    @Value("${batch.chunk-size}")
    private int chunkSize;
    @Value("${data-source.driver}")
    private String dataSourceDriver;
    @Value("${data-source.url}")
    private String dataSourceUrl;
    @Value("${data-source.user}")
    private String dataSourceUser;
    @Value("${data-source.password}")
    private String dataSourcePassword;

    @Bean
    public Job etlJob(JobRepository jobRepository,
                      JobCompletionNotificationListener listener,
                      Step loadBusinessTypesStep,
                      Step loadSitesFromOrdersStep,
                      Step loadSitesFromCustomersStep,
                      Step loadCustomersStep,
                      Step loadOrdersStep) {
        return new JobBuilder(ETL_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(loadBusinessTypesStep)
                .next(loadSitesFromOrdersStep)
                .next(loadSitesFromCustomersStep)
                .next(loadCustomersStep)
                .next(loadOrdersStep)
                .end()
                .build();
    }

    @Bean
    public Step loadBusinessTypesStep(JobRepository jobRepository,
                                      PlatformTransactionManager transactionManager,
                                      FlatFileItemReader<BusinessTypeRecord> businessTypeReader,
                                      ItemProcessor<BusinessTypeRecord, BusinessType> businessTypeProcessor,
                                      JdbcBatchItemWriter<BusinessType> businessTypeWriter) {
        return new StepBuilder(LOAD_BUSINESS_TYPES_STEP, jobRepository)
                .<BusinessTypeRecord, BusinessType>chunk(chunkSize, transactionManager)
                .reader(businessTypeReader)
                .processor(businessTypeProcessor)
                .writer(businessTypeWriter)
                .build();
    }

    @Bean
    public Step loadSitesFromOrdersStep(JobRepository jobRepository,
                                        PlatformTransactionManager transactionManager,
                                        FlatFileItemReader<OrderRecord> orderReader,
                                        ItemProcessor<OrderRecord, Site> siteProcessor,
                                        JdbcBatchItemWriter<Site> siteWriter) {
        return new StepBuilder(LOAD_SITES_FROM_ORDERS_STEP, jobRepository)
                .<OrderRecord, Site>chunk(chunkSize, transactionManager)
                .reader(orderReader)
                .processor(siteProcessor)
                .writer(siteWriter)
                .build();
    }

    @Bean
    public Step loadSitesFromCustomersStep(JobRepository jobRepository,
                                        PlatformTransactionManager transactionManager,
                                        FlatFileItemReader<CustomerRecord> customerReader,
                                        ItemProcessor<CustomerRecord, Site> siteProcessor,
                                        JdbcBatchItemWriter<Site> siteWriter) {
        return new StepBuilder(LOAD_SITES_FROM_CUSTOMERS_STEP, jobRepository)
                .<CustomerRecord, Site>chunk(chunkSize, transactionManager)
                .reader(customerReader)
                .processor(siteProcessor)
                .writer(siteWriter)
                .build();
    }

    @Bean
    public Step loadCustomersStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              FlatFileItemReader<CustomerRecord> customerReader,
                              ItemProcessor<CustomerRecord, Customer> customerProcessor,
                              JdbcBatchItemWriter<Customer> customerWriter) {
        return new StepBuilder(LOAD_CUSTOMERS_STEP, jobRepository)
                .<CustomerRecord, Customer>chunk(chunkSize, transactionManager)
                .reader(customerReader)
                .processor(customerProcessor)
                .writer(customerWriter)
                .build();
    }

    @Bean
    public Step loadOrdersStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  FlatFileItemReader<OrderRecord> orderReader,
                                  ItemProcessor<OrderRecord, Order> orderProcessor,
                                  JdbcBatchItemWriter<Order> orderWriter) {
        return new StepBuilder(LOAD_ORDERS_STEP, jobRepository)
                .<OrderRecord, Order>chunk(chunkSize, transactionManager)
                .reader(orderReader)
                .processor(orderProcessor)
                .writer(orderWriter)
                .build();
    }

    @Bean
    public HashMap<Long, Customer> customerCache(){
        return new HashMap<>();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceDriver);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUser);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }
}
