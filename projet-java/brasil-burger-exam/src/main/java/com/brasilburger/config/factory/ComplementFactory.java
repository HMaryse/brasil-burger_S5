package com.brasilburger.config.factory;

import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.repository.impl.ComplementRepositoryImpl;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.impl.ComplementServiceImpl;
import com.cloudinary.Cloudinary;

public class ComplementFactory {

    public static ComplementRepository createRepository() {
        return new ComplementRepositoryImpl();
    }

    public static ComplementService createService() {
        Cloudinary cloudinary = AppFactory.getCloudinary(); 
        return new ComplementServiceImpl(createRepository(), cloudinary);
    }
}
