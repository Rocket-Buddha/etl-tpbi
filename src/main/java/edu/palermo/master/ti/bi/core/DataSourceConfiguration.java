package edu.palermo.master.ti.bi.core;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Value("${h2.datasource.driver}")
    private String h2DataSourceDriver;

    @Value("${h2.datasource.url}")
    private String h2DataSourceUrl;

    @Value("${h2.datasource.user}")
    private String h2DataSourceUser;

    @Value("${h2.datasource.password}")
    private String h2DataSourcePassword;

    @Value("${postgres.datasource.driver}")
    private String postgresDataSourceDriver;

    @Value("${postgres.datasource.url}")
    private String postgresDataSourceUrl;

    @Value("${postgres.datasource.user}")
    private String postgresDataSourceUser;

    @Value("${postgres.datasource.password}")
    private String postgresDataSourcePassword;

    @Primary
    @Bean(name = "h2DataSource")
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(h2DataSourceDriver);
        dataSource.setUrl(h2DataSourceUrl);
        dataSource.setUsername(h2DataSourceUser);
        dataSource.setPassword(h2DataSourcePassword);
        return dataSource;
    }

    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresDataSourceDriver);
        dataSource.setUrl(postgresDataSourceUrl);
        dataSource.setUsername(postgresDataSourceUser);
        dataSource.setPassword(postgresDataSourcePassword);
        return dataSource;
    }

    @Bean
    public DataSourceInitializer h2DataSourceInitializer(@Qualifier("h2DataSource") DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/h2/schema-all.sql"));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public DataSourceInitializer postgresDataSourceInitializer(@Qualifier("postgresDataSource") DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/postgres/schema-all.sql"));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
