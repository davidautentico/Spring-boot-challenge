package com.drosa.inditex.connect.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Getter
@Setter
public class InditexConnectProperties {

  private String dbType;

  private String dbDriverClass;

  private String dbUrl;

  private String dbUserName;

  private String dbPassword;

  private String dbTableName;
}
