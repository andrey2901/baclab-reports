package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FinalReportPopup
{
   public FinalReportPopup(JComboBox<String> monthComboBox, JComboBox<Integer> yearComboBox)
   {
      JPanel panel = new JPanel(new GridLayout(2, 2));
      panel.add(new JLabel("Місяць:"));
      panel.add(monthComboBox);
      panel.add(new JLabel("Рік:"));
      panel.add(yearComboBox);
      JOptionPane.showMessageDialog(null, panel, "БакЗвіт - оберить період", JOptionPane.INFORMATION_MESSAGE);
   }
}
