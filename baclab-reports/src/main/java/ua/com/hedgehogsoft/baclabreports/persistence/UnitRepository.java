package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.hedgehogsoft.baclabreports.model.Unit;

public interface UnitRepository extends PagingAndSortingRepository<Unit, Long>
{

}
