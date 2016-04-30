package ua.com.hedgehogsoft.baclabreports.ui.swing.commons;

import javax.swing.JComboBox;

public class YearCheckBox extends JComboBox<Integer>
{
   private static final long serialVersionUID = 1L;

   public YearCheckBox()
   {
      for (int i = 2015; i < 2031; i++)
      {
         super.addItem(i);
      }
   }
}
