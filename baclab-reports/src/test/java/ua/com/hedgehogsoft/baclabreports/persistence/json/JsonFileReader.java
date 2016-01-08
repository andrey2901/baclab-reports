package ua.com.hedgehogsoft.baclabreports.persistence.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;

@Component
public class JsonFileReader<T extends ModelEntity<Long>>
{
   @Autowired
   private ObjectMapper jacksonObjectMapper;

   @Autowired
   private ResourceLoader resourceLoader;

   public T readFromJsonFile(String pathToFile, Class<T> clazz)
         throws JsonParseException, JsonMappingException, IOException
   {
      InputStream in = new FileInputStream(pathToFile);
      TypeReference<T> tr = new TypeReference<T>() {};
      return jacksonObjectMapper.readValue(in, tr);
   }

   public T readJsonFromClasspath(String pathToFile, Class<T> clazz)
         throws JsonParseException, JsonMappingException, IOException
   {
      Resource resource = resourceLoader.getResource("classpath:" + pathToFile);
      InputStream in = resource.getInputStream();
      TypeReference<T> tr = new TypeReference<T>() {};
      return jacksonObjectMapper.readValue(in, tr);
   }
}
