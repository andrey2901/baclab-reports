package ua.com.hedgehogsoft.baclabreports.cache;

public interface CacheByNameResolver<T extends CacheableByName>
{
   T findByName(String name);
}
