package com.example.demo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabasesConfiguration {

    @Value("${demo.db.datasource.initialize}")
    private boolean demoDbInitialize;

    @Value("classpath:/sql/schema-demodb.sql")
    private Resource demoDbSchema;

    @Value("classpath:/sql/data-demodb.sql")
    private Resource demoDbData;

    @Bean(name = "demoDbDatasource")
    @ConfigurationProperties(prefix = "demo.db.datasource")
    public DataSource getDemoDbDatasource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "demodbJdbcTemplate")
    public NamedParameterJdbcTemplate getDemoDbNamedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(getDemoDbDatasource());
    }

    @Bean(name = "demoDbPopulator")
    public DatabasePopulator getDemoDbDatabasePopulator(){
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        if(demoDbInitialize){
            resourceDatabasePopulator.addScript(demoDbSchema);
            resourceDatabasePopulator.addScript(demoDbData);
        }
        return resourceDatabasePopulator;
    }

    @Bean(name = "demoDbInitializer")
    public DataSourceInitializer getDemoDbDataSourceInitializer(){
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(getDemoDbDatasource());
        dataSourceInitializer.setDatabasePopulator(getDemoDbDatabasePopulator());
        return dataSourceInitializer;
    }

}
