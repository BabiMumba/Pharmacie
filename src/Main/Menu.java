package Main;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

  //fonction pour afficher le menu
    public static void displayMenu() {
        System.out.println("1. Afficher les médicaments");
        System.out.println("2. Ajouter un médicament");
        System.out.println("3. Mettre à jour un médicament");
        System.out.println("4. Supprimer un médicament");
        System.out.println("5. Quitter");
    }

}
