package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.Frame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.actreport.ActReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.finalreport.FinalReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.movement.IncomingsReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.movement.OutcomingsReportFrame;
import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.remains.RemainsReportFrame;

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

   private JButton remainsReportButton;
   private JButton actReportButton;
   private JButton finalReportButton;
   private JButton incomingsReportButton;
   private JButton outcomingsReportButton;
   private JButton closeButton;

   private @Autowired RemainsReportFrame remainsReportFrame;
   private @Autowired ActReportFrame actReportFrame;
   private @Autowired FinalReportFrame finalReportFrame;
   private @Autowired IncomingsReportFrame incomingsReportFrame;
   private @Autowired OutcomingsReportFrame outcomingsReportFrame;

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
      frame = new JFrame(title);
      frame.addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent we)
         {
            close();
         }
      });
      closeButton = new JButton(closeButtonLabel);
      closeButton.addActionListener(l -> close());
      remainsReportButton = new JButton(remainsReportButtonLabel);
      remainsReportButton.addActionListener(l -> remainsReportFrame.init());
      actReportButton = new JButton(actReportButtonLabel);
      actReportButton.addActionListener(l -> actReportFrame.init());
      finalReportButton = new JButton(generalReportButtonLabel);
      finalReportButton.addActionListener(l -> finalReportFrame.init());
      incomingsReportButton = new JButton(incomingsReportButtonLabel);
      incomingsReportButton.addActionListener(l -> incomingsReportFrame.init());
      outcomingsReportButton = new JButton(outcomingsReportButtonLabel);
      outcomingsReportButton.addActionListener(l -> outcomingsReportFrame.init());
      JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));
      buttonsPanel.add(remainsReportButton);
      buttonsPanel.add(actReportButton);
      buttonsPanel.add(finalReportButton);
      buttonsPanel.add(incomingsReportButton);
      buttonsPanel.add(outcomingsReportButton);
      buttonsPanel.add(closeButton);
      frame.add(buttonsPanel, BorderLayout.CENTER);
      frame.pack();
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      logger.info("ReportsFrame was started.");
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }
}
