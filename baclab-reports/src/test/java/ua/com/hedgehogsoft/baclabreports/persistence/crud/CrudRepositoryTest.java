package ua.com.hedgehogsoft.baclabreports.persistence.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ua.com.hedgehogsoft.baclabreports.DefaultTest;
import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;
import ua.com.hedgehogsoft.baclabreports.persistence.json.JsonFileWriter;

public abstract class CrudRepositoryTest<T extends ModelEntity<Long>> extends DefaultTest
{
   @Autowired
   protected CrudRepository<T, Long> repository;

   @Autowired
   protected JsonFileWriter<T> jsonFileWriter;
}
