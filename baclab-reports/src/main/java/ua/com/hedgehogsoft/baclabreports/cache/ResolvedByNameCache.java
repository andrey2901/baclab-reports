package ua.com.hedgehogsoft.baclabreports.cache;

public abstract class ResolvedByNameCache<T extends CacheableByName> extends Cache<T> implements CacheByNameResolver<T>
{
   public T findByName(String name)
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
