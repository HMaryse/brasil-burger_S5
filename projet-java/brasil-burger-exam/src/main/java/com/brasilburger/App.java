package com.brasilburger;

import com.brasilburger.config.factory.BurgerFactory;
import com.brasilburger.config.factory.ComplementFactory;
import com.brasilburger.config.factory.MenuFactory;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.MenuService;
import com.brasilburger.view.BurgerView;
import com.brasilburger.view.ComplementView;
import com.brasilburger.view.MenuView;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        
        BurgerService burgerService = BurgerFactory.createService();
        ComplementService complementService = ComplementFactory.createService();
        MenuService menuService = MenuFactory.createService();

        
        BurgerView burgerView = new BurgerView(burgerService);
        ComplementView complementView = new ComplementView(complementService);
        MenuView menuView = new MenuView(menuService, burgerService, complementService); 
        
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=== BRASIL BURGER ===");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Lister les burgers");
            System.out.println("3. Modifier un burger");
            System.out.println("4. Ajouter un complément");
            System.out.println("5. Lister les compléments");
            System.out.println("6. Modifier un complément");
            System.out.println("7. Ajouter un menu");
            System.out.println("8. Lister les menus");
            System.out.println("9. Modifier un menu");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {

                // BURGERS
                case 1: burgerView.addBurger(); break;
                case 2: burgerView.listBurgers(); break;
                case 3: burgerView.modifyBurger(); break;
                // COMPLEMENTS
                case 4: complementView.addComplement(); break;
                case 5: complementView.listComplements(); break;
                case 6: complementView.modifyComplement(); break;
                // MENUS
                case 7: menuView.addMenu(); break;
                case 8: menuView.listMenus(); break;
                case 9: menuView.modifyMenu(); break;

                case 0:
                    System.out.println("Au revoir !");
                    System.exit(0);

                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}
