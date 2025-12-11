package com.brasilburger.view;

import com.brasilburger.service.BurgerService;

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
}
