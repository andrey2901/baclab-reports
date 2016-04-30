package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractStoreTableModel extends AbstractTableModel
{
   private static final long serialVersionUID = 1L;
   protected List<Object[]> rows = null;
   protected String[] columnNames = null;
   protected String sequentialHeaderName = null;
   protected String productHeaderName = null;
   protected String unitHeaderName = null;
   protected String priceHeaderName = null;
   protected String amountHeaderName = null;
   protected String summationHeaderName = null;

   protected AbstractStoreTableModel(int rowsSize, String... columnNames)
   {
      rows = new ArrayList<>(rowsSize);
      this.columnNames = columnNames;
   }

   protected AbstractStoreTableModel(int rowsSize,
                                     String sequentialHeaderName,
                                     String productHeaderName,
                                     String unitHeaderName,
                                     String priceHeaderName,
                                     String amountHeaderName,
                                     String summationHeaderName)
   {
      rows = new ArrayList<>(rowsSize);
      this.sequentialHeaderName = sequentialHeaderName;
      this.productHeaderName = productHeaderName;
      this.unitHeaderName = unitHeaderName;
      this.priceHeaderName = priceHeaderName;
      this.amountHeaderName = amountHeaderName;
      this.summationHeaderName = summationHeaderName;
      this.columnNames = new String[] {this.sequentialHeaderName,
                                       this.productHeaderName,
                                       this.unitHeaderName,
                                       this.priceHeaderName,
                                       this.amountHeaderName,
                                       this.summationHeaderName};
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

   @Override
   public void setValueAt(Object value, int row, int col)
   {
      fireTableCellUpdated(row, col);
   }
}
