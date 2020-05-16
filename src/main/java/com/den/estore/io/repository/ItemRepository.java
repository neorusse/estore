package com.den.estore.io.repository;

import com.den.estore.io.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

  public List<Item> findByName(String name);
}
