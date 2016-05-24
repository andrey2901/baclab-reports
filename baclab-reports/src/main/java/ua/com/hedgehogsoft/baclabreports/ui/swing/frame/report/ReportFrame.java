package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report;

import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;

public abstract class ReportFrame extends Frame
{
   protected String closeButtonLabel;
   protected String printButtonLabel;
   protected String dateLabelTo;
   protected String dateLabelFrom;

   @Override
   protected void localize()
   {
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
      printButtonLabel = messageByLocaleService.getMessage("button.print.label");
      dateLabelFrom = messageByLocaleService.getMessage("date.label.from");
      dateLabelTo = messageByLocaleService.getMessage("date.label.to");
   }
}
