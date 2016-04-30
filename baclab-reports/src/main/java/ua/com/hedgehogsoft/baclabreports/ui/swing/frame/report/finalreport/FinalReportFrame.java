package ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.finalreport;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.ui.swing.frame.report.ReportFrame;

@Component
public class FinalReportFrame extends ReportFrame
{
   private static final Logger logger = Logger.getLogger(FinalReportFrame.class);

   @Override
   protected void localize()
   {
      super.localize();
   }

   public void init()
   {

   }

   @Override
   protected Logger getLogger()
   {
      return logger;
   }

}
