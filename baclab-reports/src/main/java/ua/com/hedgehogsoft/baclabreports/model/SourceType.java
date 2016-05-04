package ua.com.hedgehogsoft.baclabreports.model;

public enum SourceType
{
   BUDGET("Реактиви, поживні середовища"), MECENAT("Меценат"), PROVISOR("Від провізора"), DEZINFECTOR(
         "Від дезінфектора");

   private String value;

   SourceType(String str)
   {
      value = str;
   };

   @Override
   public String toString()
   {
      return value;
   }

   public static SourceType getType(String type)
   {
      switch (type)
      {
         case "Реактиви, поживні середовища":
            return SourceType.BUDGET;
         case "Меценат":
            return SourceType.MECENAT;
         case "Від провізора":
            return SourceType.PROVISOR;
         case "Від дезінфектора":
            return SourceType.DEZINFECTOR;
      }
      return null;
   }
}
