package ua.com.hedgehogsoft.baclabreports.ui.swing.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.model.Incoming;
import ua.com.hedgehogsoft.baclabreports.model.Product;
import ua.com.hedgehogsoft.baclabreports.persistence.IncomingRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.IncomingsReportTableModel;

@org.springframework.stereotype.Component
public class IncomingsReportTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @Autowired IncomingRepository incomingRepository;

   @Autowired
   public IncomingsReportTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
   }

   public JTable init(String from, String to)
   {
      DateLabelFormatter formatter = new DateLabelFormatter();
      List<Incoming> incomings = incomingRepository.getIncomingsFromPeriod((Date) formatter.stringToValue(from),
            (Date) formatter.stringToValue(to));
      String[] columnNames = {sequentialHeaderName,
                              productHeaderName,
                              unitHeaderName,
                              "Дата надходження",
                              priceHeaderName,
                              amountHeaderName,
                              summationHeaderName,
                              sourceHeaderName};
      IncomingsReportTableModel model = new IncomingsReportTableModel(incomings.size(), columnNames);
      model.setIndexColumnName(sequentialHeaderName);
      if (!incomings.isEmpty())
      {
         for (int i = 0; i < incomings.size(); i++)
         {
            Product product = incomings.get(i).getProduct();

            model.addRow(new Object[] {incomings.get(i).getId(),
                                       product.getName(),
                                       product.getUnit().getName(),
                                       formatter.dateToString(incomings.get(i).getDate()),
                                       product.getPrice(),
                                       incomings.get(i).getAmount(),
                                       incomings.get(i).getAmount() * product.getPrice(),
                                       product.getSource().getName()});
         }
      }
      setModel(model);
      setPreferredScrollableViewportSize(new Dimension(500, 70));
      setFillsViewportHeight(true);
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      // RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
      // setRowSorter(sorter);
      initColumnSizes();
      return this;
   }

   @Override
   protected void initColumnSizes()
   {
      TableColumn column;
      Component comp;
      int headerWidth = 0;
      int cellWidth = 0;
      TableCellRenderer headerRenderer = getTableHeader().getDefaultRenderer();
      for (int i = 0; i < 5; i++)
      {
         column = getColumnModel().getColumn(i);
         comp = headerRenderer.getTableCellRendererComponent(this, column.getHeaderValue(), false, false, 0, 0);
         headerWidth = comp.getPreferredSize().width;
         comp = getDefaultRenderer(getModel().getColumnClass(i)).getTableCellRendererComponent(this,
               getModel().getColumnName(i), false, false, 0, i);
         cellWidth = comp.getPreferredSize().width;
         column.setPreferredWidth(Math.max(headerWidth, cellWidth));
      }
   }

   public int getIndexColumn()
   {
      IncomingsReportTableModel model = (IncomingsReportTableModel) getModel();
      return model.findColumn(model.getIndexColumnName());
   }
}
