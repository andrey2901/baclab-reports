package ua.com.hedgehogsoft.baclabreports.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;

public class PastObserverTest extends DefaultTest
{
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired ProductRepository productRepository;

   @Test
   public void reversible()
   {
      String date = "05.01.2016";
      PastObserver past = new PastObserver(incomingRepository, outcomingRepository);
      Product existedProduct = productRepository.findOne(3L);
      assertTrue(past.isRemovable(existedProduct, 2.5, date));
      assertFalse(past.isRemovable(existedProduct, 2.6, date));
   }
}
