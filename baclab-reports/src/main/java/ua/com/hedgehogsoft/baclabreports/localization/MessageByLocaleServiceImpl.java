package ua.com.hedgehogsoft.baclabreports.localization;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageByLocaleServiceImpl implements MessageByLocaleService
{
   private @Autowired MessageSource messageSource;
   private Locale locale;

   public MessageByLocaleServiceImpl()
   {
      String baclabLocale = System.getProperty("baclab.locale");
      if (baclabLocale == null || baclabLocale.isEmpty())
         LocaleContextHolder.setLocale(Locale.getDefault());
      else
         LocaleContextHolder.setLocale(new Locale(baclabLocale.toLowerCase(), baclabLocale.toUpperCase()));
      locale = LocaleContextHolder.getLocale();
   }

   @Override
   public String getMessage(String code)
   {
      return messageSource.getMessage(code, null, locale);
   }
}