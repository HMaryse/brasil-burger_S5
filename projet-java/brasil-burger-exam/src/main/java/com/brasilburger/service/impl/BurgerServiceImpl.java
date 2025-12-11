package com.brasilburger.service.impl;

import com.brasilburger.entity.Burger;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.service.BurgerService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

public class BurgerServiceImpl implements BurgerService {

    private final BurgerRepository burgerRepo;

    // Ton Cloud Name Cloudinary
    private final String CLOUD_NAME = "derru3bz9";

    // Cloudinary instance
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", CLOUD_NAME,
            "api_key", "162132438588965",             
            "api_secret", "nIor2bZj0Su0Tvfhqz3eFFALVwY" 
    ));

    public BurgerServiceImpl(BurgerRepository repo) {
        this.burgerRepo = repo;
    }

    @Override
    public Burger addBurger(String nom, double prix) {

        String imageUrl = generateImageUrl(nom);

        Burger b = new Burger(nom, prix, imageUrl);

        return burgerRepo.save(b);
    }

    private String generateImageUrl(String nom) {
        try {
            String lower = nom.toLowerCase();
            String[] images;

            // Catégorisation
            if (lower.contains("frit")) {
                images = new String[]{
                    "https://images.unsplash.com/photo-1541592106381-b31e9677c0e5",
                    "https://images.unsplash.com/photo-1525755662778-989d0524087e",
                    "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe"
                };
            } else if (lower.contains("coca") || lower.contains("fanta") || lower.contains("boisson") || lower.contains("soda")) {
                images = new String[]{
                    "https://images.unsplash.com/photo-1604908177529-1f24da66b34a",
                    "https://images.unsplash.com/photo-1620912189867-4d66144a3a52",
                    "https://images.unsplash.com/photo-1589927986089-358123789b59"
                };
            } else if (lower.contains("menu")) {
                images = new String[]{
                    "https://images.unsplash.com/photo-1565299711253-82901df2de47",
                    "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                    "https://images.unsplash.com/photo-1562967914-608f82629710"
                };
            } else { // BURGER
                images = new String[]{
                    "https://images.unsplash.com/photo-1550547660-d9450f859349",
                    "https://images.unsplash.com/photo-1608759264510-0a684b024be3",
                    "https://images.unsplash.com/photo-1606756790138-82b8da08f346",
                    "https://images.unsplash.com/photo-1550317138-10000687a72b",
                    "https://images.unsplash.com/photo-1571091718767-18b5b1457add",
                    "https://images.unsplash.com/photo-1603079842319-ab7bbcf3f092",
                    "https://images.unsplash.com/photo-1551782450-17144c3d71b4",
                    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd",
                    "https://images.unsplash.com/photo-1606760227091-3f346fefd45b",
                    "https://images.unsplash.com/photo-1516684669134-de6f27e526b2"
                };
            }

            // Sélection aléatoire
            String imageSource = images[(int)(Math.random() * images.length)];

            // Upload vers Cloudinary
            Map upload = cloudinary.uploader().upload(imageSource, ObjectUtils.asMap(
                    "folder", "brasilburger/images"
            ));

            return upload.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération image : " + e.getMessage());
        }
    }


}
