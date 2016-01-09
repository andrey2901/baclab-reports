package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;

public interface IncomingRepository extends JpaRepository<Incoming, Long>
{

}
