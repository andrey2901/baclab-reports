package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.model.Unit;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.SourceRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.UnitRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup.IncomingPopupMessager;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@Component
public class IncomingFrame
{
   private static final Logger logger = Logger.getLogger(IncomingFrame.class);
   private String title;
   private String productNameLabel;
   private String unitNameLabel;
   private String priceNameLabel;
   private String amountNameLabel;
   private String sourceNameLabel;
   private String dateNameLabel;
   private String incomingButtonLabel;
   private String closeButtonLabel;

   private @Autowired DatePicker datePicker;
   private @Autowired UnitRepository unitRepository;
   private @Autowired SourceRepository sourceRepository;
   private @Autowired ProductRepository productRepository;
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired ProductStorageTable productStorageTable;
   private @Autowired IncomingPopupMessager incomingPopupMessager;

   private JDatePickerImpl datePickerImpl;
   private JButton closeButton;
   private JButton incomingButton;
   private JComboBox<String> incomingNameComboBox;
   private JComboBox<String> incomingCostComboBox;
   private JTextField incomingAmountTextField;
   private JComboBox<String> incomingSourceComboBox;
   private JComboBox<String> incomingUnitComboBox;
   private List<Unit> units;

   @Autowired
   public IncomingFrame(MessageByLocaleService messageByLocaleService)
   {
      title = messageByLocaleService.getMessage("frame.incoming.title");
      productNameLabel = messageByLocaleService.getMessage("message.popup.inform.product.label");
      unitNameLabel = messageByLocaleService.getMessage("message.popup.inform.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("message.popup.inform.price.label");
      amountNameLabel = messageByLocaleService.getMessage("message.popup.inform.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("message.popup.inform.source.label");
      dateNameLabel = messageByLocaleService.getMessage("message.popup.inform.date.label");
      incomingButtonLabel = messageByLocaleService.getMessage("button.debit.label");
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
   }

   public void init()
   {
      datePickerImpl = datePicker.getDatePicker();
      units = (List<Unit>) unitRepository.findAll();
      JFrame incomingFrame = new JFrame(title);
      incomingFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close(incomingFrame);
         }
      });
      closeButton = new JButton(closeButtonLabel);
      closeButton.addActionListener(l -> close(incomingFrame));
      incomingButton = new JButton(incomingButtonLabel);
      incomingButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (checkInputData())
            {
               if (!checkUnitByName((String) incomingUnitComboBox.getSelectedItem()))
               {
                  Unit unit = new Unit();
                  unit.setName((String) incomingUnitComboBox.getSelectedItem());
                  unit = unitRepository.save(unit);
                  if (unit.getId() != null)
                  {
                     units.add(unit);
                  }
               }
               Product product = new Product();
               product.setName((String) incomingNameComboBox.getSelectedItem());
               product.setPrice(Double.valueOf(((String) incomingCostComboBox.getSelectedItem()).replace(",", ".")));
               product.setAmount(Double.valueOf(incomingAmountTextField.getText().replace(",", ".")));
               product.setSource(sourceRepository.findByName((String) incomingSourceComboBox.getSelectedItem()));
               product.setUnit(unitRepository.findByName((String) incomingUnitComboBox.getSelectedItem()));
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
               close(incomingFrame);
            }
         }
      });
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(incomingButton);
      buttonsPanel.add(closeButton);
      JPanel incomingPanel = new JPanel(new GridBagLayout());
      incomingPanel.add(new JLabel(productNameLabel), position(0, 0));
      incomingNameComboBox = new JComboBox<String>();
      incomingNameComboBox.setEditable(true);
      List<String> names = productRepository.getUniqueProductNamesOrderedByName();
      if (!names.isEmpty())
      {
         for (String name : names)
         {
            incomingNameComboBox.addItem(name);
         }
      }
      incomingNameComboBox.setSelectedItem("");
      incomingPanel.add(incomingNameComboBox, position(1, 0));
      incomingPanel.add(new JLabel(unitNameLabel), position(0, 1));
      incomingUnitComboBox = new JComboBox<String>();
      incomingUnitComboBox.setEditable(true);
      incomingPanel.add(incomingUnitComboBox, position(1, 1));
      for (Unit unit : units)
      {
         incomingUnitComboBox.addItem(unit.getName());
      }
      incomingUnitComboBox.setSelectedItem("");
      incomingPanel.add(new JLabel(priceNameLabel), position(0, 2));
      incomingCostComboBox = new JComboBox<String>();
      incomingCostComboBox.setEditable(true);
      incomingPanel.add(incomingCostComboBox, position(1, 2));
      incomingPanel.add(new JLabel(amountNameLabel), position(0, 3));
      incomingAmountTextField = new JTextField();
      incomingPanel.add(incomingAmountTextField, position(1, 3));
      incomingSourceComboBox = new JComboBox<String>();
      for (Source source : sourceRepository.findAll())
      {
         incomingSourceComboBox.addItem(source.getName());
      }
      incomingSourceComboBox.setSelectedIndex(incomingSourceComboBox.getItemCount() - 1);
      incomingPanel.add(new JLabel(sourceNameLabel), position(0, 4));
      incomingPanel.add(incomingSourceComboBox, position(1, 4));
      incomingPanel.add(new JLabel(dateNameLabel), position(0, 5));
      incomingPanel.add(datePickerImpl, position(1, 5));
      incomingUnitComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (!(((String) incomingNameComboBox.getSelectedItem()) == null
                  || ((String) incomingNameComboBox.getSelectedItem()).isEmpty()))
            {
               incomingCostComboBox.removeAllItems();
               String productName = (String) incomingNameComboBox.getSelectedItem();
               String unitName = (String) incomingUnitComboBox.getSelectedItem();
               List<Double> prices = productRepository.getUniquePricesByProductNameAndUnitName(productName, unitName);
               if (!prices.isEmpty())
               {
                  for (Double price : prices)
                  {
                     incomingCostComboBox.addItem(Double.toString(price));
                  }
               }
            }
         }
      });
      incomingNameComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String productName = (String) incomingNameComboBox.getSelectedItem();
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
            incomingUnitComboBox.removeAllItems();
            for (String unit : unitNamesForProduct)
            {
               incomingUnitComboBox.addItem(unit);
            }
            for (String unit : unitNames)
            {
               incomingUnitComboBox.addItem(unit);
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

   private boolean checkInputData()
   {
      boolean result = true;

      if (incomingNameComboBox.getSelectedItem() == null || ((String) incomingNameComboBox.getSelectedItem()).isEmpty())
      {
         incomingPopupMessager.productEmpty();
         result = false;
      }
      if (incomingUnitComboBox.getSelectedItem() == null || ((String) incomingUnitComboBox.getSelectedItem()).isEmpty())
      {
         incomingPopupMessager.unitEmpty();
         result = false;
      }
      if (incomingCostComboBox.getSelectedItem() == null || ((String) incomingCostComboBox.getSelectedItem()).isEmpty())
      {
         incomingPopupMessager.priceEmpty();
         result = false;
      }
      if (incomingAmountTextField.getText() == null || incomingAmountTextField.getText().isEmpty())
      {
         incomingPopupMessager.amountEmpty();
         result = false;
      }
      if (datePickerImpl.getJFormattedTextField().getText() == null
            || datePickerImpl.getJFormattedTextField().getText().isEmpty())
      {
         incomingPopupMessager.dateEmpty();
         result = false;
      }
      return result;
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
      logger.info("IncomingsFrame was closed.");
   }
}
