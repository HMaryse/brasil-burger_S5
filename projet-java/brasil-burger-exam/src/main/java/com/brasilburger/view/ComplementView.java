package com.brasilburger.view;

import com.brasilburger.entity.Complement;
import com.brasilburger.service.ComplementService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ComplementView {

    private final ComplementService service;
    private final Scanner scanner = new Scanner(System.in);

    public ComplementView(ComplementService service) {
        this.service = service;
    }

    public void addComplement() {
        System.out.print("Nom du complément : ");
        String nom = scanner.nextLine();

        System.out.print("Prix du complément : ");
        double prix = Double.parseDouble(scanner.nextLine());

        service.addComplement(nom, prix);

        System.out.println("Complément ajouté !");
    }

    public void listComplements() {
        List<Complement> list = service.findAll();
            for (Complement c : list) {
                System.out.println("-----------------------");
                System.out.println("ID: " + c.getId());
                System.out.println("Nom: " + c.getNom());
                System.out.println("Prix: " + c.getPrix());
                System.out.println("Image: " + c.getImageUrl());
                System.out.println("État: " + c.getEtat());
            }
        }
    public void modifyComplement() {
        System.out.println("ID du complément à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Complement> opt = service.findById(id);
        if (!opt.isPresent()) {
            System.out.println("Complément introuvable !");
            return;
        }

        Complement c = opt.get();

        System.out.println("Nom actuel : " + c.getNom() + " ou Nouveau nom : (enter pour garder)");
        String newName = scanner.nextLine();
        if (newName.trim().isEmpty()) newName = c.getNom();

        System.out.println("Prix actuel : " + c.getPrix() + " ou Nouveau prix : (enter pour garder)");
        String prixStr = scanner.nextLine();
        double newPrix = prixStr.trim().isEmpty() ? c.getPrix() : Double.parseDouble(prixStr);

        System.out.println("État actuel : " + c.getEtat() + " ou Nouveau état (DISPONIBLE / INDISPONIBLE) :");
        String newEtat = scanner.nextLine();
        if (newEtat.trim().isEmpty()) newEtat = c.getEtat();

        Complement updated = service.updateComplement(id, newName, newPrix, newEtat);

        System.out.println(" Complément modifié !");
        System.out.println("Nouvelle image : " + updated.getImageUrl());
    }

}
