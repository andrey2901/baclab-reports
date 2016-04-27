package ua.com.hedgehogsoft.baclabreports.service;

import java.util.Date;

import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;

public class RemainsCounter
{
   private ProductRepository productRepository;
   private IncomingRepository incomingRepository;
   private OutcomingRepository outcomingRepository;

   public RemainsCounter(ProductRepository productRepository,
                         IncomingRepository incomingRepository,
                         OutcomingRepository outcomingRepository)
   {
      this.productRepository = productRepository;
      this.incomingRepository = incomingRepository;
      this.outcomingRepository = outcomingRepository;
   }

   public Product getRemainOfProductOnDate(long productId, Date destinationDate, Date today)
   {
      double incomingSum = incomingRepository.getIncomingsSum(productId, destinationDate, today);
      double outcomingSum = outcomingRepository.getOutcomingsSum(productId, destinationDate, today);
      Product product = productRepository.getProductById(productId);
      product.setAmount(product.getAmount() + outcomingSum - incomingSum);
      return product;
   }
}
