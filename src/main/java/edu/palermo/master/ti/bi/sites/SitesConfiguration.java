package edu.palermo.master.ti.bi.sites;

import edu.palermo.master.ti.bi.sites.entities.Site;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SitesConfiguration {
    @Autowired
    @Qualifier("postgresDataSource")
    private DataSource postgresDataSource;
    @Value("${readers.sites.query}")
    private String query;

    @Bean
    public JdbcBatchItemWriter<Site> siteWriter() {
        return new JdbcBatchItemWriterBuilder<Site>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(query)
                .assertUpdates(false)
                .dataSource(postgresDataSource)
                .build();
    }
}
