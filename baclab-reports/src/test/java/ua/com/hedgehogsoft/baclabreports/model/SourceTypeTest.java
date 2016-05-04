package ua.com.hedgehogsoft.baclabreports.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;

public class SourceTypeTest extends DefaultTest
{
   @Test
   public void test()
   {
      assertEquals(SourceType.BUDGET, SourceType.getType("Реактиви, поживні середовища"));
      assertEquals(SourceType.MECENAT, SourceType.getType("Меценат"));
      assertEquals(SourceType.PROVISOR, SourceType.getType("Від провізора"));
      assertEquals(SourceType.DEZINFECTOR, SourceType.getType("Від дезінфектора"));
   }
}
