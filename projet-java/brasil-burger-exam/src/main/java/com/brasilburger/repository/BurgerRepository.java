package com.brasilburger.repository;

import com.brasilburger.entity.Burger;

import java.util.List;
import java.util.Optional;

public interface BurgerRepository {
    Burger save(Burger burger);
    Burger update(Burger burger);
    List<Burger> findAll();
    Optional<Burger> findById(int id);
}
