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
      reports += "<option value=\"Реактиви, поживні середовища\">Реактиви, поживні середовища</option>";
      reports += "<option value=\"Меценат\">Меценат</option>";
      reports += "<option value=\"Від провізора\">Від провізора</option>";
      reports += "<option value=\"Від дезінфектора\">Від дезінфектора</option>";
      reports += "</select><br/>";
      reports += "<input type=\"submit\" value=\"Просмотр\" />";
      reports += "</form><br/>";
      reports += "<b>Загальний звіт</b><br/>";
      reports += "<form action=\"/baclab/report/final\" method=\"POST\">";
      reports += "Оберить місяць: <br/>";
      reports += "<select name=\"month\">";
      reports += "<option value=\"0\">Січень</option>";
      reports += "<option value=\"1\">Лютий</option>";
      reports += "<option value=\"2\">Березень</option>";
      reports += "<option value=\"3\">Квітень</option>";
      reports += "<option value=\"4\">Травень</option>";
      reports += "<option value=\"5\">Червень</option>";
      reports += "<option value=\"6\">Липень</option>";
      reports += "<option value=\"7\">Серпень</option>";
      reports += "<option value=\"8\">Вересень</option>";
      reports += "<option value=\"9\">Жовтень</option>";
      reports += "<option value=\"10\">Листопад</option>";
      reports += "<option value=\"11\">Грудень</option>";
      reports += "</select><br/>";
      reports += "Оберить рік: <br/>";
      reports += "<select name=\"year\">";
      reports += "<option value=\"2015\">2015</option>";
      reports += "<option value=\"2016\">2016</option>";
      reports += "<option value=\"2017\">2017</option>";
      reports += "<option value=\"2018\">2018</option>";
      reports += "<option value=\"2019\">2019</option>";
      reports += "</select><br/>";
      reports += "<input type=\"submit\" value=\"Просмотр\" />";
      reports += "</form><br/>";
      return reports;
   }
}
