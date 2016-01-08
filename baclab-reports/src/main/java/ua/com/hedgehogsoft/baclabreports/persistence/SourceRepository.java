package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.hedgehogsoft.baclabreports.model.Source;

public interface SourceRepository extends PagingAndSortingRepository<Source, Long>
{

}
