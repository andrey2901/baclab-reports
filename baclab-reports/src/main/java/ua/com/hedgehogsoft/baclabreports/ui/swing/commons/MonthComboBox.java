package ua.com.hedgehogsoft.baclabreports.ui.swing.commons;

import java.util.Calendar;
import javax.swing.JComboBox;

public class MonthComboBox extends JComboBox<String>
{
   private static final long serialVersionUID = 1L;

   public MonthComboBox(String[] months)
   {
      super(months);

      setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
   }
}
