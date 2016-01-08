package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;

public interface IncomingRepository extends PagingAndSortingRepository<Incoming, Long>
{

}
