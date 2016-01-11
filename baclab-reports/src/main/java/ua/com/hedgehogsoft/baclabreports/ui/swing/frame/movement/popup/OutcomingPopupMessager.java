package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Product;

@Component
public class OutcomingPopupMessager extends PopupMessager
{
   private String outcomingInfoLabel;
   private String amountInsufficientOnDateErrorMessage;
   private String amountInsufficientErrorMessage;

   protected void localize()
   {
      super.localize();
      outcomingInfoLabel = messageByLocaleService.getMessage("message.popup.info.outcoming.label");
      amountInsufficientOnDateErrorMessage = messageByLocaleService
            .getMessage("message.popup.error.amount.insufficient.on.date.text");
      amountInsufficientErrorMessage = messageByLocaleService
            .getMessage("message.popup.error.amount.insufficient.text");
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
      JOptionPane.showMessageDialog(null, panel, outcomingInfoLabel, JOptionPane.INFORMATION_MESSAGE);
   }

   public void amountInsufficientOnDate()
   {
      JOptionPane.showMessageDialog(null, amountInsufficientOnDateErrorMessage, popupErrorLabel,
            JOptionPane.ERROR_MESSAGE);
   }

   public void amountInsufficient()
   {
      JOptionPane.showMessageDialog(null, amountInsufficientErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }
}
