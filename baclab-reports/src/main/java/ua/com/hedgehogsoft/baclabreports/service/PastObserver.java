package ua.com.hedgehogsoft.baclabreports.service;

import java.util.Calendar;
import java.util.Date;

import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

/**
 * Check ability to insert an outcoming in the past (f.e. today is 24.05.2015
 * and the date of outcoming is 20.03.2015)
 */
public class PastObserver
{
   private ProductRepository productRepository;
   private IncomingRepository incomingRepository;
   private OutcomingRepository outcomingRepository;

   public PastObserver(ProductRepository productRepository,
                       IncomingRepository incomingRepository,
                       OutcomingRepository outcomingRepository)
   {
      this.productRepository = productRepository;
      this.incomingRepository = incomingRepository;
      this.outcomingRepository = outcomingRepository;
   }

   public boolean isRemovable(Product existedProduct, double amount, String date)
   {
      /*
       * Amount for today after deleting the incoming.
       */
      if (existedProduct.getAmount() - amount < 0)
      {
         return false;
      }

      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      Date today = cal.getTime();
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date destinationDate = (Date) formatter.stringToValue(date);
      RemainsCounter remainsCounter = new RemainsCounter(productRepository, incomingRepository, outcomingRepository);

      long productID = existedProduct.getId();

      while (destinationDate.before(today))
      {
         double remainsAmount = remainsCounter.getRemainOfProductOnDate(productID, destinationDate);
         if (remainsAmount < amount)
         {
            return false;
         }
         cal.setTime(destinationDate);
         cal.add(Calendar.DATE, 1);
         destinationDate = cal.getTime();
      }
      return true;
   }
}
