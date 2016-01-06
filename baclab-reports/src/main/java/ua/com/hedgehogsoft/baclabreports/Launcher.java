package ua.com.hedgehogsoft.baclabreports;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher
{
   private static final Logger logger = Logger.getLogger(Launcher.class);

   public static void main(String[] args)
   {
      SpringApplication.run(Launcher.class, args);
      logger.info("Hello World!");
   }
}
