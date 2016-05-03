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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.print.pdf.RemainsReportPrinter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.RemainsReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.RemainsTable;

@Component
public class RemainsReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton closeButton;
   private String popupErrorLabel;
   private String dateEmptyErrorMessage;
   private @Autowired RemainsTable table;
   private @Autowired DatePicker datePicker;
   private @Autowired SourceCache sourcesCache;
   private @Autowired RemainsReportPrinter printer;
   private static final Logger logger = Logger.getLogger(RemainsReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      popupErrorLabel = messageByLocaleService.getMessage("message.popup.error.label");
      dateEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.date.empty.text");
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

      do
      {
         new RemainsReportPopup(sourceComboBox, datePickerImpl);
         if (datePickerImpl.getJFormattedTextField().getText() == null
               || datePickerImpl.getJFormattedTextField().getText().isEmpty())
         {
            JOptionPane.showMessageDialog(null, dateEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         }
         else
         {
            break;
         }
      }
      while (true);

      frame = new JFrame("БакЗвіт - залишки");
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            logger.info("RemainsReportFrame was closed.");
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
            logger.info("RemainsReportFrame was closed.");
         }
      });

      printButton = new JButton("Друкувати");
      printButton.addActionListener(l -> printer.print(table, datePickerImpl.getJFormattedTextField().getText(),
            (String) sourceComboBox.getSelectedItem()));
      JPanel titlePanel = new JPanel(new GridLayout(5, 1));
      titlePanel.add(new JLabel("Залишок", SwingConstants.CENTER));
      titlePanel.add(new JLabel("поживних середовищ і хімреактивів, лабораторного скла ", SwingConstants.CENTER));
      titlePanel.add(new JLabel(
            "по Централізованій баклабораторії Лівобережжя КЗ \"Дніпропетровьска міська клінічна лікарня №9\" ДОР\"",
            SwingConstants.CENTER));
      titlePanel.add(new JLabel("на " + datePickerImpl.getJFormattedTextField().getText(), SwingConstants.CENTER));
      titlePanel.add(new JLabel("\"" + (String) sourceComboBox.getSelectedItem() + "\"", SwingConstants.CENTER));
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(printButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(
            table.init(datePickerImpl.getJFormattedTextField().getText(), (String) sourceComboBox.getSelectedItem()));
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(titlePanel, BorderLayout.NORTH);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("RemainsReportFrame was started.");
   }
}
