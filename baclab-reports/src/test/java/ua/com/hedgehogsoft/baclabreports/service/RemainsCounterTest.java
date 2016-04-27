package ua.com.hedgehogsoft.baclabreports.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

public class RemainsCounterTest extends DefaultTest
{
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired ProductRepository productRepository;

   @Test
   public void getRemains()
   {
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date today = (Date) formatter.stringToValue("10.01.2016");

      Date date = (Date) formatter.stringToValue("03.01.2016");
      RemainsCounter remains = new RemainsCounter(productRepository, incomingRepository, outcomingRepository);
      Product product = remains.getRemainOfProductOnDate(3, date, new Date());
      assertEquals(15, product.getAmount(), 0.0);

      date = (Date) formatter.stringToValue("03.01.2016");
      product = remains.getRemainOfProductOnDate(3, date, today);
      assertNotEquals(15, product.getAmount(), 0.0);

      date = (Date) formatter.stringToValue("07.01.2016");
      product = remains.getRemainOfProductOnDate(3, date, new Date());
      assertEquals(11.5, product.getAmount(), 0.0);

      date = (Date) formatter.stringToValue("07.01.2016");
      product = remains.getRemainOfProductOnDate(3, date, today);
      assertNotEquals(11.5, product.getAmount(), 0.0);

      date = (Date) formatter.stringToValue("09.01.2016");
      product = remains.getRemainOfProductOnDate(3, date, new Date());
      assertEquals(7.5, product.getAmount(), 0.0);

      date = (Date) formatter.stringToValue("09.01.2016");
      product = remains.getRemainOfProductOnDate(3, date, today);
      assertNotEquals(7.5, product.getAmount(), 0.0);
   }
}
