package com.drosa.inditex.connect.boot;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.drosa.inditex.connect"})
public class InditexConnectAdapter {

  private static final Function<String, ProductPrice> csv2PrecioObj = (line) -> {
    String[] p = line.split(",");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

    return ProductPrice.builder()
        .productId(Integer.parseInt(p[4]))
        .brandId(Integer.parseInt(p[0]))
        .tariffRate(Integer.parseInt(p[3]))
        .priority(Integer.parseInt(p[5]))
        .startDate(LocalDateTime.parse(p[1], formatter))
        .endDate(LocalDateTime.parse(p[2], formatter))
        .price(BigDecimal.valueOf(Double.parseDouble(p[6])))
        .lastUpdateDate(LocalDateTime.parse(p[8], formatter))
        .lastUpdateBy(p[9])
        .build();
  };

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public static void main(String[] args) {

    SpringApplication.run(InditexConnectAdapter.class, args);

    Runtime.getRuntime().addShutdownHook(new Thread(InditexConnectAdapter::shutdown));

    log.info("***************** InditexConnectAdapter Started *******************");
  }

  private static void shutdown() {

    log.info("***************** InditexConnectAdapter Stopped *******************");
  }

  private List<ProductPrice> processInputFile(String inputFilePath) {

    List<ProductPrice> inputList;

    try {
      File inputF = new File(inputFilePath);
      InputStream inputFS = new FileInputStream(inputF);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

      inputList = br.lines().skip(1).map(csv2PrecioObj).collect(Collectors.toList());
      br.close();
    } catch (IOException e) {
      log.info("error reading file: <{}>", e.getMessage());
      return null;
    }

    return inputList;
  }

  @PostConstruct
  private void initDb() {
    log.info("****** Creating table <{}> and Inserting test data ******", "Prices");

    String[] sqlStatements = {
        "DROP TABLE prices IF EXISTS",
        "CREATE TABLE prices(Brand_Id int,Start_Date TIMESTAMP,End_Date TIMESTAMP,Price_List int,Product_Id int," +
            "Priority int,Price DECIMAL(20, 2),Curr varchar(3),Last_Update TIMESTAMP,Last_Update_By varchar(64))"
    };

    Arrays.stream(sqlStatements).forEach(sql -> {
      System.out.println(sql);
      jdbcTemplate.execute(sql);
    });

    //read csv
    String INSERT_QUERY = "INSERT INTO prices VALUES(?,?,?,?,?,?,?,?,?,?)";
    List<ProductPrice> precioList = processInputFile("src/main/resources/prices.csv");
    precioList.forEach((final ProductPrice productPrice) -> {
          log.info("Precio <{}>", productPrice);
          jdbcTemplate.update(INSERT_QUERY,
              productPrice.getBrandId(),
              productPrice.getStartDate(),
              productPrice.getEndDate(),
              productPrice.getTariffRate(),
              productPrice.getProductId(),
              productPrice.getPriority(),
              productPrice.getPrice(),
              "EUR",
              productPrice.getLastUpdateDate(),
              productPrice.getLastUpdateBy()
          );
        }
    );

  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server inMemoryH2DatabaseServer() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9091");
  }

}
