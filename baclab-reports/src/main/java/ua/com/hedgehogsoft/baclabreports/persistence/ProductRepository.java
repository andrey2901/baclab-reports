package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
   @Query("SELECT DISTINCT p.name FROM Product p ORDER BY name ASC")
   List<String> getUniqueProductNamesOrderedByName();

   @Query("SELECT DISTINCT p.unit.name FROM Product p WHERE p.name = ?1")
   List<String> getUniqueUnitNamesByProductName(String productName);

   @Query("SELECT DISTINCT p.price FROM Product p WHERE p.name = ?1 AND p.unit.name = ?2")
   List<Double> getUniquePricesByProductNameAndUnitName(String productName, String unitName);
}
