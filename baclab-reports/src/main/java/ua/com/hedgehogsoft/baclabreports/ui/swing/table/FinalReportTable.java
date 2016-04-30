package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MultiLineHeaderRenderer;

@org.springframework.stereotype.Component
public class FinalReportTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;

   @Autowired
   public FinalReportTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
   }

   public JTable init()
   {
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
      MultiLineHeaderRenderer rend = new MultiLineHeaderRenderer();
      Enumeration<TableColumn> e = getColumnModel().getColumns();
      while (e.hasMoreElements())
      {
         ((TableColumn) e.nextElement()).setHeaderRenderer(rend);
      }
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
            case 6:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(100);
               column.setPreferredWidth(100);
               break;
            case 7:
               column = getColumnModel().getColumn(i);
               column.setMinWidth(100);
               column.setMaxWidth(200);
               column.setPreferredWidth(100);
               break;
         }
      }
   }

}
