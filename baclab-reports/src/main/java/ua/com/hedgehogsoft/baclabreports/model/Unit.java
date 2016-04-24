package ua.com.hedgehogsoft.baclabreports.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ua.com.hedgehogsoft.baclabreports.cache.CacheableByName;

@Entity
@Table(name = "UNITS", uniqueConstraints = {@UniqueConstraint(columnNames = "UNIT")})
public class Unit implements ModelEntity<Long>, CacheableByName
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", unique = true, nullable = false)
   private Long id;

   @Column(name = "UNIT", length = 100, unique = true, nullable = false)
   private String name;

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

   @Override
   public String toString()
   {
      return "Unit [id=" + id + ", name=" + name + "]";
   }
}
