package ua.com.hedgehogsoft.baclabreports.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.Unit;

public class UnitCacheTest extends DefaultTest
{
   private @Autowired UnitCache unitsCache;
   
   @Test
   public void size()
   {
      assertEquals(9, unitsCache.size());
   }
   
   @Test
   public void findByName()
   {
      String unitName = "amp";
      /**TODO: adds support of cyrillic to H2 db and change unitName to "амп"*/ 
      Unit unit = unitsCache.findByName(unitName);
      assertEquals(unitName, unit.getName());
      assertNull(unitsCache.findByName("pma"));
   }
}
