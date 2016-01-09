package ua.com.hedgehogsoft.baclabreports.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.model.Unit;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.SourceRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.UnitRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;

@Component
public class IncomingsFrame
{
   private static final Logger logger = Logger.getLogger(IncomingsFrame.class);
   private String title;
   private String productNameLabel;
   private String unitNameLabel;
   private String priceNameLabel;
   private String amountNameLabel;
   private String sourceNameLabel;
   private String dateNameLabel;
   private String incomingButtonName;
   private String closeButtonLabel;
   private String popupErrorLabel;
   private String productEmptyErrorMessage;
   private String unitEmptyErrorMessage;
   private String priceEmptyErrorMessage;
   private String amountEmptyErrorMessage;
   private String dateEmptyErrorMessage;

   private @Autowired DatePicker datePicker;
   private @Autowired UnitRepository unitRepository;
   private @Autowired SourceRepository sourceRepository;
   private @Autowired ProductRepository productRepository;

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
   public IncomingsFrame(MessageByLocaleService messageByLocaleService)
   {
      title = messageByLocaleService.getMessage("frame.incoming.title");
      productNameLabel = messageByLocaleService.getMessage("frame.incoming.product.label");
      unitNameLabel = messageByLocaleService.getMessage("frame.incoming.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("frame.incoming.price.label");
      amountNameLabel = messageByLocaleService.getMessage("frame.incoming.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("frame.incoming.source.label");
      dateNameLabel = messageByLocaleService.getMessage("frame.incoming.date.label");
      incomingButtonName = messageByLocaleService.getMessage("button.debit.label");
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
      popupErrorLabel = messageByLocaleService.getMessage("message.popup.error.label");
      productEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.product.empty.text");
      unitEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.unit.empty.text");
      priceEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.price.empty.text");
      amountEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.amount.empty.text");
      dateEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.date.empty.text");
   }

   public void init()
   {
      datePickerImpl = datePicker.getDatePicker();
      units = (List<Unit>) unitRepository.findAll();
      JFrame incomingsFrame = new JFrame(title);
      incomingsFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close(incomingsFrame);
         }
      });
      closeButton = new JButton(closeButtonLabel);
      closeButton.addActionListener(l -> close(incomingsFrame));
      incomingButton = new JButton(incomingButtonName);
      incomingButton.addActionListener(null);
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
      incomingsFrame.add(incomingPanel, BorderLayout.CENTER);
      incomingsFrame.add(buttonsPanel, BorderLayout.SOUTH);
      incomingsFrame.setSize(700, 225);
      incomingsFrame.setMinimumSize(new Dimension(375, 225));
      incomingsFrame.setResizable(true);
      incomingsFrame.setLocationRelativeTo(null);
      incomingsFrame.setVisible(true);
      logger.info("IncomingsFrame was started.");
   }

   private boolean checkInputData()
   {
      boolean result = true;

      if (incomingNameComboBox.getSelectedItem() == null || ((String) incomingNameComboBox.getSelectedItem()).isEmpty())
      {
         JOptionPane.showMessageDialog(null, productEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      if (incomingUnitComboBox.getSelectedItem() == null || ((String) incomingUnitComboBox.getSelectedItem()).isEmpty())
      {
         JOptionPane.showMessageDialog(null, unitEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      if (incomingCostComboBox.getSelectedItem() == null || ((String) incomingCostComboBox.getSelectedItem()).isEmpty())
      {
         JOptionPane.showMessageDialog(null, priceEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      if (incomingAmountTextField.getText() == null || incomingAmountTextField.getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, amountEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      if (datePickerImpl.getJFormattedTextField().getText() == null
            || datePickerImpl.getJFormattedTextField().getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, dateEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
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
      logger.info("IncomingsFrame was closed.");
   }
}
