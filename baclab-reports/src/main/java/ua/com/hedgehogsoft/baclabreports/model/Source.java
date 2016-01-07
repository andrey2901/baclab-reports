package ua.com.hedgehogsoft.baclabreports.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SOURCES", uniqueConstraints = {@UniqueConstraint(columnNames = "SOURCE")})
public class Source implements ModelEntity<Long>
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", unique = true, nullable = false)
   private Long id;

   @Column(name = "SOURCE", length = 100, unique = true, nullable = false)
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
      return "Source [id=" + id + ", name=" + name + "]";
   }
}
