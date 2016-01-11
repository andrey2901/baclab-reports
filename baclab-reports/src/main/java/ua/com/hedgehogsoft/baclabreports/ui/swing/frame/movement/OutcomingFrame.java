package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup.OutcomingPopupMessager;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@Component
public class OutcomingFrame extends MovementFrame
{
   private static final Logger logger = Logger.getLogger(OutcomingFrame.class);
   private String title;
   private String outcomingButtonLabel;

   private @Autowired OutcomingRepository outcomingRepository;
   private @Autowired OutcomingPopupMessager outcomingPopupMessager;

   private JButton closeButton;
   private JButton outcomingButton;
   private JComboBox<String> unitComboBox;

   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.outcoming.title");
      outcomingButtonLabel = messageByLocaleService.getMessage("button.credit.label");
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
            if (checkInputData(outcomingPopupMessager))
            {
               Product product = new Product();

               product.setName((String) nameComboBox.getSelectedItem());

               product.setPrice(Double.valueOf(((String) costComboBox.getSelectedItem()).replace(",", ".")));

               product.setAmount(Double.valueOf(amountTextField.getText().replace(",", ".")));

               product.setSource(sourceRepository.findByName((String) sourceComboBox.getSelectedItem()));

               product.setUnit(unitRepository.findByName((String) unitComboBox.getSelectedItem()));

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

      sourceComboBox = new JComboBox<String>();

      for (Source source : sourceRepository.findAll())
      {
         sourceComboBox.addItem(source.getName());
      }

      outcomingPanel.add(new JLabel(sourceNameLabel), position(0, 0));

      outcomingPanel.add(sourceComboBox, position(1, 0));

      outcomingPanel.add(new JLabel(productNameLabel), position(0, 1));

      nameComboBox = new JComboBox<String>();

      outcomingPanel.add(nameComboBox, position(1, 1));

      outcomingPanel.add(new JLabel(unitNameLabel), position(0, 2));

      unitComboBox = new JComboBox<String>();

      outcomingPanel.add(unitComboBox, position(1, 2));

      outcomingPanel.add(new JLabel(priceNameLabel), position(0, 3));

      costComboBox = new JComboBox<String>();

      outcomingPanel.add(costComboBox, position(1, 3));

      outcomingPanel.add(new JLabel(amountNameLabel), position(0, 4));

      amountTextField = new JTextField();

      outcomingPanel.add(amountTextField, position(1, 4));

      outcomingPanel.add(new JLabel(dateNameLabel), position(0, 5));

      outcomingPanel.add(datePickerImpl, position(1, 5));

      costComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (costComboBox.getItemCount() != 0)
            {
               String productName = (String) nameComboBox.getSelectedItem();

               String sourceName = (String) sourceComboBox.getSelectedItem();

               String unitName = (String) unitComboBox.getSelectedItem();

               String price = (String) costComboBox.getSelectedItem();

               amountTextField
                     .setText(Double.toString(productRepository.getAmountByProductNameAndSourceNameAndUnitNameAndPrice(
                           productName, sourceName, unitName, Double.parseDouble(price))));
            }
         }
      });

      unitComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            costComboBox.removeAllItems();

            String productName = (String) nameComboBox.getSelectedItem();

            String sourceName = (String) sourceComboBox.getSelectedItem();

            String unitName = (String) unitComboBox.getSelectedItem();

            List<Double> prices = productRepository.getUniquePricesByProductNameAndSourceNameAndUnitName(productName,
                  sourceName, unitName);

            if (!prices.isEmpty())
            {
               for (Double price : prices)
               {
                  costComboBox.addItem(Double.toString(price));
               }
            }
         }
      });

      nameComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String sourceName = (String) sourceComboBox.getSelectedItem();
            String productName = (String) nameComboBox.getSelectedItem();
            List<String> unitNames = productRepository.getUniqueUnitNamesByProductNameAndSourceName(productName,
                  sourceName);

            unitComboBox.removeAllItems();

            for (String unit : unitNames)
            {
               unitComboBox.addItem(unit);
            }
         }
      });

      sourceComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            nameComboBox.removeAllItems();

            String sourceName = (String) sourceComboBox.getSelectedItem();

            List<String> productNames = productRepository.getUniqueProductNamesBySource(sourceName);

            if (!productNames.isEmpty())
            {
               for (String name : productNames)
               {
                  nameComboBox.addItem(name);
               }
            }
         }
      });

      sourceComboBox.setSelectedIndex(0);

      outcomingFrame.add(outcomingPanel, BorderLayout.CENTER);

      outcomingFrame.add(buttonsPanel, BorderLayout.SOUTH);

      outcomingFrame.setSize(700, 225);

      outcomingFrame.setMinimumSize(new Dimension(375, 225));

      outcomingFrame.setResizable(true);

      outcomingFrame.setLocationRelativeTo(null);

      outcomingFrame.setVisible(true);

      logger.info("OutcomingsFrame was started.");
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
