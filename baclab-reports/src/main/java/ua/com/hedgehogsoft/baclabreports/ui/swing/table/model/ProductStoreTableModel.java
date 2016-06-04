package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public class ProductStoreTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;
   private String hiddenIdColumnHeaderName;

   public ProductStoreTableModel(int rowsSize,
                                 String sequentialHeaderName,
                                 String productHeaderName,
                                 String unitHeaderName,
                                 String priceHeaderName,
                                 String amountHeaderName,
                                 String summationHeaderName,
                                 String hiddenIdColumnHeaderName,
                                 String sourceHeaderName)
   {
      super(rowsSize, sequentialHeaderName, productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName,
            summationHeaderName, hiddenIdColumnHeaderName, sourceHeaderName);
      this.sequentialHeaderName = sequentialHeaderName;
      this.productHeaderName = productHeaderName;
      this.unitHeaderName = unitHeaderName;
      this.priceHeaderName = priceHeaderName;
      this.amountHeaderName = amountHeaderName;
      this.summationHeaderName = summationHeaderName;
      this.hiddenIdColumnHeaderName = hiddenIdColumnHeaderName;
      this.sourceHeaderName = sourceHeaderName;
   }

   public void addProduct(Product product)
   {
      Object[] row = new Object[] {getRowCount() + 1,
                                   product.getName(),
                                   product.getUnit().getName(),
                                   product.getPrice(),
                                   product.getAmount(),
                                   product.getTotalPrice(),
                                   product.getId(),
                                   product.getSource().getName()};

      addRow(row);
   }

   public void updateProduct(Product product)
   {
      int rowIndex = 0;
      int columnIndex = findColumn(hiddenIdColumnHeaderName);
      for (int i = 0; i < getRowCount(); i++)
      {
         if (getValueAt(i, columnIndex) == product.getId())
         {
            rowIndex = i;
            break;
         }
      }
      Object[] row = rows.get(rowIndex);
      row[findColumn(productHeaderName)] = product.getName();
      row[findColumn(unitHeaderName)] = product.getUnit().getName();
      row[findColumn(priceHeaderName)] = product.getPrice();
      row[findColumn(amountHeaderName)] = product.getAmount();
      row[findColumn(summationHeaderName)] = product.getTotalPrice();
      row[findColumn(hiddenIdColumnHeaderName)] = product.getId();
      row[findColumn(sourceHeaderName)] = product.getSource().getName();
      fireTableCellUpdated(rowIndex, findColumn(productHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(unitHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(priceHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(amountHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(summationHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(hiddenIdColumnHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(sourceHeaderName));
   }
}
