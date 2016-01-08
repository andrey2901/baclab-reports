package ua.com.hedgehogsoft.baclabreports.persistence.crud;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;
import ua.com.hedgehogsoft.baclabreports.persistence.json.JsonFileWriter;

public abstract class CrudRepositoryTest<T extends ModelEntity<Long>> extends DefaultTest
{
   @Autowired
   private CrudRepository<T, Long> repository;

   @Autowired
   private JsonFileWriter<T> jsonFileWriter;

   protected String fullFileName;

   @Test
   public void findAll()
   {
      List<T> entities = (List<T>) repository.findAll();
      assertNotEquals(0, entities.size());
   }

   @Ignore("Use when you need to write json file")
   @Test
   public void writeJson() throws JsonGenerationException, JsonMappingException, IOException
   {
      List<T> entities = (List<T>) repository.findAll();
      jsonFileWriter.writeToJsonFile(fullFileName, entities);
   }
}
