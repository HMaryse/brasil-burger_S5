package com.brasilburger.config.factory;

import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.repository.impl.BurgerRepositoryImpl;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.impl.BurgerServiceImpl;
import com.cloudinary.Cloudinary;

public class BurgerFactory {

    
    public static BurgerRepository createRepository() {
        return new BurgerRepositoryImpl();
    }

    
    public static BurgerService createService() {
        Cloudinary cloudinary = AppFactory.getCloudinary(); 
        return new BurgerServiceImpl(createRepository(), cloudinary);
    }
}
