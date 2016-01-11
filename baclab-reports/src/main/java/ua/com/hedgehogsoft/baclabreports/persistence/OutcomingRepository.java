package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Outcoming;

public interface OutcomingRepository extends JpaRepository<Outcoming, Long>
{
   @Query("SELECT SUM(o.amount) FROM Outcoming o WHERE o.product.id = ?1 AND o.date >= ?2")
   double getOutcomingsSumFromDate(long productId, Date date);

   @Query("SELECT SUM(o.amount) FROM Outcoming o WHERE o.product.id = ?1 AND o.date = ?2")
   double getOutcomingsSumOnDate(long productId, Date date);
}
