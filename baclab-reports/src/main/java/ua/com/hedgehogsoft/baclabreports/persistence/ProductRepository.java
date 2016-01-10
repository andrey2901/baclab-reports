package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
   @Transactional(readOnly = true)
   @Query("SELECT DISTINCT p.name FROM Product p ORDER BY name ASC")
   List<String> getUniqueProductNamesOrderedByName();

   @Transactional(readOnly = true)
   @Query("SELECT DISTINCT p.unit.name FROM Product p WHERE p.name = ?1")
   List<String> getUniqueUnitNamesByProductName(String productName);

   @Transactional(readOnly = true)
   @Query("SELECT DISTINCT p.price FROM Product p WHERE p.name = :productName AND p.unit.name = :unitName")
   List<Double> getUniquePricesByProductNameAndUnitName(@Param("productName") String productName,
                                                        @Param("unitName") String unitName);

   @Transactional(readOnly = true)
   @Query("SELECT p FROM Product p WHERE p.name = ?1 AND p.price = ?2 AND p.source.id = ?3 AND p.unit.id = ?4")
   Product getProductByNameAndPriceAndSourceAndUnit(String name, Double price, Long sourceID, Long unitID);

   @Modifying
   @Transactional(readOnly = false)
   @Query("UPDATE Product p SET p.amount = :amount WHERE p.id = :productId")
   int updateAmount(@Param("productId") long productId, @Param("amount") double amount);
}
