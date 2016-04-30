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

import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.IncomingsReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.IncomingsReportTable;

@Component
public class IncomingsReportFrame extends ReportFrame
{
   private JButton printButton;
   private JButton deleteButton;
   private JButton closeButton;
   private @Autowired IncomingsReportTable table;
   private @Autowired DatePicker datePicker;
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
         new IncomingsReportPopup(datePickerFrom, datePickerTo);
      }
      while (!checkInputData(datePickerFrom, datePickerTo));

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
      printButton.addActionListener(null);
      deleteButton = new JButton("Видалити");
      deleteButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {

            /*Incoming incoming = new Connection().getIncomingById((int) table.getValueAt(table.getSelectedRow(), 0));

            Product existedProduct = new Connection().getProductById(incoming.getProduct().getId());

            if (isReversible(existedProduct, incoming.getProduct().getAmount(), incoming.getDate()))
            {
               existedProduct.setAmount(existedProduct.getAmount() - incoming.getProduct().getAmount());

               if (new Connection().updateProduct(existedProduct))
               {
                  if (new Connection().deleteIncomingById(incoming.getId()))
                  {
                     DefaultTableModel model = (DefaultTableModel) table.getModel();

                     model.removeRow(table.getSelectedRow());

                     for (int i = 0; i < mainFrame.getTable().getColumnCount(); i++)
                     {
                        if (mainFrame.getTable().getColumnName(i).equals("№ з/п"))
                        {
                           for (int k = 0; k < mainFrame.getTable().getRowCount(); k++)
                           {
                              if (((int) mainFrame.getTable().getValueAt(k, i)) == existedProduct.getId())
                              {
                                 for (int z = 0; z < mainFrame.getTable().getColumnCount(); z++)
                                 {
                                    if (mainFrame.getTable().getColumnName(z).equals("Кількість, од."))
                                    {
                                       ((ProductStoreTableModel) mainFrame.getTable().getModel())
                                             .updateAmount(existedProduct);

                                       break;
                                    }
                                 }
                                 break;
                              }
                           }
                           break;
                        }
                     }

                     Sources sources = new Sources(new Connection().getSources());

                     Units units = new Units(new Connection().getUnits());

                     JPanel panel = new JPanel(new GridLayout(7, 2));
                     panel.add(new JLabel("Дата: "));
                     panel.add(new JLabel(new DateLabelFormatter().dateToString(incoming.getDate())));
                     panel.add(new JLabel("Найменування: "));
                     panel.add(new JLabel(incoming.getProduct().getName()));
                     panel.add(new JLabel("Кількість, од.: "));
                     panel.add(new JLabel(Double.toString(incoming.getProduct().getAmount())));
                     panel.add(new JLabel("Одиниця виміру: "));
                     panel.add(new JLabel(units.valueOf(incoming.getProduct().getUnit()).getName()));
                     panel.add(new JLabel("Ціна, грн./од.: "));
                     panel.add(new JLabel(Double.toString(incoming.getProduct().getPrice())));
                     panel.add(new JLabel("Група: "));
                     panel.add(new JLabel(sources.valueOf(incoming.getProduct().getSource()).getName()));
                     panel.add(new JLabel("Сума, грн.: "));
                     panel.add(new JLabel(Double.toString(incoming.getProduct().getTotalPrice())));

                     JOptionPane.showMessageDialog(null, panel, "Видалено", JOptionPane.INFORMATION_MESSAGE);

                     close(reportsFrame);
                  }
               }
            }
            else
            {
               JOptionPane.showMessageDialog(null,
                     "Ви не можете видалити вказане надходження,"
                           + "\nтак как у більш пізні строки Ви отримаєте від'ємний залишок.",
                     "Помилка", JOptionPane.ERROR_MESSAGE);
            }*/

         }
      });

      String from = datePickerFrom.getJFormattedTextField().getText();
      String to = datePickerTo.getJFormattedTextField().getText();
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
