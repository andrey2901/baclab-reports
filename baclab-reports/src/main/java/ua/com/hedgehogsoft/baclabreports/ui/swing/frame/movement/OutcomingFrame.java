package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Outcoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.SourceRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.UnitRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup.OutcomingPopupMessager;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@Component
public class OutcomingFrame
{
   private static final Logger logger = Logger.getLogger(OutcomingFrame.class);
   private String title;
   private String productNameLabel;
   private String unitNameLabel;
   private String priceNameLabel;
   private String amountNameLabel;
   private String sourceNameLabel;
   private String dateNameLabel;
   private String outcomingButtonLabel;
   private String closeButtonLabel;

   private @Autowired DatePicker datePicker;
   private @Autowired UnitRepository unitRepository;
   private @Autowired SourceRepository sourceRepository;
   private @Autowired ProductRepository productRepository;
   private @Autowired ProductStorageTable productStorageTable;
   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired OutcomingPopupMessager outcomingPopupMessager;

   private JDatePickerImpl datePickerImpl;
   private JButton closeButton;
   private JButton outcomingButton;
   private JComboBox<String> outcomingNameComboBox;
   private JComboBox<String> outcomingCostComboBox;
   private JTextField outcomingAmountTextField;
   private JComboBox<String> outcomingSourceComboBox;
   private JComboBox<String> outcomingUnitComboBox;

   @Autowired
   public OutcomingFrame(MessageByLocaleService messageByLocaleService)
   {
      title = messageByLocaleService.getMessage("frame.outcoming.title");
      productNameLabel = messageByLocaleService.getMessage("message.popup.inform.product.label");
      unitNameLabel = messageByLocaleService.getMessage("message.popup.inform.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("message.popup.inform.price.label");
      amountNameLabel = messageByLocaleService.getMessage("message.popup.inform.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("message.popup.inform.source.label");
      dateNameLabel = messageByLocaleService.getMessage("message.popup.inform.date.label");
      outcomingButtonLabel = messageByLocaleService.getMessage("button.credit.label");
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
   }

   public void init()
   {
      datePickerImpl = datePicker.getDatePicker();

      JFrame outcomingFrame = new JFrame(title);

      outcomingFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close(outcomingFrame);
         }
      });

