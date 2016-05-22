package ua.com.hedgehogsoft.baclabreports.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.cache.UnitCache;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

public class ActReportCounterTest extends DefaultTest
{
   private @PersistenceContext EntityManager em;
   private @Autowired SourceCache sourcesCache;
   private @Autowired UnitCache unitsCache;
   private List<Product> products;

   @Test
   public void count()
   {
      String source = "Mecenat";
      ActReportCounter actReportCounter = new ActReportCounter(em, sourcesCache, unitsCache);

      DateLabelFormatter formatter = new DateLabelFormatter();

      products = actReportCounter.count((Date) formatter.stringToValue("01.01.2015"),
            (Date) formatter.stringToValue("31.01.2015"), source);
      assertTrue(products.isEmpty());

      products = actReportCounter.count((Date) formatter.stringToValue("01.03.2015"),
            (Date) formatter.stringToValue("31.03.2015"), source);
      assertEquals(1, products.size());
      assertEquals(24.5, products.get(0).getAmount(), 0.0);

      products = actReportCounter.count((Date) formatter.stringToValue("01.04.2015"),
            (Date) formatter.stringToValue("30.04.2015"), source);
      assertEquals(2, products.size());
      assertEquals(21.5, products.get(0).getAmount(), 0.0);
      assertEquals(19.0, products.get(1).getAmount(), 0.0);

      products = actReportCounter.count((Date) formatter.stringToValue("01.05.2015"),
            (Date) formatter.stringToValue("31.05.2015"), source);
      assertEquals(1, products.size());
      assertEquals(14.1, products.get(0).getAmount(), 0.0);

      products = actReportCounter.count((Date) formatter.stringToValue("01.06.2015"),
            (Date) formatter.stringToValue("30.06.2015"), source);
      assertTrue(products.isEmpty());
   }
}
