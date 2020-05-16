package com.den.estore.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user_order")
public class UserOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  @Column
  private Long id;

  @ManyToMany(cascade = CascadeType.ALL)
  @JsonProperty
  @Column
  private List<Item> items;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
  @JsonProperty
  private Users user;

  @JsonProperty
  @Column
  private BigDecimal total;

  public static UserOrder createFromCart(Cart cart) {

    UserOrder order = new UserOrder();

    order.setItems(new ArrayList<>(cart.getItems()));
    order.setTotal(cart.getTotal());
    order.setUser(cart.getUser());

    return order;

  }

}
