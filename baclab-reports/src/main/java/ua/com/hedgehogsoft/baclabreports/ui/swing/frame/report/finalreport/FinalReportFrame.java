package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.finalreport;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.print.pdf.FinalReportPrinter;
import ua.com.hedgehogsoft.baclabreports.service.DateRange;
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.ComboBoxCreator;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.FinalReportTable;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class FinalReportFrame extends ReportFrame
{
   private JButton saveButton;
   private JButton closeButton;
   private @Autowired FinalReportTable table;
   private @Autowired FinalReportPrinter printer;
   private @Autowired Viewer viewer;
   private @Autowired ReportPopup popup;
   private @Autowired ComboBoxCreator comboBoxCreator;
   private String titleText1;
   private String titleText2;
   private String titleText3;
   private static final Logger logger = Logger.getLogger(FinalReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.report.final.title");
      titleText1 = messageByLocaleService.getMessage("frame.report.final.title.text.1");
      titleText2 = messageByLocaleService.getMessage("frame.report.final.title.text.2");
      titleText3 = messageByLocaleService.getMessage("frame.report.final.title.text.3");
   }

   public void init()
   {
      JComboBox<String> monthComboBox = comboBoxCreator.getMonthComboBox();
      JComboBox<Integer> yearComboBox = comboBoxCreator.getYearComboBox();
      if (popup.createFinalReportPopup(monthComboBox, yearComboBox) == -1)
         return;
      DateRange ranger = new DateRange(monthComboBox.getSelectedIndex(), (int) yearComboBox.getSelectedItem());
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      frame = new JFrame(title);
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            frame.dispose();
         }
      });

      closeButton = new JButton(closeButtonLabel);
      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            frame.dispose();
         }
      });
      saveButton = new JButton(saveButtonLabel);
      saveButton.addActionListener(l -> viewer.view(printer.print(table, dateFrom, dateTo)));
      JPanel titlePanel = new JPanel(new GridLayout(4, 1));
      titlePanel.add(new JLabel(titleText1, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText2, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText3, SwingConstants.CENTER));
      titlePanel
            .add(new JLabel(dateLabelFrom + " " + dateFrom + " " + dateLabelTo + " " + dateTo, SwingConstants.CENTER));
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(saveButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(table.init(dateFrom, dateTo));
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(titlePanel, BorderLayout.NORTH);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

}
