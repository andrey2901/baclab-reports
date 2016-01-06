package ua.com.hedgehogsoft.baclabreports;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

public class AppTest extends DefaultTest
{
   private RestTemplate restTemplate = new TestRestTemplate();
   private String url = "http://localhost:%s/greeting";
   @Value("${server.port}") private String port;

   @Test
   public void greeting()
   {
      String formattedURL = String.format(url, port);
      String greetingResponse = restTemplate.getForObject(formattedURL, String.class);
      assertEquals("Greetings from Spring Boot!", greetingResponse);
   }
}
