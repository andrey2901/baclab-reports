package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.actreport;

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

import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.model.SourceType;
import ua.com.hedgehogsoft.baclabreports.print.pdf.ActReportPrinter;
import ua.com.hedgehogsoft.baclabreports.service.DateRange;
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.MonthCheckBox;
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.YearCheckBox;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ActReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ActReportTable;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class ActReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton closeButton;
   private @Autowired ActReportTable table;
   private @Autowired SourceCache sourcesCache;
   private @Autowired ActReportPrinter printer;
   private @Autowired Viewer viewer;
   private static final Logger logger = Logger.getLogger(ActReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

   public void init()
   {
      JComboBox<String> monthComboBox = new MonthCheckBox();
      JComboBox<Integer> yearComboBox = new YearCheckBox();
      JComboBox<String> sourceComboBox = new JComboBox<String>();
      for (Source source : sourcesCache.getAll())
      {
         sourceComboBox.addItem(source.getName());
      }
      new ActReportPopup(monthComboBox, yearComboBox, sourceComboBox);
      DateRange ranger = new DateRange(monthComboBox.getSelectedIndex(), (int) yearComboBox.getSelectedItem());
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      String source = (String) sourceComboBox.getSelectedItem();

      frame = new JFrame("БакЗвіт - акт списання");
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            logger.info("ActReportFrame was closed.");
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
            logger.info("ActReportFrame was closed.");
         }
      });
      printButton = new JButton("Друкувати");
      printButton.addActionListener(l -> viewer.view(printer.print(table, dateFrom, dateTo, source)));
      JPanel titlePanel = new JPanel(new GridLayout(5, 1));
      titlePanel.add(new JLabel("Акт", SwingConstants.CENTER));
      titlePanel
            .add(new JLabel("списання поживних середовищ і хімреактивів, лабораторного скла,", SwingConstants.CENTER));
      titlePanel.add(new JLabel(
            "використаних Централізованою баклабораторією Лівобережжя КЗ \"Дніпропетровьска міська клінічна лікарня №9\" ДОР\"",
            SwingConstants.CENTER));
      titlePanel.add(new JLabel("з " + dateFrom + " до " + dateTo, SwingConstants.CENTER));
      if (SourceType.getType(source) != SourceType.BUDGET)
      {
         titlePanel.add(new JLabel("\"" + source + "\"", SwingConstants.CENTER));
      }
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(printButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(table.init(dateFrom, dateTo, source));
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(titlePanel, BorderLayout.NORTH);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("ActReportFrame was started.");
   }
}
