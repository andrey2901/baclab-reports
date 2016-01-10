package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.com.hedgehogsoft.baclabreports.model.Source;

public interface SourceRepository extends JpaRepository<Source, Long>
{
   @Transactional(readOnly = true)
   @Query("SELECT s FROM Source s WHERE s.name = ?1")
   Source findByName(String sourceName);
}
