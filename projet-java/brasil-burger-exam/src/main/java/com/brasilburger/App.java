package com.brasilburger;

import com.brasilburger.config.factory.BurgerFactory;
import com.brasilburger.config.factory.ComplementFactory;

import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;

import com.brasilburger.view.BurgerView;
import com.brasilburger.view.ComplementView;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        BurgerService burgerService = BurgerFactory.createService();
        BurgerView burgerView = new BurgerView(burgerService);
        ComplementService complementService = ComplementFactory.createService();
        ComplementView complementView = new ComplementView(complementService);

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=== BRASIL BURGER ===");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Lister les burgers");
            System.out.println("3. Modifier un burger");
            System.out.println("4. Ajouter un complément");
            System.out.println("5. Lister les compléments");
            System.out.println("6. Modifier un complément");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {

                case 1: 
                    burgerView.addBurger();
                break;
                case 2: 
                    burgerView.listBurgers();
                 break;
                case 3: 
                    burgerView.modifyBurger(); 
                break;
                case 4: 
                    complementView.addComplement(); 
                break;
                case 5: 
                    complementView.listComplements(); 
                break;
                case 6:
                    complementView.modifyComplement();
                    break;

                case 0:
                    System.out.println("Au revoir !");
                    System.exit(0);

                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}
