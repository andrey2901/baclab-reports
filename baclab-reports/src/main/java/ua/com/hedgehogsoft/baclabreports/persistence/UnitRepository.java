package ua.com.hedgehogsoft.baclabreports.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.hedgehogsoft.baclabreports.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long>
{

}
