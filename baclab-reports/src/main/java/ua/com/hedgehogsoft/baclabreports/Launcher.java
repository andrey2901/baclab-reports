package ua.com.hedgehogsoft.baclabreports;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Launcher
{
   public static void main(String[] args)
   {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(Launcher.class);
      builder.headless(false);
      builder.run(args);
   }
}
