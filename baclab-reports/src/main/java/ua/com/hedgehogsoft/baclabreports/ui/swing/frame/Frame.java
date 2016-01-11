package ua.com.hedgehogsoft.baclabreports.ui.swing.frame;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

public abstract class Frame
{
   protected String title;
   protected JFrame frame;
   protected @Autowired MessageByLocaleService messageByLocaleService;

   public void close()
   {
      frame.dispose();
      getLogger().info("close()");
   }

   @PostConstruct
   protected abstract void localize();

   protected abstract Logger getLogger();
}
