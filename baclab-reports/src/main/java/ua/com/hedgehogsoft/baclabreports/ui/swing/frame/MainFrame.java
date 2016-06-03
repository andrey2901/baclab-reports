package ua.com.hedgehogsoft.baclabreports.ui.swing.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.IncomingFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.movement.OutcomingFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportsFrame;
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
   private @Autowired IncomingFrame incomingFrame;
   private @Autowired OutcomingFrame outcomingFrame;
   private @Autowired ReportsFrame reportsFrame;
   private int width = 150;
   private int height = 25;

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
      incomingButton.setPreferredSize(new Dimension(width, height));
      incomingButton.addActionListener(l -> incomingFrame.init());
      JButton outcomingButton = new JButton(outcomingButtonLabel);
      outcomingButton.setPreferredSize(new Dimension(width, height));
      outcomingButton.addActionListener(l -> outcomingFrame.init());
      JButton reportsButton = new JButton(reportsButtonLabel);
      reportsButton.setPreferredSize(new Dimension(width, height));
      reportsButton.addActionListener(l -> reportsFrame.init());
      JButton exitButton = new JButton(exitButtonLabel);
      exitButton.setPreferredSize(new Dimension(width, height));
      exitButton.addActionListener(l -> close());
      JPanel comingButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      comingButtonPanel.add(incomingButton);
      comingButtonPanel.add(outcomingButton);
      JPanel functionalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      functionalButtonPanel.add(reportsButton);
      functionalButtonPanel.add(exitButton);
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
