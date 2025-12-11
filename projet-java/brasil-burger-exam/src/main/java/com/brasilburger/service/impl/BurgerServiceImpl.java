package com.brasilburger.service.impl;

import com.brasilburger.entity.Burger;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.service.BurgerService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BurgerServiceImpl implements BurgerService {

    private final BurgerRepository burgerRepo;
    private final Cloudinary cloudinary;

    //  IMAGES CLOUDINARY 
    private static final String[] BURGER_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483704/burger-5523523_1280_nrppxv.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483703/sandwich-5930494_1280_khlpyv.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483602/burger-7334481_1280_yxtu3z.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765478274/photo-1586190848861-99aa4a171e90_zf0ilr.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765477407/photo-1550547660-d9450f859349_djqjhz.jpg"
    };

    private static final String[] MENU_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483119/hamburger-4008822_1280_lqujr1.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483083/hamburger-6641821_1280_ghkaky.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483010/hamburger-7422976_1280_c4xjvb.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765478091/photo-1551782450-17144efb9c50_gfribr.jpg"
    };

    public BurgerServiceImpl(BurgerRepository repo, Cloudinary cloudinary) {
        this.burgerRepo = repo;
        this.cloudinary = cloudinary;
    }

    @Override
    public Burger addBurger(String nom, double prix) {
        String imageUrl = generateImageUrl(nom);
        Burger b = new Burger(nom, prix, imageUrl);
        return burgerRepo.save(b);
    }

    @Override
    public Burger updateBurger(int id, String newName, double newPrix, String newEtat) {
        Optional<Burger> opt = burgerRepo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Burger introuvable id = " + id);
        }

        Burger existing = opt.get();

        boolean nameChanged = !existing.getNom().equalsIgnoreCase(newName);

        existing.setNom(newName);
        existing.setPrix(newPrix);
        existing.setEtat(newEtat);

        if (nameChanged) {
            existing.setImageUrl(generateImageUrl(newName));
        }

        return burgerRepo.update(existing);
    }

    private String generateImageUrl(String nom) {
        try {
            String lower = nom.toLowerCase();
            String imageSelection;

            if (lower.contains("menu")) {
                imageSelection = MENU_IMAGES[(int)(Math.random() * MENU_IMAGES.length)];
            } else {
                imageSelection = BURGER_IMAGES[(int)(Math.random() * BURGER_IMAGES.length)];
            }

            Map upload = cloudinary.uploader().upload(
                    imageSelection,
                    ObjectUtils.asMap(
                            "folder", "burgers/generated",
                            "public_id", "burger_" + System.currentTimeMillis()
                    )
            );

            return upload.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération image burger : " + e.getMessage());
        }
    }

    @Override
    public Optional<Burger> findById(int id) { return burgerRepo.findById(id); }

    @Override
    public List<Burger> findAll() { return burgerRepo.findAll(); }
}
