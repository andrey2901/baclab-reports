package ua.com.hedgehogsoft.baclabreports;

import java.util.Locale;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class Launcher
{
   public static void main(String[] args)
   {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(Launcher.class);
      builder.headless(false);
      builder.run(args);
   }

   @Bean
   public LocaleResolver localeResolver()
   {
      SessionLocaleResolver slr = new SessionLocaleResolver();
      slr.setDefaultLocale(Locale.US);
      return slr;
   }

   @Bean
   public ReloadableResourceBundleMessageSource messageSource()
   {
      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setBasename("classpath:lang");
      messageSource.setCacheSeconds(3600);
      return messageSource;
   }
}
