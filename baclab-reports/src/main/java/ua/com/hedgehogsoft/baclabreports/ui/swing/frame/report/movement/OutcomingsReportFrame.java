package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.movement;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.print.pdf.OutcomingsReportPrinter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.MovementsReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.OutcomingsReportTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MovementsReportTableModel;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@Component
public class OutcomingsReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton deleteButton;
   private JButton closeButton;
   private @Autowired OutcomingsReportTable table;
   private @Autowired ProductStorageTable productStorageTable;
   private @Autowired DatePicker datePicker;
   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired ProductRepository productRepository;
   private @Autowired OutcomingsReportPrinter printer;
   private static final Logger logger = Logger.getLogger(OutcomingsReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
   }

   public void init()
   {
      JDatePickerImpl datePickerFrom = datePicker.getDatePicker();
      JDatePickerImpl datePickerTo = datePicker.getDatePicker();
      do
      {
         new MovementsReportPopup(datePickerFrom, datePickerTo);
      }
      while (!checkInputData(datePickerFrom, datePickerTo));
      String from = datePickerFrom.getJFormattedTextField().getText();
      String to = datePickerTo.getJFormattedTextField().getText();

      frame = new JFrame("БакЗвіт - списання");
      frame.pack();
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            logger.info("OutcomingsReportFrame was closed.");
            frame.dispose();
         }
      });
      closeButton = new JButton("Закрити");
      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            logger.info("OutcomingsReportFrame was closed.");
            frame.dispose();
         }
      });
      printButton = new JButton("Друкувати");
      printButton.addActionListener(l -> printer.print(table, from, to));
      deleteButton = new JButton("Видалити");
      deleteButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            Outcoming outcoming = outcomingRepository
                  .getById((long) table.getValueAt(table.getSelectedRow(), table.getIndexColumn()));
            Product existedProduct = outcoming.getProduct();
            existedProduct.setAmount(existedProduct.getAmount() + outcoming.getAmount());
            productRepository.updateAmount(existedProduct.getId(), existedProduct.getAmount());
            outcomingRepository.delete(outcoming.getId());
            MovementsReportTableModel model = (MovementsReportTableModel) table.getModel();
            model.removeRow(table.getSelectedRow());
            ((ProductStoreTableModel) productStorageTable.getModel()).updateProduct(existedProduct);

            JPanel panel = new JPanel(new GridLayout(7, 2));
            panel.add(new JLabel("Дата: "));
            panel.add(new JLabel(new DateLabelFormatter().dateToString(outcoming.getDate())));
            panel.add(new JLabel("Найменування: "));
            panel.add(new JLabel(outcoming.getProduct().getName()));
            panel.add(new JLabel("Кількість, од.: "));
            panel.add(new JLabel(Double.toString(outcoming.getAmount())));
            panel.add(new JLabel("Одиниця виміру: "));
            panel.add(new JLabel(outcoming.getProduct().getUnit().getName()));
            panel.add(new JLabel("Ціна, грн./од.: "));
            panel.add(new JLabel(Double.toString(outcoming.getProduct().getPrice())));
            panel.add(new JLabel("Група: "));
            panel.add(new JLabel(outcoming.getProduct().getSource().getName()));
            panel.add(new JLabel("Сума, грн.: "));
            panel.add(new JLabel(Double.toString(outcoming.getAmount() * outcoming.getProduct().getPrice())));

            JOptionPane.showMessageDialog(null, panel, "Видалено", JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
         }

      });

      JPanel datePanel = new JPanel(new GridLayout(2, 2));
      datePanel.add(new JLabel("Початок періоду:"));
      datePanel.add(new JLabel(from));
      datePanel.add(new JLabel("Кінець періоду:"));
      datePanel.add(new JLabel(to));
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(printButton);
      buttonsPanel.add(deleteButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = new JScrollPane(table.init(from, to));
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(datePanel, BorderLayout.NORTH);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("OutcomingsReportFrame was started.");
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

   private boolean checkInputData(JDatePickerImpl datePickerFrom, JDatePickerImpl datePickerTo)
   {
      boolean result = true;
      Date dateFrom = null;
      Date dateTo = null;
      if (datePickerFrom.getJFormattedTextField().getText() == null
            || datePickerFrom.getJFormattedTextField().getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, "Заповніть поле початку періоду", "Помилка", JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      else
      {
         dateFrom = (Date) new DateLabelFormatter().stringToValue(datePickerFrom.getJFormattedTextField().getText());
      }

      if (datePickerTo.getJFormattedTextField().getText() == null
            || datePickerTo.getJFormattedTextField().getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, "Заповніть поле кінця періоду", "Помилка", JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      else
      {
         dateTo = (Date) new DateLabelFormatter().stringToValue(datePickerTo.getJFormattedTextField().getText());
      }

      if (dateFrom != null && dateTo != null)
      {
         if (dateTo.before(dateFrom))
         {
            JOptionPane.showMessageDialog(null,
                  "Кінець періоду не може бути раніше за його початок.\nПоміняйте, будь ласка, дати місцями.",
                  "Помилка", JOptionPane.ERROR_MESSAGE);
            result = false;
         }
      }
      return result;
   }
}
