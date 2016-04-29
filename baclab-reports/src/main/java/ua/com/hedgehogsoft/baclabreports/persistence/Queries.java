package ua.com.hedgehogsoft.baclabreports.persistence;

public class Queries
{
   public final static String getOutcomingSumsWithProductByDates(String from, String to, long source_id)
   {
      return "SELECT p.id, p.name, p.price, SUM(o.amount), p.unit_id, p.source_id FROM products p "
            + "LEFT JOIN outcomings o ON p.id = o.product_id WHERE p.source_id = " + source_id + " AND o.date BETWEEN '"
            + from + "' AND '" + to + "' GROUP BY p.id ORDER BY p.id";
   }
}
