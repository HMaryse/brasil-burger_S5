package com.brasilburger.view;
import com.brasilburger.entity.Menu;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.MenuService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuView {

    private final MenuService service;
    private final BurgerService burgerService;
    private final ComplementService complementService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuView(MenuService service, BurgerService burgerService, ComplementService complementService) {
        this.service = service;
        this.burgerService = burgerService;
        this.complementService = complementService;
    }
 
    // AJOUTER UN MENU
    public void addMenu() {
        System.out.print("Nom du menu : ");
        String nom = scanner.nextLine();

        System.out.println("\n---Liste des Burgers disponibles ---");
        burgerService.findAll().forEach(b ->
                System.out.println(b.getId() + " - " + b.getNom() + " (" + b.getPrix() + " FCFA)")
        );
        System.out.print("ID des burgers (séparés par virgule) : ");
        List<Integer> burgerIds = parseIds(scanner.nextLine());

        System.out.println("\n---Liste des Compléments disponibles ---");
        complementService.findAll().forEach(c ->
                System.out.println(c.getId() + " - " + c.getNom() + " (" + c.getPrix() + " FCFA)")
        );
        System.out.print("ID des compléments (séparés par virgule) : ");
        List<Integer> complementIds = parseIds(scanner.nextLine());

        Menu m = service.addMenu(nom, burgerIds, complementIds);

        System.out.println("\nMenu créé !");
        System.out.println("ID : " + m.getId());
        System.out.println("Prix total : " + m.getPrix() + " FCFA");
        System.out.println("Image : " + m.getImageUrl());
    }

    // LISTER LES MENUS
    public void listMenus() {
        System.out.println("\n=== LISTE DES MENUS ===");

        List<Menu> menus = service.findAll();
        if (menus.isEmpty()) {
            System.out.println("Aucun menu disponible.");
            return;
        }
        for (Menu m : menus) {
            System.out.println("\nID : " + m.getId());
            System.out.println("Nom : " + m.getNom());
            System.out.println("Prix : " + m.getPrix() + " FCFA");
            System.out.println("Image : " + m.getImageUrl());
            System.out.println("État : " + m.getEtat());
        }
    }
    
    // MODIFIER INFORMATIONS DU MENU : nom + état
    public void modifyMenu() {
        System.out.print("ID du menu à modifier : ");
        String idLine = scanner.nextLine().trim();
        if (idLine.isEmpty()) {
            System.out.println("ID invalide.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idLine);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return;
        }
        Optional<Menu> opt = service.findById(id);
        if (!opt.isPresent()) {
            System.out.println("Menu introuvable !");
            return;
        }

        Menu m = opt.get();
        System.out.println("Nom actuel : " + m.getNom());
        System.out.print("Nouveau nom (enter pour garder) : ");
        String newName = scanner.nextLine();
        if (newName.trim().isEmpty()) newName = m.getNom();
        System.out.println("État actuel : " + m.getEtat());
        System.out.print("Nouvel état (DISPONIBLE / INDISPONIBLE) : ");
        String newEtat = scanner.nextLine().trim().toUpperCase();
        if (newEtat.isEmpty()) newEtat = m.getEtat();
        try {
            Menu updated = service.updateMenu(id, newName, newEtat);
            System.out.println("\nMenu mis à jour !");
            System.out.println("Nom : " + updated.getNom());
            System.out.println("État : " + updated.getEtat());
        } catch (Exception e) {
            System.out.println(" Erreur mise à jour : " + e.getMessage());
        }
    }

    // MODIFIER LA COMPOSITION D’UN MENU
    public void modifyMenuComposition() {

        System.out.print("ID du menu à modifier : ");
        String idLine = scanner.nextLine().trim();
        if (idLine.isEmpty()) {
            System.out.println("ID invalide.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idLine);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return;
        }

        Optional<Menu> opt = service.findById(id);
        if (!opt.isPresent()) {
            System.out.println("Menu introuvable !");
            return;
        }

        // Burgers
        System.out.println("\n--- Burgers disponibles ---");
        burgerService.findAll().forEach(b ->
                System.out.println(b.getId() + " - " + b.getNom() + " (" + b.getPrix() + " FCFA)")
        );
        System.out.print("Nouveaux ID burgers (séparés par virgule) : ");
        List<Integer> burgerIds = parseIds(scanner.nextLine());

        // Compléments
        System.out.println("\n--- Compléments disponibles ---");
        complementService.findAll().forEach(c ->
                System.out.println(c.getId() + " - " + c.getNom() + " (" + c.getPrix() + " FCFA)")
        );
        System.out.print("Nouveaux ID compléments (séparés par virgule) : ");
        List<Integer> compIds = parseIds(scanner.nextLine());

        try {
            Menu updated = service.updateMenuComposition(id, burgerIds, compIds);
            System.out.println("\nComposition mise à jour !");
            System.out.println("Nouveau prix : " + updated.getPrix() + " FCFA");
        } catch (Exception e) {
            System.out.println(" Erreur mise à jour : " + e.getMessage());
        }
    }

    // UTILITAIRE : PARSE DES IDS
    private List<Integer> parseIds(String text) {
        List<Integer> list = new ArrayList<>();
        if (text == null) return list;

        String trimmed = text.trim();
        if (trimmed.isEmpty()) return list;

        String[] parts = trimmed.split(",");
        for (String p : parts) {
            String s = p.trim();
            if (s.isEmpty()) continue;

            try {
                list.add(Integer.parseInt(s));
            } catch (NumberFormatException ex) {
                System.out.println("ID ignoré (non numérique) : " + s);
            }
        }
        return list;
    }
}
