package ua.com.hedgehogsoft.baclabreports.service;

import java.util.Calendar;
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

   public double getRemainOfProductOnDate(long productId, Date destinationDate)
   {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      Date today = cal.getTime();
      double incomingSum = incomingRepository.getIncomingsSumFromPeriod(productId, destinationDate, today);
      double outcomingSum = outcomingRepository.getOutcomingsSumFromPeriod(productId, destinationDate, today);
      Product product = productRepository.getProductById(productId);
      return product.getAmount() + outcomingSum - incomingSum;
   }
}
