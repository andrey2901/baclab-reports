package ua.com.hedgehogsoft.baclabreports.ui.swing.commons;

import java.util.Calendar;
import javax.swing.JComboBox;

public class MonthCheckBox extends JComboBox<String>
{
   private static final long serialVersionUID = 1L;

   public MonthCheckBox()
   {
      super(new String[] {"Січень",
                          "Лютий",
                          "Березень",
                          "Квітень",
                          "Травень",
                          "Червень",
                          "Липень",
                          "Серпень",
                          "Вересень",
                          "Жовтень",
                          "Листопад",
                          "Грудень"});

      setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
   }
}
