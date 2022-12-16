package com.drosa.inditex.connect.infrastructure.repositories;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.repositories.PriceProductRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PriceProductRepositoryImpl implements PriceProductRepository {

  private final JdbcTemplate jdbcTemplateObject;

  private final String tableName = "prices";

  private final String SELECT_PRICES_QUERY = "SELECT Brand_Id,Product_Id,Price_List,Start_Date,End_Date,Priority,Price,Curr," +
      "Last_Update,Last_Update_By FROM " + tableName +
      " WHERE Brand_id= ? AND Product_Id = ? AND Start_Date <= ? AND End_Date >= ? ORDER BY priority DESC FETCH FIRST 1 ROWS ONLY";

  /**
   * it selects all allowed prices. If there are more than one, it takes the most priority one. For this reason, the query is optimized."
   * Not mapStruct needed here. Since the table is an ad-hoc one we use that info as domain entity directly.
   */
  public ProductPrice get(int brandId, int productId, LocalDateTime date) {

    try {
      return this.jdbcTemplateObject.queryForObject(SELECT_PRICES_QUERY,
          new Object[]{brandId, productId, date, date}, (rs, rowNum) ->
               ProductPrice.builder()
                  .productId(productId)
                  .brandId(brandId)
                  .tariffRate(rs.getInt("Price_List"))
                  .priority(rs.getInt("Priority"))
                  .startDate(rs.getTimestamp("Start_Date").toLocalDateTime())
                  .endDate(rs.getTimestamp("End_Date").toLocalDateTime())
                  .price(rs.getBigDecimal("Price"))
                  .lastUpdateDate(rs.getTimestamp("Last_Update").toLocalDateTime())
                  .lastUpdateBy(rs.getString("Last_Update_By"))
                  .build()
      );
    } catch (Exception e) {
      log.error("Error reading from database for values brandId {}, productId {} and date {}", brandId, productId, date);
      throw e;
    }
  }
}
