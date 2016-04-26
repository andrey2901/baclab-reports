package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public class ProductStoreTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;
   private String sourceHeaderName = null;

   public ProductStoreTableModel(int rowsSize,
                                 String sequentialHeaderName,
                                 String productHeaderName,
                                 String unitHeaderName,
                                 String priceHeaderName,
                                 String amountHeaderName,
                                 String summationHeaderName,
                                 String sourceHeaderName)
   {
      super(rowsSize, sequentialHeaderName, productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName,
            summationHeaderName);
      this.sourceHeaderName = sourceHeaderName;
      this.columnNames = new String[] {this.sequentialHeaderName,
                                       this.productHeaderName,
                                       this.unitHeaderName,
                                       this.priceHeaderName,
                                       this.amountHeaderName,
                                       this.summationHeaderName,
                                       this.sourceHeaderName};
   }

   public void addProduct(Product product)
   {
      Object[] row = new Object[] {product.getId(),
                                   product.getName(),
                                   product.getUnit().getName(),
                                   product.getPrice(),
                                   product.getAmount(),
                                   product.getTotalPrice(),
                                   product.getSource().getName()};

      addRow(row);
   }

   public void updateProduct(Product product)
   {
      int rowIndex = 0;
      for (int i = 0; i < getRowCount(); i++)
      {
         if (rows.get(i)[findColumn(sequentialHeaderName)] == product.getId())
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
      row[findColumn(sourceHeaderName)] = product.getSource().getName();
      fireTableCellUpdated(rowIndex, findColumn(productHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(unitHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(priceHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(amountHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(summationHeaderName));
      fireTableCellUpdated(rowIndex, findColumn(sourceHeaderName));
   }
}
