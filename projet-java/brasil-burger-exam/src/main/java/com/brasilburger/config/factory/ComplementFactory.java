package com.brasilburger.config.factory;

import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.repository.impl.ComplementRepositoryImpl;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.impl.ComplementServiceImpl;
import com.cloudinary.Cloudinary;

public class ComplementFactory {

    // Crée le repository des compléments
    public static ComplementRepository createRepository() {
        return new ComplementRepositoryImpl();
    }

    // Crée le service des compléments avec Cloudinary injecté
    public static ComplementService createService() {
        Cloudinary cloudinary = AppFactory.getCloudinary(); // récupère l'instance unique
        return new ComplementServiceImpl(createRepository(), cloudinary);
    }
}
