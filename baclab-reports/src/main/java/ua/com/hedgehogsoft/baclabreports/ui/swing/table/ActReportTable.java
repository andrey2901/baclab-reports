package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ua.com.hedgehogsoft.baclabreports.cache.SourceCache;
import ua.com.hedgehogsoft.baclabreports.cache.UnitCache;
import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.service.ActReportCounter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.RemainsStoreTableModel;

@Scope("prototype")
@org.springframework.stereotype.Component
public class ActReportTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @PersistenceContext EntityManager em;
   private @Autowired SourceCache sourcesCache;
   private @Autowired UnitCache unitsCache;

   @Autowired
   public ActReportTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
   }

   public JTable init(String dateFrom, String dateTo, String source)
   {
      ActReportCounter actReportCounter = new ActReportCounter(em, sourcesCache, unitsCache);
      List<Product> products = actReportCounter.count(dateFrom, dateTo, source);

      RemainsStoreTableModel model = new RemainsStoreTableModel(products.size(), sequentialHeaderName,
            productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName, summationHeaderName);
      if (!products.isEmpty())
      {
         for (int i = 0; i < products.size(); i++)
         {
            Product product = products.get(i);

            model.addRow(new Object[] {i + 1,
                                       product.getName(),
                                       unitsCache.findByName(product.getUnit().getName()).getName(),
                                       product.getPrice(),
                                       product.getAmount(),
                                       product.getTotalPrice()});
         }
      }
      setModel(model);
      setPreferredScrollableViewportSize(new Dimension(500, 70));
      setFillsViewportHeight(true);
      setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
      setRowSorter(sorter);
      initColumnSizes();
      return this;
   }

   @Override
   protected void initColumnSizes()
   {
      AbstractTableModel model = (AbstractTableModel) getModel();
      TableColumn column = null;
      Component comp = null;
      int headerWidth = 0;
      int cellWidth = 0;
      TableCellRenderer headerRenderer = getTableHeader().getDefaultRenderer();
      for (int i = 0; i < 7; i++)
      {
         switch (i)
         {
            case 0:
               column = getColumnModel().getColumn(i);
               column.setMaxWidth(50);
               column.setMinWidth(50);
               column.setPreferredWidth(50);
               DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
               renderer.setHorizontalAlignment(SwingConstants.CENTER);
               column.setCellRenderer(renderer);
               break;
            case 1:
               column = getColumnModel().getColumn(i);
               comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
               headerWidth = comp.getPreferredSize().width;
               comp = getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(this,
                     model.getColumnName(i), false, false, 0, i);
               cellWidth = comp.getPreferredSize().width;
               column.setPreferredWidth(Math.max(headerWidth, cellWidth));
               break;
            case 2:
            case 4:
            case 5:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(100);
               column.setPreferredWidth(100);
               break;
            case 3:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(200);
               column.setPreferredWidth(100);
               break;
         }
      }
   }
}
