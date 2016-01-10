package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ua.com.hedgehogsoft.baclabreports.model.Product;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long>
{
   @Query("SELECT DISTINCT p.name FROM Product p ORDER BY p.name ASC")
   List<String> getUniqueProductNamesOrderedByName();

   @Query("SELECT DISTINCT p.name FROM Product p WHERE p.source.name = ?1 ORDER BY p.name ASC")
   List<String> getUniqueProductNamesBySource(String sourceName);

   @Query("SELECT DISTINCT p.unit.name FROM Product p WHERE p.name = ?1 ORDER BY p.unit.name ASC")
   List<String> getUniqueUnitNamesByProductName(String productName);

   @Query("SELECT DISTINCT p.unit.name FROM Product p WHERE p.name = ?1 AND p.source.name = ?2 ORDER BY p.unit.name ASC")
   List<String> getUniqueUnitNamesByProductNameAndSourceName(String productName, String sourceName);

   @Query("SELECT DISTINCT p.price FROM Product p WHERE p.name = ?1 AND p.unit.name = ?2 ORDER BY p.price ASC")
   List<Double> getUniquePricesByProductNameAndUnitName(String productName, String unitName);

   @Query("SELECT DISTINCT p.price FROM Product p WHERE p.name = ?1 AND p.source.name = ?2 AND p.unit.name = ?3 ORDER BY p.price ASC")
   List<Double> getUniquePricesByProductNameAndSourceNameAndUnitName(String productName,
                                                                     String sourceName,
                                                                     String unitName);

   @Query("SELECT p.amount FROM Product p WHERE p.name = ?1 AND p.source.name = ?2 AND p.unit.name = ?3 AND p.price = ?4")
   Double getAmountByProductNameAndSourceNameAndUnitNameAndPrice(String productName,
                                                                 String sourceName,
                                                                 String unitName,
                                                                 double price);

   @Query("SELECT p FROM Product p WHERE p.name = ?1 AND p.price = ?2 AND p.source.id = ?3 AND p.unit.id = ?4")
   Product getProductByNameAndPriceAndSourceAndUnit(String name, Double price, Long sourceID, Long unitID);

   @Modifying
   @Transactional(readOnly = false)
   @Query("UPDATE Product p SET p.amount = :amount WHERE p.id = :productId")
   int updateAmount(@Param("productId") long productId, @Param("amount") double amount);
}
