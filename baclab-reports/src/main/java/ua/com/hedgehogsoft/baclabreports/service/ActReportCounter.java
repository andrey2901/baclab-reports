package ua.com.hedgehogsoft.baclabreports.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.cache.UnitCache;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.Queries;

public class ActReportCounter
{
   private EntityManager em;
   private SourceCache sourcesCache;
   private UnitCache unitsCache;

   public ActReportCounter(EntityManager em, SourceCache sourcesCache, UnitCache unitsCache)
   {
      this.em = em;
      this.sourcesCache = sourcesCache;
      this.unitsCache = unitsCache;
   }

   public List<Product> count(String dateFrom, String dateTo, String source)
   {
      List<Product> products = new ArrayList<Product>();
      @SuppressWarnings("unchecked")
      List<List<Object>> results = em
            .createNativeQuery(
                  Queries.getOutcomingSumsWithProductByDates(dateFrom, dateTo, sourcesCache.findByName(source).getId()))
            .getResultList();
      for (Object result : results)
      {
         Object[] rslt = (Object[]) result;
         Product product = new Product();
         if (rslt[0] instanceof BigInteger)
         {
            product.setId(((BigInteger) rslt[0]).longValue());
            product.setName((String) rslt[1]);
            product.setPrice((Double) rslt[2]);
            product.setAmount((Double) rslt[3]);
            product.setUnit(unitsCache.get(((BigInteger) rslt[4]).intValue()));
            product.setSource(sourcesCache.get(((BigInteger) rslt[5]).intValue()));
         }
         else
         {
            product.setId(Long.valueOf(Integer.toString((int) rslt[0])));
            product.setName((String) rslt[1]);
            product.setPrice((Double) rslt[2]);
            product.setAmount((Double) rslt[3]);
            product.setUnit(unitsCache.get((int) rslt[4]));
            product.setSource(sourcesCache.get((int) rslt[5]));
         }
         products.add(product);
      }
      return products;
   }
}
