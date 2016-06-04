package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.model.Outcoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;

public class MovementsReportTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;
   //private String movementsHeaderName;
   private String hiddenIdColumnHeaderName;

   public MovementsReportTableModel(int rowsSize,
                                    String sequentialHeaderName,
                                    String productHeaderName,
                                    String unitHeaderName,
                                    String movementsHeaderName,
                                    String priceHeaderName,
                                    String amountHeaderName,
                                    String summationHeaderName,
                                    String hiddenIdColumnHeaderName,
                                    String sourceHeaderName)
   {
      super(rowsSize, sequentialHeaderName, productHeaderName, unitHeaderName, movementsHeaderName, priceHeaderName,
            amountHeaderName, summationHeaderName, hiddenIdColumnHeaderName, sourceHeaderName);
      this.sequentialHeaderName = sequentialHeaderName;
      this.productHeaderName = productHeaderName;
      this.unitHeaderName = unitHeaderName;
      this.priceHeaderName = priceHeaderName;
      this.amountHeaderName = amountHeaderName;
      this.summationHeaderName = summationHeaderName;
      this.hiddenIdColumnHeaderName = hiddenIdColumnHeaderName;
      this.sourceHeaderName = sourceHeaderName;
   }

   public void addIncoming(Incoming incoming)
   {
      Product product = incoming.getProduct();
      Object[] row = new Object[] {getRowCount() + 1,
                                   product.getName(),
                                   product.getUnit().getName(),
                                   new DateLabelFormatter().dateToString(incoming.getDate()),
                                   product.getPrice(),
                                   incoming.getAmount(),
                                   new BigDecimal(product.getPrice() * incoming.getAmount())
                                         .setScale(2, RoundingMode.HALF_EVEN).doubleValue(),
                                   incoming.getId(),
                                   product.getSource().getName()};
      addRow(row);
   }

   public void addOutcoming(Outcoming outcoming)
   {
      Product product = outcoming.getProduct();
      Object[] row = new Object[] {getRowCount() + 1,
                                   product.getName(),
                                   product.getUnit().getName(),
                                   new DateLabelFormatter().dateToString(outcoming.getDate()),
                                   product.getPrice(),
                                   outcoming.getAmount(),
                                   new BigDecimal(product.getPrice() * outcoming.getAmount())
                                         .setScale(2, RoundingMode.HALF_EVEN).doubleValue(),
                                   outcoming.getId(),
                                   product.getSource().getName()};
      addRow(row);
   }

   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }

   public long getSelectedId(int selectedRow)
   {
      return (long) getValueAt(selectedRow, findColumn(hiddenIdColumnHeaderName));
   }

   public void removeRow(int selectedRow)
   {
      rows.remove(selectedRow);
      fireTableRowsDeleted(selectedRow, selectedRow);
   }
}
