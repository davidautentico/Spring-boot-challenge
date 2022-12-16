package com.drosa.inditex.connect.boot.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class DataSourceConfig {

  private final InditexConnectProperties inditexConnectProperties;

  @Bean
  public DataSource getDataSource() {

    log.info("[CONNECTION] url: <{}> ", inditexConnectProperties.getDbUrl());

    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(inditexConnectProperties.getDbDriverClass());
    dataSourceBuilder.url(inditexConnectProperties.getDbUrl());
    dataSourceBuilder.username(inditexConnectProperties.getDbUserName());
    dataSourceBuilder.password(inditexConnectProperties.getDbPassword());

    return dataSourceBuilder.build();
  }
}
