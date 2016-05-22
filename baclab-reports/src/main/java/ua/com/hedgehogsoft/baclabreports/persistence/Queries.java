package ua.com.hedgehogsoft.baclabreports.persistence;

public class Queries
{
   public final static String OUTCOMINGS_SUMS_WITH_PRODUCT_BY_DATES = "SELECT p.id, p.name, p.price, SUM(o.amount), p.unit_id, p.source_id "
         + "FROM products p JOIN outcomings o ON p.id = o.product_id "
         + "WHERE p.source_id = ? AND o.date BETWEEN ? AND ? GROUP BY p.id ORDER BY p.id";
}
