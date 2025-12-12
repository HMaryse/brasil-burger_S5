package com.brasilburger.view;

import com.brasilburger.entity.Burger;
import com.brasilburger.entity.Complement;
import com.brasilburger.entity.Menu;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final MenuService menuService;
    private final BurgerService burgerService;
    private final ComplementService complementService;

    private final Scanner scanner = new Scanner(System.in);

    public MenuView(MenuService menuService, BurgerService burgerService, ComplementService complementService) {
        this.menuService = menuService;
        this.burgerService = burgerService;
        this.complementService = complementService;
    }

    public void addMenu() {

        System.out.println("Nom du menu : ");
        String nom = scanner.nextLine();

        System.out.println("\n--- Liste des burgers ---");
        List<Burger> burgers = burgerService.findAll();
        for (Burger b : burgers) {
            System.out.println(b.getId() + " - " + b.getNom() + " (" + b.getPrix() + " FCFA)");
        }

        System.out.println("Entrez les ID des burgers (séparés par des virgules) : ");
        String burgerInput = scanner.nextLine();
        List<Integer> burgerIds = parseIds(burgerInput);

        System.out.println("\n--- Liste des compléments ---");
        List<Complement> complements = complementService.findAll();
        for (Complement c : complements) {
            System.out.println(c.getId() + " - " + c.getNom() + " (" + c.getPrix() + " FCFA)");
        }

        System.out.println("Entrez les ID des compléments (séparés par des virgules) : ");
        String complementInput = scanner.nextLine();
        List<Integer> complementIds = parseIds(complementInput);

        Menu saved = menuService.addMenu(nom, burgerIds, complementIds);

        System.out.println("\nMenu ajouté !");
        System.out.println("ID : " + saved.getId());
        System.out.println("Nom : " + saved.getNom());
        System.out.println("Image : " + saved.getImageUrl());
        System.out.println("Prix total : " + saved.getPrix() + " FCFA");
    }

    public void listMenus() {
        List<Menu> menus = menuService.findAll();

        System.out.println("\n=== LISTE DES MENUS ===");

        for (Menu m : menus) {
            System.out.println("ID : " + m.getId());
            System.out.println("Nom : " + m.getNom());
            System.out.println("Prix : " + m.getPrix());
            System.out.println("Image : " + m.getImageUrl());
            System.out.println("État : " + m.getEtat());
            System.out.println("----------------------");
        }
    }

    private List<Integer> parseIds(String input) {
        List<Integer> list = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            return list;
        }

        String[] parts = input.split(",");
        for (String p : parts) {
            try {
                list.add(Integer.parseInt(p.trim()));
            } catch (Exception e) {
                System.out.println("ID ignoré : " + p);
            }
        }
        return list;
    }
}
