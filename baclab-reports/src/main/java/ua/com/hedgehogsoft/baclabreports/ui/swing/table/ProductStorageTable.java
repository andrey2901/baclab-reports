package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.ProductStoreTableModel;

@org.springframework.stereotype.Component
public class ProductStorageTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @Autowired ProductRepository productRepository;

   @Autowired
   public ProductStorageTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
   }

   @PostConstruct
   public JTable init()
   {
      List<Product> products = (List<Product>) productRepository.findAll();
      ProductStoreTableModel model = new ProductStoreTableModel(products.size(), sequentialHeaderName,
            productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName, summationHeaderName,
            hiddenIdColumnHeaderName, sourceHeaderName);

      if (!products.isEmpty())
      {
         for (int i = 0; i < products.size(); i++)
         {
            Product product = products.get(i);
            model.addProduct(product);
         }
      }
      setModel(model);
      setPreferredScrollableViewportSize(new Dimension(500, 70));
      setFillsViewportHeight(true);
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
      setRowSorter(sorter);
      initColumnSizes();
      hideHiddenColumn();
      return this;
   }

   protected void initColumnSizes()
   {
      TableColumn column;
      Component comp;
      int headerWidth = 0;
      int cellWidth = 0;

      AbstractTableModel model = (AbstractTableModel) getModel();
      TableCellRenderer headerRenderer = getTableHeader().getDefaultRenderer();
      for (int i = 0; i < 8; i++)
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
            case 3:
            case 4:
            case 5:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(100);
               column.setPreferredWidth(100);
               break;
            case 6:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(200);
               column.setPreferredWidth(100);
               break;
         }
      }
   }
}
