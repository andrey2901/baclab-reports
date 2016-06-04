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
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup.ReportPopup;
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
   private @Autowired ReportPopup popup;
   private static final Logger logger = Logger.getLogger(IncomingsReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.report.incomings.title");
   }

   public void init()
   {
      JDatePickerImpl datePickerFrom = datePicker.getDatePicker();
      JDatePickerImpl datePickerTo = datePicker.getDatePicker();
      if (popup.createMovementsReportPopup(datePickerFrom, datePickerTo) == -1)
         return;
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
      printButton = new JButton(closeButtonLabel);
      printButton.addActionListener(l -> viewer.view(printer.print(table, from, to)));
      deleteButton = new JButton(deleteButtonLabel);
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
               popup.deleteIncomingPopup(incoming);
               frame.dispose();
            }
            else
            {
               popup.deleteIncomingInsufficientErrorPopup();
            }

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
