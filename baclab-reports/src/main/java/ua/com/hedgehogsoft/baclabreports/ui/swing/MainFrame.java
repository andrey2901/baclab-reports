package ua.com.hedgehogsoft.baclabreports.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MainFrame
{
   private static final Logger logger = Logger.getLogger(MainFrame.class);

   @PostConstruct
   public void init()
   {
      JFrame mainFrame = new JFrame("baclab-reports");
      mainFrame.setLayout(new BorderLayout());
      mainFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close();
         }
      });
      JButton exitButton = new JButton("exit");
      exitButton.addActionListener(l -> close());
      mainFrame.add(exitButton, BorderLayout.SOUTH);
      mainFrame.setSize(1000, 700);
      mainFrame.setResizable(true);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
      logger.info("baclab-reports was started.");
   }

   private void close()
   {
      logger.info("baclab-reports was finished.");
      System.exit(0);
   }
}
