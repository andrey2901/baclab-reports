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

import ua.com.hedgehogsoft.baclabreports.cache.CacheableByName;
import ua.com.hedgehogsoft.baclabreports.cache.ResolvedByNameCache;
import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.cache.UnitCache;
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
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired IncomingPopupMessager incomingPopupMessager;
   private @Autowired UnitCache unitsCache;
   private @Autowired SourceCache sourcesCache;
   private String incomingButtonLabel;
   private JButton incomingButton;
   private JButton closeButton;

   protected void localize()
   {
      super.localize();
      title = messageByLocaleService.getMessage("frame.incoming.title");
      incomingButtonLabel = messageByLocaleService.getMessage("button.debit.label");
   }

   public void init()
   {
      datePickerImpl = datePicker.getDatePicker();
      frame = new JFrame(title);
      frame.addWindowListener(new WindowAdapter()
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
               if (isNewUnitName((String) unitComboBox.getSelectedItem()))
               {
                  addNewUnit((String) unitComboBox.getSelectedItem());
               }
               Product product = new Product();
               product.setName((String) nameComboBox.getSelectedItem());
               product.setPrice(Double.valueOf(((String) costComboBox.getSelectedItem()).replace(",", ".")));
               product.setAmount(Double.valueOf(amountTextField.getText().replace(",", ".")));
               product.setSource(sourcesCache.findByName((String) sourceComboBox.getSelectedItem()));
               product.setUnit(unitsCache.findByName((String) unitComboBox.getSelectedItem()));
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
      for (Unit unit : unitsCache.getAll())
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
      for (Source source : sourcesCache.getAll())
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
            resortNamesForProductInComboBox(unitComboBox, unitNamesForProduct, unitsCache);
            List<String> sourceNamesForProduct = productRepository.getUniqueSourceNamesByProductName(productName);
            resortNamesForProductInComboBox(sourceComboBox, sourceNamesForProduct, sourcesCache);
         }
      });
      frame.add(incomingPanel, BorderLayout.CENTER);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.setSize(700, 225);
      frame.setMinimumSize(new Dimension(375, 225));
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   private boolean isNewUnitName(String name)
   {
      Unit unit = unitsCache.findByName(name);
      if (unit != null)
      {
         return false;
      }
      return true;
   }

   private void addNewUnit(String unitName)
   {
      Unit unit = new Unit();
      unit.setName(unitName);
      unit = unitRepository.save(unit);
      if (unit.getId() != null)
      {
         unitsCache.add(unit);
      }
   }

   private <T extends CacheableByName> void resortNamesForProductInComboBox(JComboBox<String> comboBox,
                                                                            List<String> namesForProduct,
                                                                            ResolvedByNameCache<T> cache)
   {
      List<String> names = new ArrayList<>();
      for (CacheableByName cacheable : cache.getAll())
      {
         names.add(cacheable.getName());
      }
      names.removeAll(namesForProduct);
      comboBox.removeAllItems();
      for (String name : namesForProduct)
      {
         comboBox.addItem(name);
      }
      for (String name : names)
      {
         comboBox.addItem(name);
      }
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }
}
