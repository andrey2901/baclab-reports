package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Product;

@Component
public class IncomingPopupMessager extends PopupMessager
{
   private String incomingInfoLabel;

   @Autowired
   public IncomingPopupMessager(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
      incomingInfoLabel = messageByLocaleService.getMessage("message.popup.inform.incoming.label");
   }

   public void infoPopup(Product product)
   {
      JPanel panel = new JPanel(new GridLayout(6, 2));
      panel.add(new JLabel(productNameLabel + "   "));
      panel.add(new JLabel(product.getName()));
      panel.add(new JLabel(amountNameLabel));
      panel.add(new JLabel(Double.toString(product.getAmount())));
      panel.add(new JLabel(unitNameLabel));
      panel.add(new JLabel(product.getUnit().getName()));
      panel.add(new JLabel(priceNameLabel));
      panel.add(new JLabel(Double.toString(product.getPrice())));
      panel.add(new JLabel(sourceNameLabel));
      panel.add(new JLabel(product.getSource().getName()));
      panel.add(new JLabel(totalPriceLabel));
      panel.add(new JLabel(Double.toString(product.getTotalPrice())));
      JOptionPane.showMessageDialog(null, panel, incomingInfoLabel, JOptionPane.INFORMATION_MESSAGE);
   }
}
