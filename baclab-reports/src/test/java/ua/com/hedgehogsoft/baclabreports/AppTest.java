package ua.com.hedgehogsoft.baclabreports;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import ua.com.hedgehogsoft.baclabreports.localization.MessageByLocaleService;

public class AppTest extends DefaultTest
{
   private RestTemplate restTemplate = new TestRestTemplate();
   private String url = "http://localhost:%s/greeting";
   private @Value("${server.port}") String port;
   private @Autowired MessageByLocaleService messageByLocaleService;

   @Test
   public void greeting()
   {
      String formattedURL = String.format(url, port);
      String greetingResponse = restTemplate.getForObject(formattedURL, String.class);
      assertEquals(messageByLocaleService.getMessage("greeting"), greetingResponse);
   }
}
