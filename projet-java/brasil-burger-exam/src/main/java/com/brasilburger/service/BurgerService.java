package com.brasilburger.service;

import java.util.List;
import java.util.Optional;

import com.brasilburger.entity.Burger;

public interface BurgerService {
    Burger addBurger(String nom, double prix);
    Burger updateBurger(int id, String newName, double newPrix, String newEtat);
    Optional<Burger> findById(int id);
    List<Burger> findAll();
}

