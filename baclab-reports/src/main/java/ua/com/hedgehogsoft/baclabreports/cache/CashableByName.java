package ua.com.hedgehogsoft.baclabreports.cache;

import ua.com.hedgehogsoft.baclabreports.model.ModelEntity;

public interface CashableByName<T extends ModelEntity<Long>>
{
   T findByName(String name);
}
