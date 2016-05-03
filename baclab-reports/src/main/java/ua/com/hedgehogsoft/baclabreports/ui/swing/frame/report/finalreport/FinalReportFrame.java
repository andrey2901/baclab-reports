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
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.MonthCheckBox;
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.YearCheckBox;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.FinalReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.FinalReportTable;

@Component
public class FinalReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton closeButton;
   private @Autowired FinalReportTable table;
   private @Autowired FinalReportPrinter printer;
   private static final Logger logger = Logger.getLogger(FinalReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
   }

   public void init()
   {
      JComboBox<String> monthComboBox = new MonthCheckBox();
      JComboBox<Integer> yearComboBox = new YearCheckBox();
      new FinalReportPopup(monthComboBox, yearComboBox);
      DateRange ranger = new DateRange(monthComboBox.getSelectedIndex(), (int) yearComboBox.getSelectedItem());
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      frame = new JFrame("БакЗвіт - загальний звіт");
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            logger.info("FinalReportFrame was closed.");
            frame.dispose();
         }
      });

      closeButton = new JButton("Закрити");
      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            frame.dispose();
            logger.info("FinalReportFrame was closed.");
         }
      });
      printButton = new JButton("Друкувати");
      printButton.addActionListener(l -> printer.print(table, dateFrom, dateTo));
      JPanel titlePanel = new JPanel(new GridLayout(4, 1));
      titlePanel.add(new JLabel("Звіт", SwingConstants.CENTER));
      titlePanel.add(new JLabel("про надходження і відпуск (використання)", SwingConstants.CENTER));
      titlePanel.add(new JLabel("лікарських засобів та медичних виробів", SwingConstants.CENTER));
      titlePanel.add(new JLabel("з " + dateFrom + " до " + dateTo, SwingConstants.CENTER));
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(printButton);
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
      logger.info("FinalReportFrame was started.");
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

}
