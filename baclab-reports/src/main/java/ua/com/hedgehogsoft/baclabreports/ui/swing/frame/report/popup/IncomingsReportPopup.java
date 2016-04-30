package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePickerImpl;

public class IncomingsReportPopup
{
   public IncomingsReportPopup(JDatePickerImpl periodBegin, JDatePickerImpl periodEnd)
   {
      JPanel panel = new JPanel(new GridLayout(2, 2));
      panel.add(new JLabel("Початок періоду:"));
      panel.add(new JLabel("Кінець періоду:"));
      panel.add(periodBegin);
      panel.add(periodEnd);
      JOptionPane.showMessageDialog(null, panel, "БакЗвіт - оберить період", JOptionPane.INFORMATION_MESSAGE);
   }
}
