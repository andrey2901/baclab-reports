package ua.com.hedgehogsoft.baclabreports.persistence;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.hedgehogsoft.baclabreports.model.Incoming;

public interface IncomingRepository extends JpaRepository<Incoming, Long>
{
   @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Incoming i WHERE i.product.id = ?1 AND i.date >= ?2 AND i.date < ?3")
   double getIncomingsSum(long productId, Date destinationDate, Date today);

   @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Incoming i WHERE i.product.id = ?1 AND i.date BETWEEN ?2 AND ?3")
   double getIncomingsSumFromPeriod(long productId, Date destinationDate, Date today);
}
