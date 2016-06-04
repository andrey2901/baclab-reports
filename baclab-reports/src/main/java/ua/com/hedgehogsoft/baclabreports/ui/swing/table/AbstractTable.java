package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

public abstract class AbstractTable extends JTable
{
   private static final long serialVersionUID = 1L;
   protected String sequentialHeaderName;
   protected String productHeaderName;
   protected String unitHeaderName;
   protected String priceHeaderName;
   protected String amountHeaderName;
   protected String summationHeaderName;
   protected String sourceHeaderName;
   protected String hiddenIdColumnHeaderName = "hiddenIdColumn";

   public AbstractTable(MessageByLocaleService messageByLocaleService)
   {
      sequentialHeaderName = messageByLocaleService.getMessage("table.header.sequential.label");
      productHeaderName = messageByLocaleService.getMessage("table.header.product.label");
      unitHeaderName = messageByLocaleService.getMessage("table.header.unit.label");
      priceHeaderName = messageByLocaleService.getMessage("table.header.price.label");
      amountHeaderName = messageByLocaleService.getMessage("table.header.amount.label");
      summationHeaderName = messageByLocaleService.getMessage("table.header.summation.label");
      sourceHeaderName = messageByLocaleService.getMessage("table.header.source.label");
      this.getTableHeader().setReorderingAllowed(false);
   }

   protected abstract void initColumnSizes();

   public void hideHiddenColumn()
   {
      getColumnModel().removeColumn(
            getColumnModel().getColumn(((AbstractTableModel) getModel()).findColumn(hiddenIdColumnHeaderName)));
   }
}
