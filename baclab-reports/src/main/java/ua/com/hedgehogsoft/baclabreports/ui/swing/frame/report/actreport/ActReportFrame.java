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
import ua.com.hedgehogsoft.baclabreports.ui.swing.commons.ComboBoxCreator;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ActReportTable;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class ActReportFrame extends ReportFrame
{
   private JButton saveButton;
   private JButton closeButton;
   private @Autowired ActReportTable table;
   private @Autowired SourceCache sourcesCache;
   private @Autowired ActReportPrinter printer;
   private @Autowired Viewer viewer;
   private @Autowired ReportPopup popup;
   private @Autowired ComboBoxCreator comboBoxCreator;
   private String titleText1;
   private String titleText2;
   private String titleText3;
   private static final Logger logger = Logger.getLogger(ActReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.outcoming.title");
      titleText1 = messageByLocaleService.getMessage("frame.report.act.title.text.1");
      titleText2 = messageByLocaleService.getMessage("frame.report.act.title.text.2");
      titleText3 = messageByLocaleService.getMessage("frame.report.act.title.text.3");
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

   public void init()
   {
      JComboBox<String> monthComboBox = comboBoxCreator.getMonthComboBox();
      JComboBox<Integer> yearComboBox = comboBoxCreator.getYearComboBox();
      JComboBox<String> sourceComboBox = new JComboBox<String>();
      for (Source source : sourcesCache.getAll())
      {
         sourceComboBox.addItem(source.getName());
      }
      if (popup.createActReportPopup(monthComboBox, yearComboBox, sourceComboBox) == -1)
         return;
      DateRange ranger = new DateRange(monthComboBox.getSelectedIndex(), (int) yearComboBox.getSelectedItem());
      DateLabelFormatter formatter = new DateLabelFormatter();
      String dateFrom = formatter.dateToString(ranger.from());
      String dateTo = formatter.dateToString(ranger.to());
      String source = (String) sourceComboBox.getSelectedItem();

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
      saveButton.addActionListener(l -> viewer.view(printer.print(table, dateFrom, dateTo, source)));
      JPanel titlePanel = new JPanel(new GridLayout(5, 1));
      titlePanel.add(new JLabel(titleText1, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText2, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText3, SwingConstants.CENTER));
      titlePanel
            .add(new JLabel(dateLabelFrom + " " + dateFrom + " " + dateLabelTo + " " + dateTo, SwingConstants.CENTER));
      if (SourceType.getType(source) != SourceType.BUDGET)
      {
         titlePanel.add(new JLabel("\"" + source + "\"", SwingConstants.CENTER));
      }
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(saveButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(table.init(ranger.from(), ranger.to(), source));
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(titlePanel, BorderLayout.NORTH);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}
