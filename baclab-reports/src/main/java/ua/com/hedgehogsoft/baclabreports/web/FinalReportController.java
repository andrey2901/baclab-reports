package ua.com.hedgehogsoft.baclabreports.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JTable;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.hedgehogsoft.baclabreports.print.pdf.FinalReportPrinter;
import ua.com.hedgehogsoft.baclabreports.service.DateRange;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.FinalReportTable;

@RestController
public class FinalReportController
{
   private @Autowired FinalReportTable table;
   private @Autowired FinalReportPrinter printer;

   @RequestMapping("/report/final")
   public String getFinalReport(@RequestParam String month, @RequestParam String year)
   {
      DateRange ranger = new DateRange(Integer.parseInt(month), Integer.parseInt(year));
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      String finalReport = "<h2 align=\"center\">Звіт</h2>";
      finalReport += "<h3 align=\"center\">про надходження і відпуск (використання)</h3>";
      finalReport += "<h3 align=\"center\">лікарських засобів та медичних виробів</h3>";
      finalReport += "<h3 align=\"center\">з " + dateFrom + " до " + dateTo + "</h3>";
      finalReport = printFinalReport(finalReport, table.init(dateFrom, dateTo));
      finalReport += "<a href=\"/baclab/download?month=" + month + "&year=" + year + "\">Download</a>";
      return finalReport;
   }

   @RequestMapping(value = "/download", method = RequestMethod.GET)
   public void download(@RequestParam String month,
                        @RequestParam String year,
                        HttpServletRequest request,
                        HttpServletResponse response)
         throws IOException
   {
      String browserType = request.getHeader("User-Agent");
      DateRange ranger = new DateRange(Integer.parseInt(month), Integer.parseInt(year));
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      File file = printer.print(table.init(dateFrom, dateTo), true, dateFrom, dateTo);
      response.setContentType("application/pdf; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      String fileName = URLEncoder.encode(file.getName(), "UTF-8");
      if (browserType.contains("Firefox") || browserType.contains("MSIE"))
         response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
      else
         response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
      InputStream is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
   }

   private String printFinalReport(String finalReport, JTable table)
   {
      int columns = table.getColumnCount();
      int rows = table.getRowCount();

      finalReport += "<table width=\"70%\" border=\"1\" align=\"center\" cellpadding=\"15\" cellspacing=\"0\">";
      finalReport += "<tr>";
      for (int i = 0; i < columns; i++)
      {
         switch (i)
         {
            case 0:
            case 1:
            case 2:
               finalReport += "<th rowspan=\"2\">";
               finalReport += table.getColumnName(i);
               finalReport += "</th>";
               break;
            case 3:
               finalReport += "<th colspan=\"4\">Кількість</th>";
               break;
         }
      }
      finalReport += "</tr>";
      finalReport += "<tr>";
      for (int i = 0; i < columns; i++)
      {
         switch (i)
         {
            case 3:
            case 4:
            case 5:
            case 6:
               finalReport += "<th>";
               finalReport += table.getColumnName(i);
               finalReport += "</th>";
               break;
         }
      }
      finalReport += "</tr>";
      String cell;
      Map<String, List<String>> groupedCells = new HashMap<>();
      for (int row = 0; row < rows; row++)
      {
         String group = (String) table.getValueAt(row, 7);
         if (!groupedCells.containsKey(group))
         {
            cell = new String("<tr><td colspan=\"7\">" + group + "</td><tr>");
            groupedCells.put(group, new ArrayList<>());
            groupedCells.get(group).add(cell);
         }
         List<String> cells = groupedCells.get(group);
         cells.add("<tr>");
         for (int column = 0; column < columns - 1; column++)
         {
            cells.add("<td>" + table.getValueAt(row, column) + "</td>");
         }
         cells.add("</tr>");
      }
      for (String group : groupedCells.keySet())
      {
         for (String printCell : groupedCells.get(group))
         {
            finalReport += printCell;
         }
      }
      finalReport += "</table>";
      return finalReport;
   }
}
