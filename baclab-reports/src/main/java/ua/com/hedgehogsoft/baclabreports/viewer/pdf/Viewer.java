package ua.com.hedgehogsoft.baclabreports.viewer.pdf;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Viewer
{
   private JButton closeButton;
   private static final Logger logger = Logger.getLogger(Viewer.class);

   public void view(File file)
   {
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

      closeButton = new JButton("Закрити");
      closeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            frame.dispose();
            logger.info("Viewer was closed.");
         }
      });
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(closeButton);
      JScrollPane scrollPane = createPanelWithAllPages(file);
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("FinalReportFrame was started.");
   }

   public JScrollPane createPanelWithAllPages(File file)
   {
      JScrollPane scroll = null;
      try
      {
         PDDocument document = PDDocument.load(file);
         PDFRenderer render = new PDFRenderer(document);
         JPanel docPanel = new JPanel();
         docPanel.setLayout(new BoxLayout(docPanel, BoxLayout.Y_AXIS));
         scroll = new JScrollPane(docPanel);
         for (int i = 0; i < document.getNumberOfPages(); i++)
         {
            docPanel.add(new JLabel(new ImageIcon(render.renderImage(i))));
         }
      }
      catch (IOException e)
      {
         logger.error("Can't load file[" + file.getAbsolutePath() + "] to viewer", e);
      }

      return scroll;
   }
}
