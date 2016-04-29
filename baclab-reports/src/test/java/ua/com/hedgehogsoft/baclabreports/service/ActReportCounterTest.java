package ua.com.hedgehogsoft.baclabreports.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.cache.UnitCache;
import ua.com.hedgehogsoft.baclabreports.model.Product;

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

      products = actReportCounter.count("2016-01-01", "2016-01-31", source);
      assertTrue(products.isEmpty());

      products = actReportCounter.count("2016-03-01", "2016-03-31", source);
      assertEquals(1, products.size());
      assertEquals(24.5, products.get(0).getAmount(), 0.0);

      products = actReportCounter.count("2016-04-01", "2016-04-30", source);
      assertEquals(2, products.size());
      assertEquals(21.5, products.get(0).getAmount(), 0.0);
      assertEquals(19.0, products.get(1).getAmount(), 0.0);

      products = actReportCounter.count("2016-05-01", "2016-05-31", source);
      assertEquals(1, products.size());
      assertEquals(14.1, products.get(0).getAmount(), 0.0);

      products = actReportCounter.count("2016-06-01", "2016-06-30", source);
      assertTrue(products.isEmpty());
   }
}
