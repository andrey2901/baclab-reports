package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;

public interface OutcomingRepository extends JpaRepository<Outcoming, Long>
{

}
