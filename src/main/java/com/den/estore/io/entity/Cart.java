package com.den.estore.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  @Column
  private long id;

  @Column
  @JsonProperty
  private BigDecimal total;

  @OneToOne(mappedBy = "cart")
  @JsonProperty
  private Users user;

  @ManyToMany
  @JsonProperty
  @Column
  private List<Item> items;


  // Add Item
  public void addItem(Item item) {
    if(items == null) {
      items = new ArrayList<>();
    }

    items.add(item);

    if(total == null) {
      total = new BigDecimal(0);
    }

    total = total.add(item.getPrice());
  }

  // Remove Item
  public void removeItem(Item item) {

    if(items == null) {
      items = new ArrayList<>();
    }

    items.remove(item);

    if(total == null) {
      total = new BigDecimal(0);
    }

    total = total.subtract(item.getPrice());
  }

}
