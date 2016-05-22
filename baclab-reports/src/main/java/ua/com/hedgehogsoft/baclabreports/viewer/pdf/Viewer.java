package ua.com.hedgehogsoft.baclabreports.viewer.pdf;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Viewer
{
   private JButton printButton;
   private JButton closeButton;
   private JButton openButton;
   private JButton zoomInButton;
   private JButton zoomOutButton;
   private List<JLabel> pages;
   private float scaleIndex = 1.5f;
   private float zoomIndex = 0.2f;
   private int bottomMargin = 20;
   private File pdf;
   private static final Logger logger = Logger.getLogger(Viewer.class);

   public void view(File file)
   {
      pdf = file;
      JFrame frame = new JFrame("Viewer");
      frame.setLayout(new BorderLayout());
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            frame.dispose();
            logger.info("Viewer was closed.");
         }
      });

      printButton = new JButton("Друкувати");
      printButton.addActionListener(null);
      closeButton = new JButton("Закрити");
      closeButton.addActionListener(l ->
      {
         frame.dispose();
         logger.info("Viewer was closed.");

      });
      openButton = new JButton("Вiдкрити ...");
      openButton.addActionListener(l ->
      {
         JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new File(System.getProperty("report.folder")));
         chooser.setFileFilter(new FileFilter()
         {
            @Override
            public String getDescription()
            {
               return "PDF files";
            }

            @Override
            public boolean accept(File f)
            {
               if (f.isDirectory())
               {
                  return true;
               }
               String extension = getExtension(f);
               if (extension != null)
               {
                  switch (extension)
                  {
                     case "pdf":
                        return true;
                     default:
                        return false;
                  }
               }
               return false;
            }

            private String getExtension(File f)
            {
               String ext = null;
               String s = f.getName();
               int i = s.lastIndexOf('.');

               if (i > 0 && i < s.length() - 1)
               {
                  ext = s.substring(i + 1).toLowerCase();
               }
               return ext;
            }

         });
         chooser.showOpenDialog(openButton.getParent());
         File selectedFile = chooser.getSelectedFile();
         if (selectedFile != null)
         {
            new Viewer().view(selectedFile);
         }
      });
      zoomInButton = new JButton("Збiльшити");
      zoomInButton.addActionListener(l ->
      {
         try (PDDocument document = PDDocument.load(pdf))
         {
            PDFRenderer render = new PDFRenderer(document);
            int numberOfPages = document.getNumberOfPages();
            scaleIndex += zoomIndex;
            for (int i = 0; i < numberOfPages; i++)
            {
               JLabel page = pages.get(i);
               page.setIcon(new ImageIcon(render.renderImage(i, scaleIndex)));
            }
         }
         catch (IOException e)
         {
            logger.error("Can't load file[" + pdf.getAbsolutePath() + "] to viewer", e);
         }
      });
      zoomOutButton = new JButton("Зменшити");
      zoomOutButton.addActionListener(l ->
      {
         try (PDDocument document = PDDocument.load(pdf))
         {
            PDFRenderer render = new PDFRenderer(document);
            int numberOfPages = document.getNumberOfPages();
            scaleIndex -= zoomIndex;
            for (int i = 0; i < numberOfPages; i++)
            {
               JLabel page = pages.get(i);
               page.setIcon(new ImageIcon(render.renderImage(i, scaleIndex)));
            }
         }
         catch (IOException e)
         {
            logger.error("Can't load file[" + pdf.getAbsolutePath() + "] to viewer", e);
         }
      });
      JPanel zoomPanel = new JPanel();
      zoomPanel.add(openButton);
      zoomPanel.add(zoomInButton);
      zoomPanel.add(zoomOutButton);
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(printButton);
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = createPanelWithAllPages();
      frame.add(zoomPanel, BorderLayout.NORTH);
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("FinalReportFrame was started.");
   }

   public JScrollPane createPanelWithAllPages()
   {
      JScrollPane scroll = null;
      try (PDDocument document = PDDocument.load(pdf))
      {
         PDFRenderer render = new PDFRenderer(document);
         int numberOfPages = document.getNumberOfPages();
         JPanel docPanel = new JPanel();
         docPanel.setLayout(new GridLayout(numberOfPages, 1));
         scroll = new JScrollPane(docPanel);
         pages = new ArrayList<>();
         for (int i = 0; i < numberOfPages; i++)
         {
            ImageIcon image = new ImageIcon(render.renderImage(i, scaleIndex));
            JLabel page = new JLabel(image, JLabel.CENTER);
            page.setBorder(BorderFactory.createEmptyBorder(0, 0, bottomMargin, 0));
            pages.add(page);
            docPanel.add(page);
         }
      }
      catch (IOException e)
      {
         logger.error("Can't load file[" + pdf.getAbsolutePath() + "] to viewer", e);
      }

      return scroll;
   }
}
