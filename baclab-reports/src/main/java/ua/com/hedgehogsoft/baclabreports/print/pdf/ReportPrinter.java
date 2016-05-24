package ua.com.hedgehogsoft.baclabreports.print.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class ReportPrinter
{
   protected Font font;
   protected String fileName;
   private static final Logger logger = Logger.getLogger(ReportPrinter.class);

   public File print(JTable table, String... args)
   {
      fileName = resolveFileName(args);
      File file = null;
      try
      {
         BaseFont bf = BaseFont.createFont(System.getProperty("local.fonts"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
         font = new Font(bf);
         font.setSize(14);
         file = new File(System.getProperty("report.folder") + System.getProperty("file.separator") + fileName);
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
}
