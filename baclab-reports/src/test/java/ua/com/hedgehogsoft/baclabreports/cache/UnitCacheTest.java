package ua.com.hedgehogsoft.baclabreports.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.Unit;

public class UnitCacheTest extends DefaultTest
{
   private @Autowired UnitCache unitsCache;
   private Unit unit;

   @Before
   public void init()
   {
      unit = new Unit();
      unit.setName("TestUnit");
   }

   @Test
   public void size()
   {
      assertEquals(9, unitsCache.size());
      unitsCache.add(unit);
      assertEquals(10, unitsCache.size());
      unitsCache.remove(unit);
      assertEquals(9, unitsCache.size());
   }

   @Test
   public void findByName()
   {
      String unitName = "amp";
      /**
       * TODO: adds support of cyrillic to H2 db and change unitName to "амп"
       */
      Unit cachedUnit = unitsCache.findByName(unitName);
      assertEquals(unitName, cachedUnit.getName());
      assertNull(unitsCache.findByName("pma"));

      unitsCache.add(unit);
      cachedUnit = unitsCache.findByName(unit.getName());
      assertEquals(unit.getName(), cachedUnit.getName());
      unitsCache.remove(unit);
      assertNull(unitsCache.findByName(unit.getName()));
   }
}
