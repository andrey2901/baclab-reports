package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
   @Query("SELECT DISTINCT p.name FROM Product p")
   List<String> getUniqueProductNames();
}
