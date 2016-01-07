package ua.com.hedgehogsoft.baclabreports.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

@RestController
public class HelloController
{
   private @Autowired MessageByLocaleService messageByLocaleService;

   @RequestMapping("/greeting")
   public String hello()
   {
      return messageByLocaleService.getMessage("greeting");
   }
}
