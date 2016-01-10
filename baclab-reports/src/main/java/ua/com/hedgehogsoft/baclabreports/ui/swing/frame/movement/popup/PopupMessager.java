package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.popup;

import javax.swing.JOptionPane;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;

public abstract class PopupMessager extends Frame
{
   protected String productNameLabel;
   protected String unitNameLabel;
   protected String priceNameLabel;
   protected String amountNameLabel;
   protected String sourceNameLabel;
   protected String dateNameLabel;
   protected String totalPriceLabel;

   protected String popupErrorLabel;
   protected String productEmptyErrorMessage;
   protected String unitEmptyErrorMessage;
   protected String priceEmptyErrorMessage;
   protected String amountEmptyErrorMessage;
   protected String dateEmptyErrorMessage;

   protected PopupMessager(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
      productNameLabel = messageByLocaleService.getMessage("message.popup.inform.product.label");
      unitNameLabel = messageByLocaleService.getMessage("message.popup.inform.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("message.popup.inform.price.label");
      amountNameLabel = messageByLocaleService.getMessage("message.popup.inform.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("message.popup.inform.source.label");
      dateNameLabel = messageByLocaleService.getMessage("message.popup.inform.date.label");
      totalPriceLabel = messageByLocaleService.getMessage("message.popup.inform.summation.label");

      popupErrorLabel = messageByLocaleService.getMessage("message.popup.error.label");
      productEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.product.empty.text");
      unitEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.unit.empty.text");
      priceEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.price.empty.text");
      amountEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.amount.empty.text");
      dateEmptyErrorMessage = messageByLocaleService.getMessage("message.popup.error.date.empty.text");
   }

   public void productEmpty()
   {
      JOptionPane.showMessageDialog(null, productEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }

   public void unitEmpty()
   {
      JOptionPane.showMessageDialog(null, unitEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }

   public void priceEmpty()
   {
      JOptionPane.showMessageDialog(null, priceEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }

   public void amountEmpty()
   {
      JOptionPane.showMessageDialog(null, amountEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }

   public void dateEmpty()
   {
      JOptionPane.showMessageDialog(null, dateEmptyErrorMessage, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
   }
}
