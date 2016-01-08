package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;

public interface OutcomingRepository extends PagingAndSortingRepository<Outcoming, Long>
{

}
