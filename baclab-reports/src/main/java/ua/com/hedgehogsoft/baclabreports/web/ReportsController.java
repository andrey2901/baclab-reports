package ua.com.hedgehogsoft.baclabreports.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportsController
{
   @RequestMapping("/baclab/reports")
   public String getReports()
   {
      String reports = "<a href=\"/greeting\">Привітання</a><br/><br/>";
      reports += "<a href=\"/baclab/remains/now\">Склад</a><br/><br/>";
      reports += "<b>Залишки на сьогодні</b><br/>";
      reports += "<form action=\"/baclab/remains\" method=\"POST\">";
      reports += "Оберить групу: <br/>";
      reports += "<select name=\"source\">";
      reports += "<option vаluе=\"Реактиви, поживні середовища\">Реактиви, поживні середовища</option>";
      reports += "<option vаluе=\"Меценат\">Меценат</option>";
      reports += "<option vаluе=\"Від провізора\">Від провізора</option>";
      reports += "<option vаluе=\"Від дезінфектора\">Від дезінфектора</option>";
      reports += "</select><br/>";
      reports += "<input type=\"submit\" value=\"Просмотр\" />";
      reports += "</form><br/><br/>";
      return reports;
   }
}
