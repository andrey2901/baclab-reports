package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;

public interface OutcomingRepository extends JpaRepository<Outcoming, Long>
{
   @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Outcoming o WHERE o.product.id = ?1 AND o.date >= ?2 AND o.date < ?3")
   double getOutcomingsSum(long productId, Date destinationDate, Date today);

   @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Outcoming o WHERE o.product.id = ?1 AND o.date BETWEEN ?2 AND ?3")
   double getOutcomingsSumFromPeriod(long productId, Date from, Date to);

   @Query("SELECT o FROM Outcoming o WHERE o.date BETWEEN ?1 AND ?2")
   List<Outcoming> getOutcomingsFromPeriod(Date stringToValue, Date stringToValue2);

   @Query("SELECT o FROM Outcoming o WHERE o.id = ?1")
   Outcoming getById(long outcomingId);
}
