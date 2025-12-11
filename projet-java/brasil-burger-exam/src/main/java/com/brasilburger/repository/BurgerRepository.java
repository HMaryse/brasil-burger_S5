package com.brasilburger.repository;

import com.brasilburger.entity.Burger;

import java.util.List;

public interface BurgerRepository {
    Burger save(Burger burger);
    Burger update(Burger burger);
    List<Burger> findAll();
}
