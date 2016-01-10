package ua.com.hedgehogsoft.baclabreports.ui.swing.date;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class DateLabelFormatterTest
{
   @Test
   public void stringToValue()
   {
      Date date = (Date) new DateLabelFormatter().stringToValue("10.01.2016");
      assertEquals(1452376800000L, date.getTime());
   }
}
