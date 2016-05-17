package ua.com.hedgehogsoft.baclabreports;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Launcher.class)
@WebAppConfiguration
@IntegrationTest
@TestPropertySource(locations = "classpath:test_application.properties")
public abstract class DefaultTest
{
   public DefaultTest()
   {
      System.setProperty("java.awt.headless", "false");
   }
}
