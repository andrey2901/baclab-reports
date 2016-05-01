package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

public class IncomingsReportTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;
   private String indexColumnName;

   public IncomingsReportTableModel(int rowsSize, String... columnNames)
   {
      super(rowsSize, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }

   public String getIndexColumnName()
   {
      return indexColumnName;
   }

   public void setIndexColumnName(String indexColumnName)
   {
      this.indexColumnName = indexColumnName;
   }

   public void removeRow(int selectedRow)
   {
      rows.remove(selectedRow);
      fireTableRowsDeleted(selectedRow, selectedRow);
   }
}
