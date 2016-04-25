package ua.com.hedgehogsoft.baclabreports.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

public class IncomingRepositoryTest extends DefaultTest
{
   private @Autowired IncomingRepository incomingRepository;

   @Test
   public void getIncomingsSumFromDate()
   {
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date date = (Date) formatter.stringToValue("31.12.2015");
      assertEquals(40.0, incomingRepository.getIncomingsSum(1, date, new Date()), 0.0);
   }
}
