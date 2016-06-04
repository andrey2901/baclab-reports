package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.FinalReportTableModel;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MultiLineHeaderRenderer;

@Scope("prototype")
@org.springframework.stereotype.Component
public class FinalReportTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @Autowired ProductRepository productRepository;
   private @Autowired IncomingRepository incomingRepository;
   private @Autowired OutcomingRepository outcomingRepository;
   private String incomingHeaderName;
   private String outcomingHeaderName;
   private String beginRemainsHeaderName;
   private String endRemainsHeaderName;

   @Autowired
   public FinalReportTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
      this.incomingHeaderName = messageByLocaleService.getMessage("table.report.final.header.incoming.label");
      this.outcomingHeaderName = messageByLocaleService.getMessage("table.report.final.header.outcoming.label");
      this.beginRemainsHeaderName = messageByLocaleService
            .getMessage("table.report.final.header.remains.period.begin.label");
      this.endRemainsHeaderName = messageByLocaleService
            .getMessage("table.report.final.header.remains.period.end.label");
   }

   public JTable init(String dateFrom, String dateTo)
   {
      String[] columnNames = {sequentialHeaderName,
                              productHeaderName,
                              unitHeaderName,
                              beginRemainsHeaderName,
                              incomingHeaderName,
                              outcomingHeaderName,
                              endRemainsHeaderName,
                              sourceHeaderName};
      List<Product> products = new ArrayList<Product>();
      List<Long> ids = productRepository.getProductIds();
      FinalReportTableModel model = new FinalReportTableModel(products.size(), columnNames);
      RemainsCounter remains = new RemainsCounter(productRepository, incomingRepository, outcomingRepository);
      DateLabelFormatter formatter = new DateLabelFormatter();
      Date beginPeriod = (Date) formatter.stringToValue(dateFrom);
      Date endPeriod = (Date) formatter.stringToValue(dateTo);

      int i = 0;

      for (long id : ids)
      {
         double remainOnBeginPeriod = remains.getRemainOfProductOnDate(id, beginPeriod);
         double remainOnEndPeriod = remains.getRemainOfProductOnDate(id, endPeriod);
         double incomingsFromPeriod = incomingRepository.getIncomingsSumFromPeriod(id, beginPeriod, endPeriod);
         double outcomingsFromPeriod = outcomingRepository.getOutcomingsSumFromPeriod(id, beginPeriod, endPeriod);
         Product product = productRepository.getProductById(id);

         if (!(remainOnBeginPeriod == 0.0 && remainOnEndPeriod == 0.0 && incomingsFromPeriod == 0.0
               && outcomingsFromPeriod == 0.0))
         {
            model.addRow(new Object[] {++i,
                                       product.getName(),
                                       product.getUnit().getName(),
                                       remainOnBeginPeriod,
                                       incomingsFromPeriod,
                                       outcomingsFromPeriod,
                                       remainOnEndPeriod,
                                       product.getSource().getName()});
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
