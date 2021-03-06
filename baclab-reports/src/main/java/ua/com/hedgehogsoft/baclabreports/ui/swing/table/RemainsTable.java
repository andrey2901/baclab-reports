package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.persistence.ProductRepository;
import ua.com.hedgehogsoft.baclabreports.service.RemainsCounter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.RemainsStoreTableModel;

@Scope("prototype")
@org.springframework.stereotype.Component
public class RemainsTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @Autowired ProductRepository productRepository;
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired OutcomingRepository outcomingRepository;

   @Autowired
   public RemainsTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
   }

   public JTable init(String date, String source)
   {
      List<Long> ids = productRepository.getProductIdsBySource(source);
      List<Product> products = new ArrayList<Product>();
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date destinationDate = (Date) formatter.stringToValue(date);
      RemainsCounter remains = new RemainsCounter(productRepository, incomingRepository, outcomingRepository);
      for (long id : ids)
      {
         double remainOnDate = remains.getRemainOfProductOnDate(id, destinationDate);
         if (remainOnDate != 0.0)
         {
            products.add(productRepository.getProductById(id));
         }
      }
      RemainsStoreTableModel model = new RemainsStoreTableModel(products.size(), sequentialHeaderName,
            productHeaderName, unitHeaderName, priceHeaderName, amountHeaderName, summationHeaderName);
      if (!products.isEmpty())
      {
         for (int i = 0; i < products.size(); i++)
         {
            Product product = products.get(i);

            model.addRow(new Object[] {i + 1,
                                       product.getName(),
                                       product.getUnit().getName(),
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