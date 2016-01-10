package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement;

import java.awt.GridBagConstraints;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;

public abstract class MovementFrame extends Frame
{
   protected String productNameLabel;
   protected String unitNameLabel;
   protected String priceNameLabel;
   protected String amountNameLabel;
   protected String sourceNameLabel;
   protected String dateNameLabel;
   protected String closeButtonLabel;

   protected MovementFrame(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
      productNameLabel = messageByLocaleService.getMessage("message.popup.inform.product.label");
      unitNameLabel = messageByLocaleService.getMessage("message.popup.inform.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("message.popup.inform.price.label");
      amountNameLabel = messageByLocaleService.getMessage("message.popup.inform.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("message.popup.inform.source.label");
      dateNameLabel = messageByLocaleService.getMessage("message.popup.inform.date.label");
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
}
