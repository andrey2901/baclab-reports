package ua.com.hedgehogsoft.baclabreports.printer;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public final class Printer
{
   private static final Logger logger = Logger.getLogger(Printer.class);

   public void print(File file)
   {
      try (PDDocument document = PDDocument.load(file))
      {
         printWithDialog(document);
      }
      catch (IOException | PrinterException e)
      {
         logger.error("Can't print a file [" + file.getName() + "] on printer", e);
      }
   }

   /**
    * Prints the document at its actual size. This is the recommended way to
    * print.
    */
   public void print(PDDocument document) throws IOException, PrinterException
   {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPageable(new PDFPageable(document));
      job.print();
   }

   /**
    * Prints using custom PrintRequestAttribute values.
    */
   public void printWithAttributes(PDDocument document) throws IOException, PrinterException
   {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPageable(new PDFPageable(document));

      PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
      attr.add(new PageRanges(1, 1)); // pages 1 to 1

      job.print(attr);
   }

   /**
    * Prints with a print preview dialog.
    */
   public void printWithDialog(PDDocument document) throws IOException, PrinterException
   {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPageable(new PDFPageable(document));

      if (job.printDialog())
      {
         job.print();
      }
   }

   /**
    * Prints with a print preview dialog and custom PrintRequestAttribute
    * values.
    */
   public void printWithDialogAndAttributes(PDDocument document) throws IOException, PrinterException
   {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPageable(new PDFPageable(document));

      PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
      attr.add(new PageRanges(1, 1)); // pages 1 to 1

      if (job.printDialog(attr))
      {
         job.print(attr);
      }
   }

   /**
    * Prints using a custom page size and custom margins.
    */
   public void printWithPaper(PDDocument document) throws IOException, PrinterException
   {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPageable(new PDFPageable(document));

      // define custom paper
      Paper paper = new Paper();
      paper.setSize(306, 396); // 1/72 inch
      paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // no
                                                                         // margins

      // custom page format
      PageFormat pageFormat = new PageFormat();
      pageFormat.setPaper(paper);

      // override the page format
      Book book = new Book();
      // append all pages
      book.append(new PDFPrintable(document), pageFormat, document.getNumberOfPages());
      job.setPageable(book);

      job.print();
   }
}