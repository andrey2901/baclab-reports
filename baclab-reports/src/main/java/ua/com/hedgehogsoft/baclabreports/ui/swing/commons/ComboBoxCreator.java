package ua.com.hedgehogsoft.baclabreports.ui.swing.commons;

import javax.annotation.PostConstruct;
import javax.swing.JComboBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

@Component
public class ComboBoxCreator
{
   private @Autowired MessageByLocaleService messageByLocaleService;
   private String jan;
   private String feb;
   private String mar;
   private String apr;
   private String may;
   private String jun;
   private String jul;
   private String aug;
   private String sep;
   private String oct;
   private String nov;
   private String dec;

   @PostConstruct
   private void localize()
   {
      jan = messageByLocaleService.getMessage("date.month.label.jan");
      feb = messageByLocaleService.getMessage("date.month.label.feb");
      mar = messageByLocaleService.getMessage("date.month.label.mar");
      apr = messageByLocaleService.getMessage("date.month.label.apr");
      may = messageByLocaleService.getMessage("date.month.label.may");
      jun = messageByLocaleService.getMessage("date.month.label.jun");
      jul = messageByLocaleService.getMessage("date.month.label.jul");
      aug = messageByLocaleService.getMessage("date.month.label.aug");
      sep = messageByLocaleService.getMessage("date.month.label.sep");
      oct = messageByLocaleService.getMessage("date.month.label.oct");
      nov = messageByLocaleService.getMessage("date.month.label.nov");
      dec = messageByLocaleService.getMessage("date.month.label.dec");
   };

   public JComboBox<String> getMonthComboBox()
   {
      return new MonthComboBox(new String[] {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec});
   }

   public JComboBox<Integer> getYearComboBox()
   {
      return new YearComboBox();
   }
}
