package com.brasilburger.repository;

import com.brasilburger.entity.Menu;
import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    Menu save(Menu menu);
    Menu update(Menu menu);
    List<Menu> findAll();
    Optional<Menu> findById(int id);
}