      closeButton = new JButton(closeButtonLabel);

      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            close(outcomingFrame);
         }
      });

      outcomingButton = new JButton(outcomingButtonLabel);

      outcomingButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (checkInputData())
            {
               Product product = new Product();

               product.setName((String) outcomingNameComboBox.getSelectedItem());

               product.setPrice(Double.valueOf(((String) outcomingCostComboBox.getSelectedItem()).replace(",", ".")));

               product.setAmount(Double.valueOf(outcomingAmountTextField.getText().replace(",", ".")));

               product.setSource(sourceRepository.findByName((String) outcomingSourceComboBox.getSelectedItem()));

               product.setUnit(unitRepository.findByName((String) outcomingUnitComboBox.getSelectedItem()));

               Product existedProduct = productRepository.getProductByNameAndPriceAndSourceAndUnit(product.getName(),
                     product.getPrice(), product.getSource().getId(), product.getUnit().getId());

               if (existedProduct.getAmount() >= product.getAmount())
               {
                  if (isReversible(existedProduct, product.getAmount(),
                        datePickerImpl.getJFormattedTextField().getText()))
                  {
                     existedProduct.setAmount(existedProduct.getAmount() - product.getAmount());

                     if (productRepository.updateAmount(existedProduct.getId(), existedProduct.getAmount()) != 0)
                     {
                        Outcoming outcoming = new Outcoming();
                        outcoming.setProduct(existedProduct);
                        outcoming.setAmount(product.getAmount());
                        DateLabelFormatter formatter = new DateLabelFormatter();
                        Date date = (Date) formatter.stringToValue(datePickerImpl.getJFormattedTextField().getText());
                        outcoming.setDate(date);
                        outcoming = outcomingRepository.save(outcoming);
                        if (outcoming != null)
                        {
                           ProductStoreTableModel model = (ProductStoreTableModel) productStorageTable.getModel();

                           model.updateProduct(existedProduct);

                           logger.info("Outcomings were performed.");

                           outcomingPopupMessager.infoPopup(product);

                           close(outcomingFrame);
                        }
                     }
                  }
                  else
                  {
                     outcomingPopupMessager.amountInsufficientOnDate();
                  }
               }
               else
               {
                  outcomingPopupMessager.amountInsufficient();
               }
               close(outcomingFrame);
            }
         }
      });

      JPanel buttonsPanel = new JPanel();

      buttonsPanel.add(outcomingButton);

      buttonsPanel.add(closeButton);

      /*--------------------------------------------------------------*/
      JPanel outcomingPanel = new JPanel(new GridBagLayout());

      outcomingSourceComboBox = new JComboBox<String>();

      for (Source source : sourceRepository.findAll())
      {
         outcomingSourceComboBox.addItem(source.getName());
      }

      outcomingPanel.add(new JLabel(sourceNameLabel), position(0, 0));

      outcomingPanel.add(outcomingSourceComboBox, position(1, 0));

      outcomingPanel.add(new JLabel(productNameLabel), position(0, 1));

      outcomingNameComboBox = new JComboBox<String>();

      outcomingPanel.add(outcomingNameComboBox, position(1, 1));

      outcomingPanel.add(new JLabel(unitNameLabel), position(0, 2));

      outcomingUnitComboBox = new JComboBox<String>();

      outcomingPanel.add(outcomingUnitComboBox, position(1, 2));

      outcomingPanel.add(new JLabel(priceNameLabel), position(0, 3));

      outcomingCostComboBox = new JComboBox<String>();

      outcomingPanel.add(outcomingCostComboBox, position(1, 3));

      outcomingPanel.add(new JLabel(amountNameLabel), position(0, 4));

      outcomingAmountTextField = new JTextField();

      outcomingPanel.add(outcomingAmountTextField, position(1, 4));

      outcomingPanel.add(new JLabel(dateNameLabel), position(0, 5));

      outcomingPanel.add(datePickerImpl, position(1, 5));

      outcomingCostComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (outcomingCostComboBox.getItemCount() != 0)
            {
               String productName = (String) outcomingNameComboBox.getSelectedItem();

               String sourceName = (String) outcomingSourceComboBox.getSelectedItem();

               String unitName = (String) outcomingUnitComboBox.getSelectedItem();

               String price = (String) outcomingCostComboBox.getSelectedItem();

               outcomingAmountTextField
                     .setText(Double.toString(productRepository.getAmountByProductNameAndSourceNameAndUnitNameAndPrice(
                           productName, sourceName, unitName, Double.parseDouble(price))));
            }
         }
      });

      outcomingUnitComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            outcomingCostComboBox.removeAllItems();

            String productName = (String) outcomingNameComboBox.getSelectedItem();

            String sourceName = (String) outcomingSourceComboBox.getSelectedItem();

            String unitName = (String) outcomingUnitComboBox.getSelectedItem();

            List<Double> prices = productRepository.getUniquePricesByProductNameAndSourceNameAndUnitName(productName,
                  sourceName, unitName);

            if (!prices.isEmpty())
            {
               for (Double price : prices)
               {
                  outcomingCostComboBox.addItem(Double.toString(price));
               }
            }
         }
      });

      outcomingNameComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String sourceName = (String) outcomingSourceComboBox.getSelectedItem();
            String productName = (String) outcomingNameComboBox.getSelectedItem();
            List<String> unitNames = productRepository.getUniqueUnitNamesByProductNameAndSourceName(productName,
                  sourceName);

            outcomingUnitComboBox.removeAllItems();

            for (String unit : unitNames)
            {
               outcomingUnitComboBox.addItem(unit);
            }
         }
      });

      outcomingSourceComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            outcomingNameComboBox.removeAllItems();

            String sourceName = (String) outcomingSourceComboBox.getSelectedItem();

            List<String> productNames = productRepository.getUniqueProductNamesBySource(sourceName);

            if (!productNames.isEmpty())
            {
               for (String name : productNames)
               {
                  outcomingNameComboBox.addItem(name);
               }
            }
         }
      });

      outcomingSourceComboBox.setSelectedIndex(0);

      outcomingFrame.add(outcomingPanel, BorderLayout.CENTER);

      outcomingFrame.add(buttonsPanel, BorderLayout.SOUTH);

      outcomingFrame.setSize(700, 225);

      outcomingFrame.setMinimumSize(new Dimension(375, 225));

      outcomingFrame.setResizable(true);

      outcomingFrame.setLocationRelativeTo(null);

      outcomingFrame.setVisible(true);

      logger.info("OutcomingsFrame was started.");
   }

   private boolean checkInputData()
   {
      boolean result = true;

      if (outcomingNameComboBox.getSelectedItem() == null
            || ((String) outcomingNameComboBox.getSelectedItem()).isEmpty())
      {
         outcomingPopupMessager.productEmpty();
         result = false;
      }
      if (outcomingUnitComboBox.getSelectedItem() == null
            || ((String) outcomingUnitComboBox.getSelectedItem()).isEmpty())
      {
         outcomingPopupMessager.unitEmpty();
         result = false;
      }
      if (outcomingCostComboBox.getSelectedItem() == null
            || ((String) outcomingCostComboBox.getSelectedItem()).isEmpty())
      {
         outcomingPopupMessager.priceEmpty();
         result = false;
      }
      if (outcomingAmountTextField.getText() == null || outcomingAmountTextField.getText().isEmpty())
      {
         outcomingPopupMessager.amountEmpty();
         result = false;
      }
      if (datePickerImpl.getJFormattedTextField().getText() == null
            || datePickerImpl.getJFormattedTextField().getText().isEmpty())
      {
         outcomingPopupMessager.dateEmpty();
         result = false;
      }
      return result;
   }

   private GridBagConstraints position(int x, int y)
   {
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      switch (x)
      {
         case 0:
            c.gridx = 0;
            c.weightx = 0;
            c.gridwidth = 1;
            break;
         case 1:
            c.gridx = 1;
            c.weightx = 10;
            c.gridwidth = 3;
            break;
      }
      c.gridy = y;
      return c;
   }

   private void close(JFrame frame)
   {
      frame.dispose();
      logger.info("OutcomingsFrame was closed.");
   }

   /*
    * Check ability to insert an outcoming in the past (f.e. today is 24.05.2015
    * and the date of outcoming is 20.03.2015)
    */
   private boolean isReversible(Product existedProduct, double outcomingAmount, String date)
   {
      // Calendar cal = Calendar.getInstance();
      // cal.set(Calendar.HOUR_OF_DAY, 0);
      // cal.set(Calendar.MINUTE, 0);
      // cal.set(Calendar.SECOND, 0);
      // cal.set(Calendar.MILLISECOND, 0);
      //
      // Date today = cal.getTime();
      //
      // DateLabelFormatter formatter = new DateLabelFormatter();
      //
      // Date destinationDate = (Date) formatter.stringToValue(date);
      //
      // while (destinationDate.before(today))
      // {
      // double incomingsSum = new
      // Connection().getIncomingsSumFromDate(existedProduct.getId(),
      // formatter.dateToString(destinationDate));
      //
      // double outcomingsSum = new
      // Connection().getOutcomingsSumFromDate(existedProduct.getId(),
      // formatter.dateToString(destinationDate));
      //
      // double remainsAmount = existedProduct.getAmount() + outcomingsSum -
      // incomingsSum;
      //
      // double incomingSumOnDate = new
      // Connection().getIncomingSumOnDate(existedProduct.getId(),
      // formatter.dateToString(destinationDate));
      //
      // double outcomingSumOnDate = new
      // Connection().getOutcomingSumOnDate(existedProduct.getId(),
      // formatter.dateToString(destinationDate));
      //
      // remainsAmount = remainsAmount + incomingSumOnDate - outcomingSumOnDate;
      //
      // if (remainsAmount < outcomingAmount)
      // {
      // return false;
      // }
      //
      // cal.setTime(destinationDate);
      // cal.add(Calendar.DATE, 1);
      // destinationDate = cal.getTime();
      // }

      return true;
   }
}
