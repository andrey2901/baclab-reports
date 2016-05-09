package ua.com.hedgehogsoft.baclabreports.service;

import static org.junit.Assert.assertEquals;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
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
      Date date = (Date) formatter.stringToValue("03.01.2015");

      RemainsCounter remains = new RemainsCounter(productRepository, incomingRepository, outcomingRepository);
      double remainOnDate = remains.getRemainOfProductOnDate(3, date);
      assertEquals(15, remainOnDate, 0.0);

      date = (Date) formatter.stringToValue("07.01.2015");
      remainOnDate = remains.getRemainOfProductOnDate(3, date);
      assertEquals(11.5, remainOnDate, 0.0);

      date = (Date) formatter.stringToValue("09.01.2015");
      remainOnDate = remains.getRemainOfProductOnDate(3, date);
      assertEquals(7.5, remainOnDate, 0.0);
   }
}
