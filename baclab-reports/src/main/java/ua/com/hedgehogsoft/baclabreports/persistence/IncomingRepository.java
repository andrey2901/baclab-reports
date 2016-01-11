package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;

public interface IncomingRepository extends JpaRepository<Incoming, Long>
{
   @Query("SELECT SUM(i.amount) FROM Incoming i WHERE i.product.id = ?1 AND i.date >= ?2")
   double getIncomingsSumFromDate(long productId, Date date);

   @Query("SELECT SUM(i.amount) FROM Incoming i WHERE i.product.id = ?1 AND i.date > ?2")
   double getIncomingsSumOnDate(long productId, Date date);
}
