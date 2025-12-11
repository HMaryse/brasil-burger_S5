package com.brasilburger.view;

import com.brasilburger.entity.Burger;
import com.brasilburger.service.BurgerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BurgerView {

    private final BurgerService service;
    private final Scanner scanner = new Scanner(System.in);

    public BurgerView(BurgerService service) {
        this.service = service;
    }

    public void addBurger() {
        System.out.println("Nom du burger : ");
        String nom = scanner.nextLine();

        System.out.println("Prix du burger : ");
        double prix = Double.parseDouble(scanner.nextLine());

        service.addBurger(nom, prix);

        System.out.println("Burger ajouté avec succès !");
    }

    public void listBurgers() {
        List<Burger> list = service.findAll();
        System.out.println("=== Liste des burgers ===");

        for (Burger b : list) {
            System.out.println("ID: " + b.getId());
            System.out.println("Nom: " + b.getNom());
            System.out.println("Prix: " + b.getPrix());
            System.out.println("Image: " + b.getImageUrl());
            System.out.println("État: " + b.getEtat());
            System.out.println("---------------------------");
        }
    }

    public void modifyBurger() {
        System.out.println("ID du burger à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Burger> opt = service.findById(id);
        if (!opt.isPresent()) {
            System.out.println("Burger introuvable !");
            return;
        }

        Burger existing = opt.get();

        System.out.println("Nom actuel: " + existing.getNom() + " ou Nouveau nom (entrer pour garder le nom) : ");
        String nouveauNom = scanner.nextLine();
        if (nouveauNom.trim().isEmpty()) nouveauNom = existing.getNom();

        System.out.println("Prix actuel: " + existing.getPrix() + " ou Nouveau prix (enter pour garder) : ");
        String prixInput = scanner.nextLine();
        double nouveauPrix = prixInput.trim().isEmpty() ? existing.getPrix() : Double.parseDouble(prixInput);

        System.out.println("État actuel: " + existing.getEtat() + " ou Nouvel état (D = DISPONIBLE, I = INDISPONIBLE, enter pour garder) : ");
        String etatInput = scanner.nextLine().trim().toUpperCase();

        String nouveauEtat = existing.getEtat();
        if (etatInput.equals("D")) nouveauEtat = "DISPONIBLE";
        else if (etatInput.equals("I")) nouveauEtat = "INDISPONIBLE";

        Burger updated = service.updateBurger(id, nouveauNom, nouveauPrix, nouveauEtat);

        System.out.println("Burger mis à jour !");
        System.out.println("Nouvelle image : " + updated.getImageUrl());
    }
}
