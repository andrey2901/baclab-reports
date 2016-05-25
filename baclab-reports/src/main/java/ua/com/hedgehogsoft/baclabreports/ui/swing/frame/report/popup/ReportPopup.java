package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.popup;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;

@Component
public class ReportPopup extends ReportFrame
{
   protected String productNameLabel;
   protected String unitNameLabel;
   protected String priceNameLabel;
   protected String amountNameLabel;
   protected String sourceNameLabel;
   protected String dateNameLabel;
   protected String totalPriceLabel;
   protected String removedLabel;
   protected String popupErrorLabel;
   protected String removeAmountInsufficientOnDateErrorMessage;
   private String dateRangePopupLabelPeriod;
   private String dateRangeErrorPopupLabelBegin;
   private String dateRangeErrorPopupLabelEnd;
   private String dateRangeErrorPopupLabelChange;
   private static final Logger logger = Logger.getLogger(ReportPopup.class);

   @Override
   protected void localize()
   {
      super.localize();
      productNameLabel = messageByLocaleService.getMessage("message.popup.info.product.label");
      unitNameLabel = messageByLocaleService.getMessage("message.popup.info.unit.label");
      priceNameLabel = messageByLocaleService.getMessage("message.popup.info.price.label");
      amountNameLabel = messageByLocaleService.getMessage("message.popup.info.amount.label");
      sourceNameLabel = messageByLocaleService.getMessage("message.popup.info.source.label");
      dateNameLabel = messageByLocaleService.getMessage("message.popup.info.date.label");
      totalPriceLabel = messageByLocaleService.getMessage("message.popup.info.summation.label");
      removedLabel = messageByLocaleService.getMessage("message.popup.info.removed.label");
      popupErrorLabel = messageByLocaleService.getMessage("message.popup.error.label");
      removeAmountInsufficientOnDateErrorMessage = messageByLocaleService
            .getMessage("message.popup.error.removed.amount.insufficient.on.date.text");
      dateRangePopupLabelPeriod = messageByLocaleService.getMessage("message.popup.date.range.label.period");
      dateRangeErrorPopupLabelBegin = messageByLocaleService.getMessage("message.popup.error.date.range.begin");
      dateRangeErrorPopupLabelEnd = messageByLocaleService.getMessage("message.popup.error.date.range.end");
      dateRangeErrorPopupLabelChange = messageByLocaleService.getMessage("message.popup.error.date.range.change");
   }

   public void deleteIncomingPopup(Incoming incoming)
   {
      JPanel panel = new JPanel(new GridLayout(7, 2));
      panel.add(new JLabel(dateNameLabel));
      panel.add(new JLabel(new DateLabelFormatter().dateToString(incoming.getDate())));
      panel.add(new JLabel(productNameLabel));
      panel.add(new JLabel(incoming.getProduct().getName()));
      panel.add(new JLabel(amountNameLabel));
      panel.add(new JLabel(Double.toString(incoming.getAmount())));
      panel.add(new JLabel(unitNameLabel));
      panel.add(new JLabel(incoming.getProduct().getUnit().getName()));
      panel.add(new JLabel(priceNameLabel));
      panel.add(new JLabel(Double.toString(incoming.getProduct().getPrice())));
      panel.add(new JLabel(sourceNameLabel));
      panel.add(new JLabel(incoming.getProduct().getSource().getName()));
      panel.add(new JLabel(totalPriceLabel));
      panel.add(new JLabel(Double.toString(incoming.getAmount() * incoming.getProduct().getPrice())));
      JOptionPane.showMessageDialog(null, panel, removedLabel, JOptionPane.INFORMATION_MESSAGE);
   }

   public void deleteIncomingInsufficientErrorPopup()
   {
      JOptionPane.showMessageDialog(null, removeAmountInsufficientOnDateErrorMessage, popupErrorLabel,
            JOptionPane.ERROR_MESSAGE);
   }

   public void createMovementsReportPopup(JDatePickerImpl periodBegin, JDatePickerImpl periodEnd)
   {
      do
      {
         JPanel panel = new JPanel(new GridLayout(2, 2));
         panel.add(new JLabel(dateRangeLabelBegin));
         panel.add(new JLabel(dateRangeLabelEnd));
         panel.add(periodBegin);
         panel.add(periodEnd);
         JOptionPane.showMessageDialog(null, panel, dateRangePopupLabelPeriod, JOptionPane.INFORMATION_MESSAGE);
      }
      while (!checkInputData(periodBegin, periodEnd));
   }

   private boolean checkInputData(JDatePickerImpl datePickerFrom, JDatePickerImpl datePickerTo)
   {
      boolean result = true;
      Date dateFrom = null;
      Date dateTo = null;
      if (datePickerFrom.getJFormattedTextField().getText() == null
            || datePickerFrom.getJFormattedTextField().getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, dateRangeErrorPopupLabelBegin, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      else
      {
         dateFrom = (Date) new DateLabelFormatter().stringToValue(datePickerFrom.getJFormattedTextField().getText());
      }

      if (datePickerTo.getJFormattedTextField().getText() == null
            || datePickerTo.getJFormattedTextField().getText().isEmpty())
      {
         JOptionPane.showMessageDialog(null, dateRangeErrorPopupLabelEnd, popupErrorLabel, JOptionPane.ERROR_MESSAGE);
         result = false;
      }
      else
      {
         dateTo = (Date) new DateLabelFormatter().stringToValue(datePickerTo.getJFormattedTextField().getText());
      }

      if (dateFrom != null && dateTo != null)
      {
         if (dateTo.before(dateFrom))
         {
            JOptionPane.showMessageDialog(null, dateRangeErrorPopupLabelChange, popupErrorLabel,
                  JOptionPane.ERROR_MESSAGE);
            result = false;
         }
      }
      return result;
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

}
