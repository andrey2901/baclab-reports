package ua.com.hedgehogsoft.baclabreports.ui.swing.date;

import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

@Component
public class DatePicker
{
   private @Autowired MessageByLocaleService messageByLocaleService;

   public JDatePickerImpl getDatePicker()
   {
      UtilDateModel model = new UtilDateModel();
      Properties props = new Properties();
      props.put("text.today", messageByLocaleService.getMessage("datepicker.today.text"));
      props.put("text.month", messageByLocaleService.getMessage("datepicker.month.text"));
      props.put("text.year", messageByLocaleService.getMessage("datepicker.year.text"));
      JDatePanelImpl datePanel = new JDatePanelImpl(model, props);
      return new JDatePickerImpl(datePanel, new DateLabelFormatter());
   }
}
