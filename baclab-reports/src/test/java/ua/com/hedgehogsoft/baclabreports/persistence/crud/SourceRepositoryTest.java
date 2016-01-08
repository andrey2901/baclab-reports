package ua.com.hedgehogsoft.baclabreports.persistence.crud;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ua.com.hedgehogsoft.baclabreports.model.Source;

public class SourceRepositoryTest extends CrudRepositoryTest<Source>
{
   @Test
   public void findAll()
   {
      List<Source> sources = (List<Source>) repository.findAll();
      assertNotEquals(0, sources.size());
   }

   @Test
   public void writeJson() throws JsonGenerationException, JsonMappingException, IOException
   {
      List<Source> sources = (List<Source>) repository.findAll();
      jsonFileWriter.writeToJsonFile("C:\\sources.json", sources);
   }
}
