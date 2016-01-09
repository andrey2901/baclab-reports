package ua.com.hedgehogsoft.baclabreports.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
      List<String> names = productRepository.getUniqueProductNames();
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
      incomingUnitComboBox.addActionListener(null);
      incomingNameComboBox.addActionListener(null);
      incomingsFrame.add(incomingPanel, BorderLayout.CENTER);
      incomingsFrame.add(buttonsPanel, BorderLayout.SOUTH);
      incomingsFrame.setSize(700, 225);
      incomingsFrame.setMinimumSize(new Dimension(375, 225));
      incomingsFrame.setResizable(true);
      incomingsFrame.setLocationRelativeTo(null);
      incomingsFrame.setVisible(true);
      logger.info("IncomingsFrame was started.");
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
