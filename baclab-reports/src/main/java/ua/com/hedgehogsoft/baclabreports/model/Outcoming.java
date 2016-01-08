package ua.com.hedgehogsoft.baclabreports.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OUTCOMINGS")
public class Outcoming implements ModelEntity<Long>
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", unique = true, nullable = false)
   private Long id;

   @Column(name = "DATE", nullable = false)
   private Date date;

   @Column(name = "AMOUNT", nullable = false)
   private Double amount;

   @ManyToOne
   @JoinColumn(name = "PRODUCT_ID", nullable = false)
   private Product product;

   @Override
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public Double getAmount()
   {
      return amount;
   }

   public void setAmount(Double amount)
   {
      this.amount = new BigDecimal(amount).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
   }

   public Product getProduct()
   {
      return product;
   }

   public void setProduct(Product product)
   {
      this.product = product;
   }
}
