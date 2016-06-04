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
import ua.com.hedgehogsoft.baclabreports.model.Outcoming;
import ua.com.hedgehogsoft.baclabreports.persistence.OutcomingRepository;
import ua.com.hedgehogsoft.baclabreports.ui.swing.date.DateLabelFormatter;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.model.MovementsReportTableModel;

@org.springframework.stereotype.Component
public class OutcomingsReportTable extends AbstractTable
{
   private static final long serialVersionUID = 1L;
   private @Autowired OutcomingRepository outcomingRepository;
   private String outcomingDateHeaderName;

   @Autowired
   public OutcomingsReportTable(MessageByLocaleService messageByLocaleService)
   {
      super(messageByLocaleService);
      outcomingDateHeaderName = messageByLocaleService
            .getMessage("table.report.outcomings.header.outcoming.date.label");
   }

   public JTable init(String from, String to)
   {
      DateLabelFormatter formatter = new DateLabelFormatter();
      List<Outcoming> outcomings = outcomingRepository.getOutcomingsFromPeriod((Date) formatter.stringToValue(from),
            (Date) formatter.stringToValue(to));
      MovementsReportTableModel model = new MovementsReportTableModel(outcomings.size(), sequentialHeaderName,
            productHeaderName, unitHeaderName, outcomingDateHeaderName, priceHeaderName, amountHeaderName,
            summationHeaderName, hiddenIdColumnHeaderName, sourceHeaderName);
      if (!outcomings.isEmpty())
      {
         for (int i = 0; i < outcomings.size(); i++)
         {
            model.addOutcoming(outcomings.get(i));
         }
      }
      setModel(model);
      setPreferredScrollableViewportSize(new Dimension(500, 70));
      setFillsViewportHeight(true);
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      // RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
      // setRowSorter(sorter);
      initColumnSizes();
      hideHiddenColumn();
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
}
