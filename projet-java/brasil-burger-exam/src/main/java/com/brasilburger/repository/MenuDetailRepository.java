package com.brasilburger.repository;

import java.util.List;

public interface MenuDetailRepository {

    void saveMenuBurgers(int menuId, List<Integer> burgerIds);
    void saveMenuComplements(int menuId, List<Integer> complementIds);
    void deleteByMenuId(int menuId);

}

