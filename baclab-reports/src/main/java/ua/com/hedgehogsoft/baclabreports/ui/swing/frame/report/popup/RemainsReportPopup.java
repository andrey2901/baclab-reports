package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePickerImpl;

public class RemainsReportPopup
{
   public RemainsReportPopup(JComboBox<String> sourceComboBox, JDatePickerImpl datePickerImpl)
   {
      JPanel panel = new JPanel(new GridLayout(2, 2));
      panel.add(new JLabel("Група:"));
      panel.add(sourceComboBox);
      panel.add(new JLabel("Дата:"));
      panel.add(datePickerImpl);
      JOptionPane.showMessageDialog(null, panel, "БакЗвіт - оберить дату", JOptionPane.INFORMATION_MESSAGE);
   }
}
