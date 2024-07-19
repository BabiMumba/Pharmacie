import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Pharmacie pharmacie = new Pharmacie();
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("Menu:");
            System.out.println("1. Ajouter un médicament");
            System.out.println("2. Supprimer un médicament");
            System.out.println("3. Rechercher un médicament par ID");
            System.out.println("4. Afficher les médicaments par type");
            System.out.println("5. Modifier un médicament");
            System.out.println("6. Lister les médicaments par lettre");
            System.out.println("7. Afficher tous les médicaments");
            System.out.println("8. Afficher le nombre de médicaments");
            System.out.println("9. Quitter");
            System.out.print("Choisissez une option: ");

            try {
                int choix = scanner.nextInt();
                switch (choix) {
                    case 1:
                        System.out.print("Entrez l'ID du médicament: ");
                        String id = scanner.next();
                        System.out.print("Entrez le nom du médicament: ");
                        String nom = scanner.next();
                        System.out.print("Entrez le type du médicament: ");
                        String type = scanner.next();
                        Medicament medicament = new Medicament(id, nom, type);
                        pharmacie.ajouterMedicament(medicament);
                        break;
                    case 2:
                        //pour supprimer un médicament
                        System.out.print("Entrez l'ID du médicament à supprimer: ");
                        String idMedicament = scanner.next();
                        pharmacie.supprimerMedicament(idMedicament);
                        break;
                    case 3:
                        System.out.print("Entrez l'ID du médicament à rechercher: ");
                        String ida = scanner.next(); // Read the medication ID from the user
                        Medicament medicaments = pharmacie.rechercherParId(ida);
                        if (medicaments != null) {
                            System.out.println("Médicament trouvé: " + medicaments);
                        } else {
                            System.out.println("Aucun médicament trouvé avec l'ID: " + ida);
                        }
                        break;
                    case 4:
                        //pour afficher les médicaments par type
                        System.out.print("Entrez le type de médicament à afficher: ");
                        String typeMedicament = scanner.next();
                        pharmacie.afficherMedicamentsParType(typeMedicament);
                        break;
                    case 5:
                        //pour modifier un médicament
                        System.out.print("Entrez l'ID du médicament à modifier: ");
                        String idMedicamentModif = scanner.next();
                        System.out.print("Entrez le nouveau nom du médicament: ");
                        String nomMedicamentModif = scanner.next();
                        System.out.print("Entrez le nouveau type du médicament: ");
                        String typeMedicamentModif = scanner.next();
                        pharmacie.modifierMedicament(idMedicamentModif, nomMedicamentModif, typeMedicamentModif);

                        break;
                    case 6:
                        //pour lister les médicaments par lettre
                        System.out.print("Entrez la lettre du médicament à lister: ");
                        char lettre = scanner.next().charAt(0);
                        pharmacie.listerMedicamentsParLettre(lettre);

                        break;
                    case 7:
                        pharmacie.afficherTousLesMedicaments();
                        break;
                    case 8:
                        pharmacie.afficherNombreMedicaments();
                        break;
                    case 9:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next(); // to consume the invalid input
            }

            if (continuer) {
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine(); // to catch the nextInt newline
                scanner.nextLine(); // to actually pause
            }
        }
    }
}