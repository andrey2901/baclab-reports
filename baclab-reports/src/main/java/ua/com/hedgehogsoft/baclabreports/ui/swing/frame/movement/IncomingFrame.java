package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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

import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.model.Unit;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup.IncomingPopupMessager;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@Component
public class IncomingFrame extends MovementFrame
{
   private static final Logger logger = Logger.getLogger(IncomingFrame.class);
   private String incomingButtonLabel;
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired IncomingPopupMessager incomingPopupMessager;
   private JFrame incomingFrame;
   private JButton closeButton;
   private JButton incomingButton;
   private List<Unit> units;

   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.incoming.title");
      incomingButtonLabel = messageByLocaleService.getMessage("button.debit.label");
   }

   public void init()
   {
      datePickerImpl = datePicker.getDatePicker();
      units = (List<Unit>) unitRepository.findAll();
      incomingFrame = new JFrame(title);
      incomingFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close();
         }
      });
      closeButton = new JButton(closeButtonLabel);
      closeButton.addActionListener(l -> close());
      incomingButton = new JButton(incomingButtonLabel);
      incomingButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (checkInputData(incomingPopupMessager))
            {
               if (!checkUnitByName((String) unitComboBox.getSelectedItem()))
               {
                  Unit unit = new Unit();
                  unit.setName((String) unitComboBox.getSelectedItem());
                  unit = unitRepository.save(unit);
                  if (unit.getId() != null)
                  {
                     units.add(unit);
                  }
               }
               Product product = new Product();
               product.setName((String) nameComboBox.getSelectedItem());
               product.setPrice(Double.valueOf(((String) costComboBox.getSelectedItem()).replace(",", ".")));
               product.setAmount(Double.valueOf(amountTextField.getText().replace(",", ".")));
               product.setSource(sourceRepository.findByName((String) sourceComboBox.getSelectedItem()));
               product.setUnit(unitRepository.findByName((String) unitComboBox.getSelectedItem()));
               Product existedProduct = productRepository.getProductByNameAndPriceAndSourceAndUnit(product.getName(),
                     product.getPrice(), product.getSource().getId(), product.getUnit().getId());
               boolean wasUpdated = false;
               if (existedProduct == null)
               {
                  existedProduct = productRepository.save(product);
                  if (existedProduct != null)
                  {
                     ProductStoreTableModel model = (ProductStoreTableModel) productStorageTable.getModel();
                     model.addProduct(product);
                     wasUpdated = true;
                  }
               }
               else
               {
                  existedProduct.setAmount(product.getAmount() + existedProduct.getAmount());
                  if (productRepository.updateAmount(existedProduct.getId(), existedProduct.getAmount()) != 0)
                  {
                     ProductStoreTableModel model = (ProductStoreTableModel) productStorageTable.getModel();
                     model.updateProduct(existedProduct);
                     wasUpdated = true;
                  }
               }

               if (wasUpdated)
               {
                  Incoming incoming = new Incoming();
                  incoming.setProduct(existedProduct);
                  incoming.setAmount(product.getAmount());
                  DateLabelFormatter formatter = new DateLabelFormatter();
                  Date date = (Date) formatter.stringToValue(datePickerImpl.getJFormattedTextField().getText());
                  incoming.setDate(date);
                  incomingRepository.save(incoming);
                  logger.info("Incomings were performed.");
                  incomingPopupMessager.infoPopup(product);
               }
               close();
            }
         }
      });
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(incomingButton);
      buttonsPanel.add(closeButton);
      JPanel incomingPanel = new JPanel(new GridBagLayout());
      incomingPanel.add(new JLabel(productNameLabel), position(0, 0));
      nameComboBox = new JComboBox<String>();
      nameComboBox.setEditable(true);
      List<String> names = productRepository.getUniqueProductNamesOrderedByName();
      if (!names.isEmpty())
      {
         for (String name : names)
         {
            nameComboBox.addItem(name);
         }
      }
      nameComboBox.setSelectedItem("");
      incomingPanel.add(nameComboBox, position(1, 0));
      incomingPanel.add(new JLabel(unitNameLabel), position(0, 1));
      unitComboBox = new JComboBox<String>();
      unitComboBox.setEditable(true);
      incomingPanel.add(unitComboBox, position(1, 1));
      for (Unit unit : units)
      {
         unitComboBox.addItem(unit.getName());
      }
      unitComboBox.setSelectedItem("");
      incomingPanel.add(new JLabel(priceNameLabel), position(0, 2));
      costComboBox = new JComboBox<String>();
      costComboBox.setEditable(true);
      incomingPanel.add(costComboBox, position(1, 2));
      incomingPanel.add(new JLabel(amountNameLabel), position(0, 3));
      amountTextField = new JTextField();
      incomingPanel.add(amountTextField, position(1, 3));
      sourceComboBox = new JComboBox<String>();
      for (Source source : sourceRepository.findAll())
      {
         sourceComboBox.addItem(source.getName());
      }
      sourceComboBox.setSelectedIndex(sourceComboBox.getItemCount() - 1);
      incomingPanel.add(new JLabel(sourceNameLabel), position(0, 4));
      incomingPanel.add(sourceComboBox, position(1, 4));
      incomingPanel.add(new JLabel(dateNameLabel), position(0, 5));
      incomingPanel.add(datePickerImpl, position(1, 5));
      unitComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (!(((String) nameComboBox.getSelectedItem()) == null
                  || ((String) nameComboBox.getSelectedItem()).isEmpty()))
            {
               costComboBox.removeAllItems();
               String productName = (String) nameComboBox.getSelectedItem();
               String unitName = (String) unitComboBox.getSelectedItem();
               List<Double> prices = productRepository.getUniquePricesByProductNameAndUnitName(productName, unitName);
               if (!prices.isEmpty())
               {
                  for (Double price : prices)
                  {
                     costComboBox.addItem(Double.toString(price));
                  }
               }
            }
         }
      });
      nameComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String productName = (String) nameComboBox.getSelectedItem();
            List<String> unitNamesForProduct = productRepository.getUniqueUnitNamesByProductName(productName);
            /*
             * The next part of code resort unit combo box in order to be units
             * for this product at first and all others lately.
             */
            List<Unit> persistedUnits = unitRepository.findAll();
            List<String> unitNames = new ArrayList<>();
            for (Unit unit : persistedUnits)
            {
               unitNames.add(unit.getName());
            }
            unitNames.removeAll(unitNamesForProduct);
            unitComboBox.removeAllItems();
            for (String unit : unitNamesForProduct)
            {
               unitComboBox.addItem(unit);
            }
            for (String unit : unitNames)
            {
               unitComboBox.addItem(unit);
            }
         }
      });
      incomingFrame.add(incomingPanel, BorderLayout.CENTER);
      incomingFrame.add(buttonsPanel, BorderLayout.SOUTH);
      incomingFrame.setSize(700, 225);
      incomingFrame.setMinimumSize(new Dimension(375, 225));
      incomingFrame.setResizable(true);
      incomingFrame.setLocationRelativeTo(null);
      incomingFrame.setVisible(true);
      logger.info("IncomingsFrame was started.");
   }

   private boolean checkUnitByName(String name)
   {
      for (Unit unit : units)
      {
         if (unit.getName().equals(name))
            return true;
      }

      return false;
   }

   private void close()
   {
      incomingFrame.dispose();
      logger.info("IncomingsFrame was closed.");
   }
}
