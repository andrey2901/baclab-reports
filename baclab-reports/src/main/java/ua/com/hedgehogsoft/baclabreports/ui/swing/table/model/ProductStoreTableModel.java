package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.com.hedgehogsoft.baclabreports.model.Product;

public class ProductStoreTableModel extends AbstractTableModel
{
   private static final long serialVersionUID = 1L;
   private List<Object[]> rows = null;
   private String[] columnNames = null;
   private String sequentialHeaderName = null;
   private String productHeaderName = null;
   private String unitHeaderName = null;
   private String priceHeaderName = null;
   private String amountHeaderName = null;
   private String summationHeaderName = null;
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
      rows = new ArrayList<>(rowsSize);
      this.sequentialHeaderName = sequentialHeaderName;
      this.productHeaderName = productHeaderName;
      this.unitHeaderName = unitHeaderName;
      this.priceHeaderName = priceHeaderName;
      this.amountHeaderName = amountHeaderName;
      this.summationHeaderName = summationHeaderName;
      this.sourceHeaderName = sourceHeaderName;
      this.columnNames = new String[] {this.sequentialHeaderName,
                                       this.productHeaderName,
                                       this.unitHeaderName,
                                       this.priceHeaderName,
                                       this.amountHeaderName,
                                       this.summationHeaderName,
                                       this.sourceHeaderName};
   }

   @Override
   public int getRowCount()
   {
      return rows.size();
   }

   @Override
   public int getColumnCount()
   {
      return columnNames.length;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex)
   {
      Object[] row = rows.get(rowIndex);

      return row[columnIndex];
   }

   public void setColumnName(int i, String name)
   {
      columnNames[i] = name;
      fireTableStructureChanged();
   }

   @Override
   public String getColumnName(int col)
   {
      return columnNames[col];
   }

   public void addRow(Object[] row)
   {
      int rowCount = getRowCount();
      rows.add(row);
      fireTableRowsInserted(rowCount, rowCount);
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

   @Override
   public void setValueAt(Object value, int row, int col)
   {
      fireTableCellUpdated(row, col);
   }
}
