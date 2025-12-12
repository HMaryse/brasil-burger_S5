package com.brasilburger.service;

import java.util.List;
import java.util.Optional;
import com.brasilburger.entity.Menu;

public interface MenuService {
    
    Menu addMenu(String nom, List<Integer> burgerIds, List<Integer> complementIds);
    List<Menu> findAll();
    Optional<Menu> findById(int id);
    Menu updateMenu(int id, String newName, String newEtat);
}


