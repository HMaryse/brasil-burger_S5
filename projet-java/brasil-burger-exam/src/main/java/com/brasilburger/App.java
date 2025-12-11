package com.brasilburger;

import com.brasilburger.config.factory.BurgerFactory;
import com.brasilburger.service.BurgerService;
import com.brasilburger.view.BurgerView;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        BurgerService burgerService = BurgerFactory.createService();
        BurgerView burgerView = new BurgerView(burgerService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== BRASIL BURGER ===");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Lister les burgers");
            System.out.println("3. Modifier un burger");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1: burgerView.addBurger(); break;
                case 2: burgerView.listBurgers(); break;
                case 3: burgerView.modifyBurger(); break;
                case 4:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}
