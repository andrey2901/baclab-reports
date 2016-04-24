package ua.com.hedgehogsoft.baclabreports.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Source;
import ua.com.hedgehogsoft.baclabreports.persistence.SourceRepository;

@Component
public class SourceCache extends ResolvedByNameCache<Source>
{
   protected @Autowired SourceRepository sourceRepository;

   @Override
   protected void init()
   {
      addAll(sourceRepository.findAll());
   }
}
