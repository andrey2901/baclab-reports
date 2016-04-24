package ua.com.hedgehogsoft.baclabreports.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.hedgehogsoft.baclabreports.model.Unit;
import ua.com.hedgehogsoft.baclabreports.persistence.UnitRepository;

@Component
public class UnitCache extends Cache<Unit> implements CashableByName<Unit>
{
   protected @Autowired UnitRepository unitRepository;

   @Override
   protected void init()
   {
      addAll(unitRepository.findAll());
   }

   @Override
   public Unit findByName(String name)
   {
      for (int i = 0; i < size(); i++)
      {
         if (get(i).getName().equals(name))
         {
            return get(i);
         }
      }
      return null;
   }
}
