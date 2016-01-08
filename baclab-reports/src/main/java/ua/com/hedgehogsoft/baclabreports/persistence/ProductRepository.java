package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>
{

}
