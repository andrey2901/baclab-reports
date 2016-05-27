package ua.com.hedgehogsoft.baclabreports.ui.swing.commons;

import java.util.Calendar;

import javax.swing.JComboBox;

public class YearComboBox extends JComboBox<Integer>
{
   private static final long serialVersionUID = 1L;

   public YearComboBox()
   {
      for (int i = 2010; i < 2031; i++)
      {
         super.addItem(i);
      }
      setSelectedIndex(Calendar.getInstance().get(Calendar.YEAR) - 2010);
   }
}
