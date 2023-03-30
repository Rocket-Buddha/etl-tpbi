package edu.palermo.master.ti.bi.sites;

import edu.palermo.master.ti.bi.businesstypes.dto.BusinessTypeRecord;
import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
import edu.palermo.master.ti.bi.sites.entities.Site;
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
public class SitesConfiguration {
    @Autowired
    private DataSource dataSource;
    @Value("${readers.sites.query}")
    private String query;

    @Bean
    public JdbcBatchItemWriter<Site> siteWriter() {
        return new JdbcBatchItemWriterBuilder<Site>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(query)
                .assertUpdates(false)
                .dataSource(dataSource)
                .build();
    }
}
