package com.brasilburger;

import java.util.Scanner;

import com.brasilburger.repository.impl.BurgerRepositoryImpl;
import com.brasilburger.service.impl.BurgerServiceImpl;
import com.brasilburger.view.BurgerView;

public class App 
{
    public static void main(String[] args) {

        // Injection des dépendances
        BurgerServiceImpl burgerService = new BurgerServiceImpl(
            new BurgerRepositoryImpl()
        );

        BurgerView burgerView = new BurgerView(burgerService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== BRASIL BURGER ===");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Quitter");
            System.out.print("Choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1:
                    burgerView.addBurger();
                    break;

                case 2:
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}
