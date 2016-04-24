package ua.com.hedgehogsoft.baclabreports.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;

public abstract class Cache<T extends ModelEntity<Long>>
{
   protected List<T> cache = new ArrayList<>();
   
   @PostConstruct
   protected abstract void init();

   public boolean add(T entity)
   {
      return cache.add(entity);
   }

   public boolean addAll(List<T> entities)
   {
      return cache.addAll(entities);
   }

   public T get(int index)
   {
      return cache.get(index);
   }

   public List<T> getAll()
   {
      List<T> entities = new ArrayList<>();
      Iterator<T> it = cache.iterator();
      while (it.hasNext())
      {
         entities.add(it.next());
      }
      return entities;
   }

   public boolean contains(T entity)
   {
      return cache.contains(entity);
   }

   public int size()
   {
      return cache.size();
   }

   public void clear()
   {
      cache.clear();
   }
}
