package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.movement;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ReportPopup;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.OutcomingsReportTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MovementsReportTableModel;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

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
   private @Autowired Viewer viewer;
   private @Autowired ReportPopup popup;
   private static final Logger logger = Logger.getLogger(OutcomingsReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.report.outcomings.title");
   }

   public void init()
   {
      JDatePickerImpl datePickerFrom = datePicker.getDatePicker();
      JDatePickerImpl datePickerTo = datePicker.getDatePicker();
      popup.createMovementsReportPopup(datePickerFrom, datePickerTo);
      String from = datePickerFrom.getJFormattedTextField().getText();
      String to = datePickerTo.getJFormattedTextField().getText();

      frame = new JFrame(title);
      frame.pack();
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
      printButton = new JButton(printButtonLabel);
      printButton.addActionListener(l -> viewer.view(printer.print(table, from, to)));
      deleteButton = new JButton(deleteButtonLabel);
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
            popup.deleteOutcomingPopup(outcoming);
            frame.dispose();
         }

      });

      JPanel datePanel = new JPanel(new GridLayout(2, 2));
      datePanel.add(new JLabel(dateRangeLabelBegin));
      datePanel.add(new JLabel(from));
      datePanel.add(new JLabel(dateRangeLabelEnd));
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
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }
}
