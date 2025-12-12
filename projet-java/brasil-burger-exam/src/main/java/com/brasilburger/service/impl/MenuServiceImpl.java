package com.brasilburger.service.impl;

import com.brasilburger.entity.Burger;
import com.brasilburger.entity.Complement;
import com.brasilburger.entity.Menu;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.repository.MenuDetailRepository;
import com.brasilburger.service.MenuService;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepo;
    private final MenuDetailRepository detailRepo;
    private final BurgerService burgerService;
    private final ComplementService complementService;

    // IMAGES MENU 
    private static final String[] MENU_IMAGES = {
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483119/hamburger-4008822_1280_lqujr1.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483083/hamburger-6641821_1280_ghkaky.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765483010/hamburger-7422976_1280_c4xjvb.jpg",
        "https://res.cloudinary.com/derru3bz9/image/upload/v1765478091/photo-1551782450-17144efb9c50_gfribr.jpg"
    };

    public MenuServiceImpl(
            MenuRepository menuRepo,
            MenuDetailRepository detailRepo,
            BurgerService burgerService,
            ComplementService complementService
    ) {
        this.menuRepo = menuRepo;
        this.detailRepo = detailRepo;
        this.burgerService = burgerService;
        this.complementService = complementService;
    }

    @Override
    public Menu addMenu(String nom, List<Integer> burgerIds, List<Integer> complementIds) {

        double totalPrice = 0;

        for (int id : burgerIds) {
            Burger b = burgerService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Burger introuvable: " + id));
            totalPrice += b.getPrix();
        }

        for (int id : complementIds) {
            Complement c = complementService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Complément introuvable: " + id));
            totalPrice += c.getPrix();
        }

        
        String imageUrl = generateMenuImage();

        Menu menu = new Menu(nom, totalPrice, imageUrl, "DISPONIBLE");
        Menu saved = menuRepo.save(menu);

        
        detailRepo.saveMenuBurgers(saved.getId(), burgerIds);
        detailRepo.saveMenuComplements(saved.getId(), complementIds);

        return saved;
    }

    @Override
    public Menu updateMenu(int id, String newNom, String newEtat) {
        Optional<Menu> opt = menuRepo.findById(id);

        if (!opt.isPresent()) {
            throw new RuntimeException("Menu introuvable id=" + id);
        }

        Menu m = opt.get();
        m.setNom(newNom);
        m.setEtat(newEtat);

        return menuRepo.update(m);
    }

    @Override
    public Optional<Menu> findById(int id) {
        return menuRepo.findById(id);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepo.findAll();
    }

    // Génération d'une image du menu
    private String generateMenuImage() {
        return MENU_IMAGES[new Random().nextInt(MENU_IMAGES.length)];
    }
}
