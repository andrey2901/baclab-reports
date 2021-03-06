package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.remains;

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
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.model.SourceType;
import ua.com.hedgehogsoft.baclabreports.print.pdf.RemainsReportPrinter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.RemainsTable;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class RemainsReportFrame extends ReportFrame
{
   private JButton saveButton;
   private JButton closeButton;
   private @Autowired RemainsTable table;
   private @Autowired DatePicker datePicker;
   private @Autowired SourceCache sourcesCache;
   private @Autowired RemainsReportPrinter printer;
   private @Autowired ReportPopup popup;
   private @Autowired Viewer viewer;
   private String titleText1;
   private String titleText2;
   private String titleText3;
   private static final Logger logger = Logger.getLogger(RemainsReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.report.remains.title");
      titleText1 = messageByLocaleService.getMessage("frame.report.remains.title.text.1");
      titleText2 = messageByLocaleService.getMessage("frame.report.remains.title.text.2");
      titleText3 = messageByLocaleService.getMessage("frame.report.remains.title.text.3");
      messageByLocaleService.getMessage("message.popup.error.label");
      messageByLocaleService.getMessage("message.popup.error.date.empty.text");
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

   public void init()
   {
      JDatePickerImpl datePickerImpl = datePicker.getDatePicker();
      JComboBox<String> sourceComboBox = new JComboBox<String>();
      for (Source source : sourcesCache.getAll())
      {
         sourceComboBox.addItem(source.getName());
      }
      if (popup.createRemainsReportPopup(sourceComboBox, datePickerImpl) == -1)
         return;
      String source = (String) sourceComboBox.getSelectedItem();
      String date = datePickerImpl.getJFormattedTextField().getText();
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
      saveButton.addActionListener(l -> viewer.view(printer.print(table, date, source)));
      JPanel titlePanel = new JPanel(new GridLayout(5, 1));
      titlePanel.add(new JLabel(titleText1, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText2, SwingConstants.CENTER));
      titlePanel.add(new JLabel(titleText3, SwingConstants.CENTER));
      titlePanel.add(new JLabel(dateLabelOn + " " + date, SwingConstants.CENTER));
      if (SourceType.getType(source) != SourceType.BUDGET)
      {
         titlePanel.add(new JLabel("\"" + source + "\"", SwingConstants.CENTER));
      }
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(saveButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(table.init(date, source));
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
