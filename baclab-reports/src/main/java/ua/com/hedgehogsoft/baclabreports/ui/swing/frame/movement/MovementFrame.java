package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.SourceRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.UnitRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DatePicker;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup.PopupMessager;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;

public abstract class MovementFrame extends Frame
{
   protected String productNameLabel;
   protected String unitNameLabel;
   protected String priceNameLabel;
   protected String amountNameLabel;
   protected String sourceNameLabel;
   protected String dateNameLabel;
   protected String closeButtonLabel;

   protected JComboBox<String> nameComboBox;
   protected JComboBox<String> costComboBox;
   protected JComboBox<String> sourceComboBox;
   protected JComboBox<String> unitComboBox;
   protected JTextField amountTextField;
   protected JDatePickerImpl datePickerImpl;

   protected @Autowired DatePicker datePicker;
   protected @Autowired UnitRepository unitRepository;
   protected @Autowired SourceRepository sourceRepository;
   protected @Autowired ProductRepository productRepository;
   protected @Autowired ProductStorageTable productStorageTable;

   protected void localize()
   {
      productNameLabel = messageByLocaleService.getMessage("frame.product.label");
      unitNameLabel = messageByLocaleService.getMessage("frame.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("frame.price.label");
      amountNameLabel = messageByLocaleService.getMessage("frame.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("frame.source.label");
      dateNameLabel = messageByLocaleService.getMessage("frame.date.label");
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
   }

   protected GridBagConstraints position(int x, int y)
   {
      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = GridBagConstraints.HORIZONTAL;
      switch (x)
      {
         case 0:
            constraints.gridx = 0;
            constraints.weightx = 0;
            constraints.gridwidth = 1;
            break;
         case 1:
            constraints.gridx = 1;
            constraints.weightx = 10;
            constraints.gridwidth = 3;
            break;
      }
      constraints.gridy = y;
      return constraints;
   }

   protected boolean checkInputData(PopupMessager messenger)
   {
      boolean result = true;

      if (nameComboBox.getSelectedItem() == null || ((String) nameComboBox.getSelectedItem()).isEmpty())
      {
         messenger.productEmpty();
         result = false;
      }
      if (unitComboBox.getSelectedItem() == null || ((String) unitComboBox.getSelectedItem()).isEmpty())
      {
         messenger.unitEmpty();
         result = false;
      }
      if (costComboBox.getSelectedItem() == null || ((String) costComboBox.getSelectedItem()).isEmpty())
      {
         messenger.priceEmpty();
         result = false;
      }
      if (amountTextField.getText() == null || amountTextField.getText().isEmpty())
      {
         messenger.amountEmpty();
         result = false;
      }
      if (datePickerImpl.getJFormattedTextField().getText() == null
            || datePickerImpl.getJFormattedTextField().getText().isEmpty())
      {
         messenger.dateEmpty();
         result = false;
      }
      return result;
   }
}
