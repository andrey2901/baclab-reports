package ua.com.hedgehogsoft.baclabreports.persistence.console;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class <code>WebConfiguration</code> is useful class for debug in test
 * mode.You can set a breakpoint in the application and then use H2 console for
 * working with embedded db (just type the url in your browser like:
 * http://localhost:8080/console/). Use port number from your application.
 */
@Configuration
public class H2WebConsoleConfiguration
{
   @Bean
   ServletRegistrationBean h2servletRegistration()
   {
      ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());

      registrationBean.addUrlMappings("/console/*");

      return registrationBean;
   }
}
