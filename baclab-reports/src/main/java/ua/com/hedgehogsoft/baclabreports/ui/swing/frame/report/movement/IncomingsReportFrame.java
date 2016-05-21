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

import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.print.pdf.IncomingsReportPrinter;
import ua.com.hedgehogsoft.baclabreports.service.PastObserver;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.MovementsReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.IncomingsReportTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MovementsReportTableModel;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class IncomingsReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton deleteButton;
   private JButton closeButton;
   private @Autowired IncomingsReportTable table;
   private @Autowired ProductStorageTable productStorageTable;
   private @Autowired DatePicker datePicker;
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired ProductRepository productRepository;
   private @Autowired IncomingsReportPrinter printer;
   private @Autowired Viewer viewer;
   private static final Logger logger = Logger.getLogger(IncomingsReportFrame.class);

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

      frame = new JFrame("БакЗвіт - надходження");
      frame.pack();
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            logger.info("IncomingsReportFrame was closed.");
            frame.dispose();
         }
      });
      closeButton = new JButton("Закрити");
      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            logger.info("IncomingsReportFrame was closed.");
            frame.dispose();
         }
      });
      printButton = new JButton("Друкувати");
      printButton.addActionListener(l -> viewer.view(printer.print(table, from, to)));
      deleteButton = new JButton("Видалити");
      deleteButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            Incoming incoming = incomingRepository
                  .getById((long) table.getValueAt(table.getSelectedRow(), table.getIndexColumn()));
            Product existedProduct = incoming.getProduct();
            DateLabelFormatter formatter = new DateLabelFormatter();
            PastObserver past = new PastObserver(productRepository, incomingRepository, outcomingRepository);
            if (past.isRemovable(existedProduct, incoming.getAmount(), formatter.dateToString(incoming.getDate())))
            {
               existedProduct.setAmount(existedProduct.getAmount() - incoming.getProduct().getAmount());
               productRepository.updateAmount(existedProduct.getId(), existedProduct.getAmount());
               incomingRepository.delete(incoming.getId());
               MovementsReportTableModel model = (MovementsReportTableModel) table.getModel();
               model.removeRow(table.getSelectedRow());
               ((ProductStoreTableModel) productStorageTable.getModel()).updateProduct(existedProduct);

               // for (int i = 0; i < productStorageTable.getColumnCount(); i++)
               // {
               // if (productStorageTable.getColumnName(i).equals("№ з/п"))
               // {
               // for (int k = 0; k < productStorageTable.getRowCount(); k++)
               // {
               // if (((int) productStorageTable.getValueAt(k, i)) ==
               // existedProduct.getId())
               // {
               // for (int z = 0; z < productStorageTable.getColumnCount(); z++)
               // {
               // if (productStorageTable.getColumnName(z).equals("Кількість,
               // од."))
               // {
               // ((ProductStoreTableModel) productStorageTable.getModel())
               // .updateProduct(existedProduct);
               // break;
               // }
               // }
               // break;
               // }
               // }
               // break;
               // }
               // }

               JPanel panel = new JPanel(new GridLayout(7, 2));
               panel.add(new JLabel("Дата: "));
               panel.add(new JLabel(new DateLabelFormatter().dateToString(incoming.getDate())));
               panel.add(new JLabel("Найменування: "));
               panel.add(new JLabel(incoming.getProduct().getName()));
               panel.add(new JLabel("Кількість, од.: "));
               panel.add(new JLabel(Double.toString(incoming.getAmount())));
               panel.add(new JLabel("Одиниця виміру: "));
               panel.add(new JLabel(incoming.getProduct().getUnit().getName()));
               panel.add(new JLabel("Ціна, грн./од.: "));
               panel.add(new JLabel(Double.toString(incoming.getProduct().getPrice())));
               panel.add(new JLabel("Група: "));
               panel.add(new JLabel(incoming.getProduct().getSource().getName()));
               panel.add(new JLabel("Сума, грн.: "));
               panel.add(new JLabel(Double.toString(incoming.getAmount() * incoming.getProduct().getPrice())));

               JOptionPane.showMessageDialog(null, panel, "Видалено", JOptionPane.INFORMATION_MESSAGE);

               frame.dispose();

            }
            else
            {
               JOptionPane.showMessageDialog(null,
                     "Ви не можете видалити вказане надходження,"
                           + "\nтак как у більш пізні строки Ви отримаєте від'ємний залишок.",
                     "Помилка", JOptionPane.ERROR_MESSAGE);
            }

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
      logger.info("IncomingsReport was started.");
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
