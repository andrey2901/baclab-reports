package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

public class IncomingsReportTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;

   public IncomingsReportTableModel(int rowsSize, String... columnNames)
   {
      super(rowsSize, columnNames);
   }

   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }
}
