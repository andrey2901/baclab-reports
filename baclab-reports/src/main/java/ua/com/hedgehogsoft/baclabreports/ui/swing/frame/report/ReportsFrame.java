package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.PdfFileFilter;
import ua.com.hedgehogsoft.baclabreports.viewer.pdf.Viewer;

@Component
public class ReportsFrame extends Frame
{
   private static final Logger logger = Logger.getLogger(ReportsFrame.class);

   private String remainsReportButtonLabel;
   private String actReportButtonLabel;
   private String generalReportButtonLabel;
   private String incomingsReportButtonLabel;
   private String outcomingsReportButtonLabel;
   private String viewerButtonLabel;
   private String closeButtonLabel;

   private JButton remainsReportButton;
   private JButton actReportButton;
   private JButton finalReportButton;
   private JButton incomingsReportButton;
   private JButton outcomingsReportButton;
   private JButton viewerButton;
   private JButton closeButton;

   private @Autowired RemainsReportFrame remainsReportFrame;
   private @Autowired ActReportFrame actReportFrame;
   private @Autowired FinalReportFrame finalReportFrame;
   private @Autowired IncomingsReportFrame incomingsReportFrame;
   private @Autowired OutcomingsReportFrame outcomingsReportFrame;
   private @Autowired Viewer viewer;

   @Override
   protected void localize()
   {
      title = messageByLocaleService.getMessage("frame.reports.title");
      remainsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.remains.label");
      actReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.act.label");
      generalReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.general.label");
      incomingsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.incoming.label");
      outcomingsReportButtonLabel = messageByLocaleService.getMessage("frame.reports.button.outcoming.label");
      viewerButtonLabel = messageByLocaleService.getMessage("frame.reports.button.viewer.label");
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
      viewerButton = new JButton(viewerButtonLabel);
      viewerButton.addActionListener(l ->
      {
         JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new File(System.getProperty("report.folder")));
         chooser.setFileFilter(new PdfFileFilter());
         chooser.showOpenDialog(viewerButton.getParent());
         File selectedFile = chooser.getSelectedFile();
         if (selectedFile != null)
         {
            viewer.view(selectedFile);
         }
      });
      JPanel buttonsPanel = new JPanel(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      buttonsPanel.add(remainsReportButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 0;
      buttonsPanel.add(actReportButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 2;
      c.gridy = 0;
      buttonsPanel.add(finalReportButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 1;
      buttonsPanel.add(incomingsReportButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 1;
      buttonsPanel.add(outcomingsReportButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 2;
      c.gridy = 1;
      buttonsPanel.add(viewerButton, c);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 2;
      c.gridwidth = 3;
      buttonsPanel.add(closeButton, c);
      frame.add(buttonsPanel, BorderLayout.CENTER);
      frame.pack();
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }
}
