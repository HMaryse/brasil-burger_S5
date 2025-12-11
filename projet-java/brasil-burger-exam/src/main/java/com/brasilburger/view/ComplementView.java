package com.brasilburger.view;

import com.brasilburger.entity.Complement;
import com.brasilburger.service.ComplementService;

import java.util.List;
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
}
