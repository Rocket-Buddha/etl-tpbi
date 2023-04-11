package edu.palermo.master.ti.bi.usd;

import edu.palermo.master.ti.bi.usd.dto.USDParallelRecord;
import edu.palermo.master.ti.bi.usd.dto.USDRecord;
import edu.palermo.master.ti.bi.usd.entities.USD;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class USDConfiguration {

    public static final String USD_RECORD_ITEM_READER = "USDRecordItemReader";
    public static final String USD_PARALLEL_RECORD_ITEM_READER = "USDParallelRecordItemReader";
    @Autowired
    @Qualifier("h2DataSource")
    private DataSource h2DataSource;
    @Value("${readers.usd.path}")
    private String path;
    @Value("${readers.usd.parallel-path}")
    private String parallelPath;
    @Value("${readers.usd.delimiter}")
    private String delimiter;
    @Value("${readers.usd.lines-to-skip}")
    private int linesToSkip;
    @Value("${readers.usd.query}")
    private String query;
    @Value("${readers.usd.parallel-query}")
    private String parallelQuery;

    @Bean
    public FlatFileItemReader<USDRecord> usdReader() {
        return new FlatFileItemReaderBuilder<USDRecord>()
                .name(USD_RECORD_ITEM_READER)
                .resource(new ClassPathResource(path))
                .linesToSkip(linesToSkip)
                .delimited()
                .delimiter(delimiter)
                .names("date",
                        "last",
                        "opening",
                        "max",
                        "min",
                        "vol",
                        "var")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<USDRecord>() {{
                    setTargetType(USDRecord.class);
                }})
                .build();
    }

    @Bean
    public FlatFileItemReader<USDParallelRecord> usdParallelReader() {
        return new FlatFileItemReaderBuilder<USDParallelRecord>()
                .name(USD_PARALLEL_RECORD_ITEM_READER)
                .resource(new ClassPathResource(parallelPath))
                .linesToSkip(linesToSkip)
                .delimited()
                .delimiter(delimiter)
                .names("fecha",
                        "ccl",
                        "mep",
                        "informal")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<USDParallelRecord>() {{
                    setTargetType(USDParallelRecord.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<USD> usdParallelWriter() {
        return new JdbcBatchItemWriterBuilder<USD>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(false)
                .sql(parallelQuery)
                .dataSource(h2DataSource)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<USD> usdWriter() {
        return new JdbcBatchItemWriterBuilder<USD>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(false)
                .sql(query)
                .dataSource(h2DataSource)
                .build();
    }
}
