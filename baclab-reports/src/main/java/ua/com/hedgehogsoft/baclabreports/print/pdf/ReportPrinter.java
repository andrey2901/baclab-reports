package ua.com.hedgehogsoft.baclabreports.print.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.com.hedgehogsoft.baclabreports.model.SourceType;

public abstract class ReportPrinter
{
   protected Font font;
   protected String fileName;
   private static final Logger logger = Logger.getLogger(ReportPrinter.class);

   public File print(JTable table, String... args)
   {
      return print(table, false, args);
   }

   public File print(JTable table, boolean isWeb, String... args)
   {
      fileName = resolveFileName(args);
      File file = null;
      if (isWeb)
      {
         file = new File(fileName);
      }
      else
      {
         file = new File(System.getProperty("report.folder") + System.getProperty("file.separator") + fileName);
      }
      try
      {
         BaseFont bf = BaseFont.createFont(System.getProperty("local.fonts"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
         font = new Font(bf);
         font.setSize(14);
         Document document = new Document(PageSize.A4, 0, 0, 30, 30);
         PdfWriter.getInstance(document, new FileOutputStream(file));
         document.open();
         setContent(document, table, args);
         document.close();
      }
      catch (IOException | DocumentException e)
      {
         logger.error("Can't print file [" + fileName + "]", e);
      }
      return file;
   }

   protected abstract Logger getLogger();

   protected abstract String resolveFileName(String... args);

   protected abstract void setContent(Document document, JTable table, String... args) throws DocumentException;

   public int orderedPrint(PdfPTable pdfTable, Map<String, List<PdfPCell>> groupedCells, SourceType type, int counter)
   {
      for (String group : groupedCells.keySet())
      {
         if (SourceType.getType(group) == type)
         {
            for (PdfPCell printCell : groupedCells.get(group))
            {
               if (printCell == null)
               {
                  printCell = new PdfPCell(new Phrase(Integer.toString(++counter), font));
                  printCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                  printCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  pdfTable.addCell(printCell);
               }
               else if (SourceType.getType(printCell.getPhrase().getContent()) == SourceType.BUDGET)
               {
                  continue;
               }
               else
               {
                  pdfTable.addCell(printCell);
               }
            }
         }
      }
      return counter;
   }
}
