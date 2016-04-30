package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ActReportPopup
{
   public ActReportPopup(JComboBox<String> monthComboBox,
                                 JComboBox<Integer> yearComboBox,
                                 JComboBox<String> sourceComboBox)
   {
      JPanel panel = new JPanel(new GridLayout(3, 2));
      panel.add(new JLabel("Місяць:"));
      panel.add(monthComboBox);
      panel.add(new JLabel("Рік:"));
      panel.add(yearComboBox);
      panel.add(new JLabel("Група:"));
      panel.add(sourceComboBox);
      JOptionPane.showMessageDialog(null, panel, "БакЗвіт - оберить період", JOptionPane.INFORMATION_MESSAGE);
   }
}
