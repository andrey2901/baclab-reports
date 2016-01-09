package ua.com.hedgehogsoft.baclabreports.ui.swing.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField.AbstractFormatter;

import org.apache.log4j.Logger;

public class DateLabelFormatter extends AbstractFormatter
{
   private static final long serialVersionUID = 1L;
   private static final Logger logger = Logger.getLogger(DateLabelFormatter.class);
   private String datePattern = "dd.MM.yyyy";
   private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

   @Override
   public Object stringToValue(String text)
   {
      Object result = null;
      try
      {
         result = dateFormatter.parseObject(text);
      }
      catch (ParseException e)
      {
         logger.error("Date parsing from string error.", e);
      }
      return result;
   }

   @Override
   public String valueToString(Object value)
   {
      if (value != null)
      {
         Calendar cal = (Calendar) value;
         return dateFormatter.format(cal.getTime());
      }

      return "";
   }

   public String dateToString(Date date)
   {
      if (date != null)
      {
         return dateFormatter.format(date);
      }
      return "";
   }

}