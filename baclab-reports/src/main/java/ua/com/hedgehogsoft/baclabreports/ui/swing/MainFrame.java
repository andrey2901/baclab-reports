package ua.com.hedgehogsoft.baclabreports.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;
import ua.com.hedgehogsoft.baclabreports.ui.swing.table.ProductStorageTable;

@Component
public class MainFrame
{
   private static final Logger logger = Logger.getLogger(MainFrame.class);
   private String title;
   private String incomingButtonLabel;
   private String outcomingButtonLabel;
   private String reportsButtonLabel;
   private String exitButtonLabel;
   private @Autowired ProductStorageTable table;
   private @Autowired IncomingFrame incomingsFrame;
   private @Autowired OutcomingFrame outcomingsFrame;

   @Autowired
   public MainFrame(MessageByLocaleService messageByLocaleService)
   {
      title = messageByLocaleService.getMessage("mainframe.title");
      incomingButtonLabel = messageByLocaleService.getMessage("button.incoming.label");
      outcomingButtonLabel = messageByLocaleService.getMessage("button.outcoming.label");
      reportsButtonLabel = messageByLocaleService.getMessage("button.reports.label");
      exitButtonLabel = messageByLocaleService.getMessage("button.exit.label");
   }

   @PostConstruct
   public void init()
   {
      JFrame mainFrame = new JFrame(title);
      mainFrame.setLayout(new BorderLayout());
      mainFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close();
         }
      });
      JButton incomingButton = new JButton(incomingButtonLabel);
      incomingButton.addActionListener(l -> incomingsFrame.init());
      JButton outcomingButton = new JButton(outcomingButtonLabel);
      outcomingButton.addActionListener(l -> outcomingsFrame.init());
      JButton reportsButton = new JButton(reportsButtonLabel);
      reportsButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            // new ReportsFrame();
         }
      });
      JButton exitButton = new JButton(exitButtonLabel);
      exitButton.addActionListener(l -> close());
      JPanel comingButtonPanel = new JPanel();
      comingButtonPanel.add(incomingButton, BorderLayout.WEST);
      comingButtonPanel.add(outcomingButton, BorderLayout.EAST);
      JPanel functionalButtonPanel = new JPanel();
      functionalButtonPanel.add(reportsButton, BorderLayout.WEST);
      functionalButtonPanel.add(exitButton, BorderLayout.EAST);
      JPanel buttonsPanel = new JPanel(new BorderLayout());
      buttonsPanel.add(comingButtonPanel, BorderLayout.NORTH);
      buttonsPanel.add(functionalButtonPanel, BorderLayout.SOUTH);
      JScrollPane scrollPane = new JScrollPane(table);
      mainFrame.add(scrollPane, BorderLayout.CENTER);
      mainFrame.add(buttonsPanel, BorderLayout.SOUTH);
      mainFrame.setSize(1000, 700);
      mainFrame.setResizable(true);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
      logger.info("baclab-reports was started");
   }

   private void close()
   {
      logger.info("baclab-reports was finished");
      System.exit(0);
   }
}
