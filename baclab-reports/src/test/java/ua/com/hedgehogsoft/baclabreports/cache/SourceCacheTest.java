package ua.com.hedgehogsoft.baclabreports.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.Source;

public class SourceCacheTest extends DefaultTest
{
   private @Autowired SourceCache sourcesCache;
   private Source source;

   @Before
   public void init()
   {
      source = new Source();
      source.setName("TestSource");
   }

   @Test
   public void size()
   {
      assertEquals(4, sourcesCache.size());
      sourcesCache.add(source);
      assertEquals(5, sourcesCache.size());
      sourcesCache.remove(source);
      assertEquals(4, sourcesCache.size());
   }

   @Test
   public void findByName()
   {
      String sourceName = "Mecenat";
      /**
       * TODO: adds support of cyrillic to H2 db and change unitName to
       * "Меценат"
       */
      Source cachedSource = sourcesCache.findByName(sourceName);
      assertEquals(sourceName, cachedSource.getName());
      assertNull(sourcesCache.findByName("Tanecem"));

      sourcesCache.add(source);
      cachedSource = sourcesCache.findByName(source.getName());
      assertEquals(source.getName(), cachedSource.getName());
      sourcesCache.remove(source);
      assertNull(sourcesCache.findByName(source.getName()));
   }
}
