package ua.com.hedgehogsoft.baclabreports.web;

import java.util.Date;

import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.hedgehogsoft.baclabreports.model.SourceType;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.RemainsTable;

@RestController
public class RemainsController
{
   private @Autowired RemainsTable remainsTable;
   private @Autowired ProductStorageTable productStorageTable;

   @RequestMapping("/remains")
   public String getRemains(@RequestParam String source)
   {
      String date = new DateLabelFormatter().dateToString(new Date());
      String remains = "<h2 align=\"center\">Залишок</h2>";
      remains += "<h3 align=\"center\">поживних середовищ і хімреактивів, лабораторного скла</h3>";
      remains += "<h3 align=\"center\">по Централізованій баклабораторії Лівобережжя КЗ \"Дніпропетровьска міська клінічна лікарня №9\" ДОР\"</h3>";
      if (SourceType.getType(source) != SourceType.BUDGET)
      {
         remains += "<h3 align=\"center\">\"" + source + "\"</h3>";
      }
      remains += "<h3 align=\"center\">на " + date + "</h3>";
      return printTableRemains(remains, remainsTable.init(date, source));
   }

   @RequestMapping("/remains/now")
   public String getRemains()
   {
      String date = new DateLabelFormatter().dateToString(new Date());
      String remains = "<h2 align=\"center\">Загальний залишок</h2>";
      remains += "<h3 align=\"center\">поживних середовищ і хімреактивів, лабораторного скла</h3>";
      remains += "<h3 align=\"center\">по Централізованій баклабораторії Лівобережжя КЗ \"Дніпропетровьска міська клінічна лікарня №9\" ДОР\"</h3>";
      remains += "<h3 align=\"center\">на " + date + "</h3>";
      JTable table = productStorageTable;
      return printTableRemains(remains, table);
   }

   private String printTableRemains(String remains, JTable table)
   {
      int columns = table.getColumnCount();
      int rows = table.getRowCount();

      remains += "<table width=\"70%\" border=\"1\" align=\"center\" cellpadding=\"15\" cellspacing=\"0\">";
      remains += "<tr>";
      for (int i = 0; i < columns; i++)
      {
         remains += "<th>";
         remains += table.getColumnName(i);
         remains += "</th>";
      }
      remains += "</tr>";
      for (int i = 0; i < rows; i++)
      {
         remains += "<tr>";
         for (int j = 0; j < columns; j++)
         {
            remains += "<td>";
            remains += table.getValueAt(i, j);
            remains += "</td>";
         }
         remains += "</tr>";
      }
      remains += "</table>";
      return remains;
   }
}
