package ua.com.hedgehogsoft.baclabreports.persistence.crud;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public class ProductRepositoryTest extends CrudRepositoryTest<Product>
{
   public ProductRepositoryTest()
   {
      fullFileName = "product.json";
   }
}
