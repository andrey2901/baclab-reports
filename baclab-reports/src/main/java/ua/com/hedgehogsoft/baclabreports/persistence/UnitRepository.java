package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.com.hedgehogsoft.baclabreports.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long>
{
   @Transactional(readOnly = true)
   @Query("SELECT u FROM Unit u WHERE u.name = ?1")
   Unit findByName(String unitName);
}
