package ua.com.hedgehogsoft.baclabreports.service;

import java.util.Calendar;
import java.util.Date;

import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

/**
 * Check ability to insert an outcoming in the past (f.e. today is 24.05.2015
 * and the date of outcoming is 20.03.2015)
 */
public class PastObserver
{
   private IncomingRepository incomingRepository;
   private OutcomingRepository outcomingRepository;

   public PastObserver(IncomingRepository incomingRepository, OutcomingRepository outcomingRepository)
   {
      this.incomingRepository = incomingRepository;
      this.outcomingRepository = outcomingRepository;
   }

   public boolean isReversible(Product existedProduct, double outcomingAmount, String date)
   {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      Date today = cal.getTime();
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date destinationDate = (Date) formatter.stringToValue(date);

      long productID = existedProduct.getId();

      while (destinationDate.before(today))
      {
         double remainsAmount = getRemainsAmount(productID, existedProduct.getAmount(), destinationDate, today);
         if (remainsAmount < outcomingAmount)
         {
            return false;
         }
         cal.setTime(destinationDate);
         cal.add(Calendar.DATE, 1);
         destinationDate = cal.getTime();
      }
      return true;
   }

   private double getRemainsAmount(long productID, double remainsForToday, Date destinationDate, Date today)
   {
      double incomingsSum = incomingRepository.getIncomingsSum(productID, destinationDate, today);
      double outcomingsSum = outcomingRepository.getOutcomingsSum(productID, destinationDate, today);
      return remainsForToday + outcomingsSum - incomingsSum;
   }
}
