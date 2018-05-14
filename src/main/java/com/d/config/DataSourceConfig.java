package com.d.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
public class DataSourceConfig {

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.test1")
	@Primary
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}
}
