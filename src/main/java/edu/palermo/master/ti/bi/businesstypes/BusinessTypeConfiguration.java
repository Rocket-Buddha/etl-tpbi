package edu.palermo.master.ti.bi.businesstypes;

import edu.palermo.master.ti.bi.businesstypes.dto.BusinessTypeRecord;
import edu.palermo.master.ti.bi.businesstypes.entities.BusinessType;
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
public class BusinessTypeConfiguration {

    public static final String BUSINESS_TYPE_RECORD_ITEM_READER = "businessTypeRecordItemReader";
    @Autowired
    private DataSource dataSource;
    @Value("${readers.business-type.path}")
    private String path;
    @Value("${readers.business-type.delimiter}")
    private String delimiter;
    @Value("${readers.business-type.lines-to-skip}")
    private int linesToSkip;
    @Value("${readers.business-type.query}")
    private String query;

    @Bean
    public FlatFileItemReader<BusinessTypeRecord> businessTypeReader(){
        return new FlatFileItemReaderBuilder<BusinessTypeRecord>()
                .name(BUSINESS_TYPE_RECORD_ITEM_READER)
                .resource(new ClassPathResource(path))
                .linesToSkip(linesToSkip)
                .delimited()
                .delimiter(delimiter)
                .names("initcap", "active", "business_type_id")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<BusinessTypeRecord>() {{
                    setTargetType(BusinessTypeRecord.class);
                }})
                .build();
    }
    @Bean
    public JdbcBatchItemWriter<BusinessType> businessTypeWriter() {
        return new JdbcBatchItemWriterBuilder<BusinessType>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .assertUpdates(false)
                .sql(query)
                .dataSource(dataSource)
                .build();
    }
}
