package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

}
