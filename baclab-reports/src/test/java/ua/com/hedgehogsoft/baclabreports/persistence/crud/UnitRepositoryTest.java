package ua.com.hedgehogsoft.baclabreports.persistence.crud;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ua.com.hedgehogsoft.baclabreports.model.Unit;

public class UnitRepositoryTest extends CrudRepositoryTest<Unit>
{
   @Test
   public void findAll()
   {
      List<Unit> units = (List<Unit>) repository.findAll();
      assertNotEquals(0, units.size());
   }

   @Ignore("Use when you need to write json file")
   @Test
   public void writeJson() throws JsonGenerationException, JsonMappingException, IOException
   {
      List<Unit> units = (List<Unit>) repository.findAll();
      jsonFileWriter.writeToJsonFile("units.json", units);
   }
}
