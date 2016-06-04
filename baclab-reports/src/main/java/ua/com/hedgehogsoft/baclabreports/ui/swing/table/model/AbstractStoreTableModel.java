package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractStoreTableModel extends AbstractTableModel
{
   private static final long serialVersionUID = 1L;
   protected List<Object[]> rows;
   protected String[] columnNames;
   protected String sequentialHeaderName;
   protected String productHeaderName;
   protected String unitHeaderName;
   protected String priceHeaderName;
   protected String amountHeaderName;
   protected String summationHeaderName;
   protected String sourceHeaderName;

   protected AbstractStoreTableModel(int rowsSize, String... columnNames)
   {
      rows = new ArrayList<>(rowsSize);
      this.columnNames = columnNames;
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
