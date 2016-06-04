package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report;

import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;

public abstract class ReportFrame extends Frame
{
   protected String closeButtonLabel;
   protected String saveButtonLabel;
   protected String deleteButtonLabel;
   protected String dateLabelTo;
   protected String dateLabelFrom;
   protected String dateLabelOn;
   protected String dateRangeLabelBegin;
   protected String dateRangeLabelEnd;

   @Override
   protected void localize()
   {
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
      saveButtonLabel = messageByLocaleService.getMessage("button.save.label");
      dateLabelFrom = messageByLocaleService.getMessage("date.label.from");
      dateLabelTo = messageByLocaleService.getMessage("date.label.to");
      dateLabelOn = messageByLocaleService.getMessage("date.label.on");
      deleteButtonLabel = messageByLocaleService.getMessage("button.delete.label");
      dateRangeLabelBegin = messageByLocaleService.getMessage("date.range.label.begin");
      dateRangeLabelEnd = messageByLocaleService.getMessage("date.range.label.end");
   }
}
