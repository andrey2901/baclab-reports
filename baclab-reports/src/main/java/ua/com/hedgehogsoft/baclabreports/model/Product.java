package ua.com.hedgehogsoft.baclabreports.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ua.com.hedgehogsoft.baclabreports.cache.CacheableByName;

@Entity
@Table(name = "PRODUCTS", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME"),
                                               @UniqueConstraint(columnNames = "PRICE"),
                                               @UniqueConstraint(columnNames = "SOURCE_ID"),
                                               @UniqueConstraint(columnNames = "UNIT_ID")})
public class Product implements ModelEntity<Long>, CacheableByName
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", unique = true, nullable = false)
   private Long id;

   @Column(name = "NAME", nullable = false)
   private String name;

   @Column(name = "AMOUNT", nullable = false)
   private Double amount;

   @Column(name = "PRICE", nullable = false)
   private Double price;

   @ManyToOne
   @JoinColumn(name = "SOURCE_ID", nullable = false)
   private Source source;

   @ManyToOne
   @JoinColumn(name = "UNIT_ID", nullable = false)
   private Unit unit;

   @Override
   public boolean equals(Object obj)
   {
      if (!(obj instanceof Product))
      {
         return false;
      }

      if (obj == this)
      {
         return true;
      }

      Product product = (Product) obj;

      if (product.getName() != null)
      {
         if (product.getName().equals(name) && Double.toString(price).equals(Double.toString(product.getPrice()))
               && source.getId() == product.getSource().getId() && source.getName() == product.getSource().getName()
               && unit.getId() == product.getUnit().getId() && unit.getName() == product.getUnit().getName())
         {
            return true;
         }
      }
      return false;
   }

   @JsonIgnore
   public Double getTotalPrice()
   {
      return new BigDecimal(price * amount).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
   }

   @Override
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @Override
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Double getAmount()
   {
      return amount;
   }

   public void setAmount(Double amount)
   {
      this.amount = new BigDecimal(amount).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
   }

   public Double getPrice()
   {
      return price;
   }

   public void setPrice(Double price)
   {
      this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
   }

   public Source getSource()
   {
      return source;
   }

   public void setSource(Source source)
   {
      this.source = source;
   }

   public Unit getUnit()
   {
      return unit;
   }

   public void setUnit(Unit unit)
   {
      this.unit = unit;
   }

   @Override
   public String toString()
   {
      return "Product [id=" + id + ", name=" + name + ", amount=" + amount + ", price=" + price + ", source="
            + source.getName() + ", unit=" + unit.getName() + "]";
   }
}
