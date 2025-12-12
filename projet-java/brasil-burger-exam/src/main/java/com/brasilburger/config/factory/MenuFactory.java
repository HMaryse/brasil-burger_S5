package com.brasilburger.config.factory;

import com.brasilburger.repository.impl.MenuRepositoryImpl;
import com.brasilburger.repository.impl.MenuDetailRepositoryImpl;
import com.brasilburger.service.impl.MenuServiceImpl;
import com.brasilburger.service.MenuService;

public class MenuFactory {

    public static MenuService createService() {
        return new MenuServiceImpl(
                new MenuRepositoryImpl(),
                new MenuDetailRepositoryImpl(),
                BurgerFactory.createService(),
                ComplementFactory.createService()
        );
    }
}



