package ua.com.hedgehogsoft.baclabreports.persistence.json;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;

@Component
public class JsonFileWriter<T extends ModelEntity<Long>>
{
   @Autowired
   private ObjectMapper jacksonObjectMapper;

   public void writeToJsonFile(String pathToFile, T entity)
         throws JsonGenerationException, JsonMappingException, IOException
   {
      OutputStream out = new FileOutputStream(pathToFile);
      jacksonObjectMapper.writeValue(out, entity);
   }

   public void writeToJsonFile(String pathToFile, List<T> entities)
         throws JsonGenerationException, JsonMappingException, IOException
   {
      OutputStream out = new FileOutputStream(pathToFile);
      jacksonObjectMapper.writeValue(out, entities);
   }
}
