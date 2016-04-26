package ua.com.hedgehogsoft.baclabreports.ui.swing.table.model;

public class RemainsStoreTableModel extends AbstractStoreTableModel
{
   private static final long serialVersionUID = 1L;

   public RemainsStoreTableModel(int rowsSize,
                                 String sequentialHeaderName,
                                 String productHeaderName,
                                 String unitHeaderName,
                                 String priceHeaderName,
                                 String amountHeaderName,
                                 String summationHeaderName)
   {
      super(rowsSize, sequentialHeaderName, productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName,
            summationHeaderName);
   }
}
