package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.hedgehogsoft.baclabreports.model.Source;

public interface SourceRepository extends JpaRepository<Source, Long>
{

}
