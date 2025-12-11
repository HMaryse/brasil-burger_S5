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

    // Images 
    private static final String[] BURGER_IMAGES = {
        "https://cdn.pixabay.com/photo/2014/10/23/18/05/burger-500054_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/03/05/19/02/burger-1238246_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/02/19/11/53/hamburger-1209078_1280.jpg"
    };

    private static final String[] FRIES_IMAGES = {
        "https://cdn.pixabay.com/photo/2017/03/17/08/56/french-fries-2150900_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/05/10/18/46/potato-2304661_1280.jpg"
    };

    private static final String[] DRINK_IMAGES = {
        "https://cdn.pixabay.com/photo/2017/07/28/14/28/cola-2547680_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/22/19/33/drink-1853254_1280.jpg"
    };

    private static final String[] MENU_IMAGES = {
        "https://cdn.pixabay.com/photo/2017/01/22/19/20/burger-2000596_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/03/05/19/02/burger-1238246_1280.jpg"
    };

    public BurgerServiceImpl(BurgerRepository repo, Cloudinary cloudinary) {
        this.burgerRepo = repo;
        this.cloudinary = cloudinary;
    }

    // AJOUT BURGER
    @Override
    public Burger addBurger(String nom, double prix) {
        String imageUrl = generateImageUrl(nom);
        Burger b = new Burger(nom, prix, imageUrl);
        return burgerRepo.save(b);
    }

    // MODIFIER BURGER
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

        // nouvelle image si nom changé
        if (nameChanged) {
            existing.setImageUrl(generateImageUrl(newName));
        }

        return burgerRepo.update(existing);
    }

    @Override
    public Optional<Burger> findById(int id) {
        return burgerRepo.findById(id);
    }

    @Override
    public List<Burger> findAll() {
        return burgerRepo.findAll();
    }

    private String generateImageUrl(String nom) {
        try {
            String lower = nom.toLowerCase();
            String imageSelectionne;

            if (lower.contains("frit")) {
                imageSelectionne = FRIES_IMAGES[(int)(Math.random() * FRIES_IMAGES.length)];
            } else if (lower.contains("coca") || lower.contains("soda") || lower.contains("fanta") || lower.contains("boisson")) {
                imageSelectionne = DRINK_IMAGES[(int)(Math.random() * DRINK_IMAGES.length)];
            } else if (lower.contains("menu")) {
                imageSelectionne = MENU_IMAGES[(int)(Math.random() * MENU_IMAGES.length)];
            } else {
                imageSelectionne = BURGER_IMAGES[(int)(Math.random() * BURGER_IMAGES.length)];
            }

            Map upload = cloudinary.uploader().upload(imageSelectionne, ObjectUtils.asMap(
                "folder", "brasilburger/images",
                "public_id", "burger_" + System.currentTimeMillis()
            ));

            return upload.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération image : " + e.getMessage());
        }
    }
}
