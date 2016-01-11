package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;

@Component
public class ReportsFrame extends Frame
{
   private static final Logger logger = Logger.getLogger(ReportsFrame.class);

   private String remainsReportButtonLabel;
   private String actReportButtonLabel;
   private String generalReportButtonLabel;
   private String incomingsReportButtonLabel;
   private String outcomingsReportButtonLabel;
   private String closeButtonLabel;

   private JFrame reportsFrame;
   private JButton remainsReportButton;
   private JButton actReportButton;
   private JButton finalReportButton;
   private JButton incomingsReportButton;
   private JButton outcomingsReportButton;
   private JButton closeButton;

   @Override
   protected void localize()
   {
      title = messageByLocaleService.getMessage("frame.reports.title");
      remainsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.remains.label");
      actReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.act.label");
      generalReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.general.label");
      incomingsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.incoming.label");
      outcomingsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.outcoming.label");
      closeButtonLabel = messageByLocaleService.getMessage("button.close.label");
   }

   public void init()
   {
      reportsFrame = new JFrame(title);
      reportsFrame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close();
         }
      });

      closeButton = new JButton(closeButtonLabel);

      closeButton.addActionListener(l -> close());

      remainsReportButton = new JButton(remainsReportButtonLabel);

      remainsReportButton.addActionListener(null);

      actReportButton = new JButton(actReportButtonLabel);

      actReportButton.addActionListener(null);

      finalReportButton = new JButton(generalReportButtonLabel);

      finalReportButton.addActionListener(null);

      incomingsReportButton = new JButton(incomingsReportButtonLabel);

      incomingsReportButton.addActionListener(null);

      outcomingsReportButton = new JButton(outcomingsReportButtonLabel);

      outcomingsReportButton.addActionListener(null);

      JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));

      buttonsPanel.add(remainsReportButton);

      buttonsPanel.add(actReportButton);

      buttonsPanel.add(finalReportButton);

      buttonsPanel.add(incomingsReportButton);

      buttonsPanel.add(outcomingsReportButton);

      buttonsPanel.add(closeButton);

      reportsFrame.add(buttonsPanel, BorderLayout.CENTER);

      reportsFrame.pack();

      reportsFrame.setResizable(false);

      reportsFrame.setLocationRelativeTo(null);

      reportsFrame.setVisible(true);

      logger.info("ReportsFrame was started.");
   }

   private void close()
   {
      reportsFrame.dispose();
      logger.info("ReportsFrame was closed.");
   }
}
