# Pharmaco - Logiciel de Gestion de Pharmacie

Pharmaco est un logiciel de gestion de pharmacie conçu pour aider les pharmaciens à gérer efficacement leur stock de médicaments. Il permet de gérer différents types de médicaments, notamment :

Médicaments en vente libre : Ces médicaments peuvent être achetés sans ordonnance médicale. Le logiciel permet de suivre leur stock, leurs prix et leurs informations spécifiques.
Médicaments sur ordonnance : Ces médicaments ne peuvent être délivrés que sur présentation d'une ordonnance médicale.
## video de presentation youtube:
### https://www.youtube.com/watch?v=v-kTPYPVE5s&ab_channel=BabiMumba
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/v-kTPYPVE5s/0.jpg)](https://www.youtube.com/watch?v=v-kTPYPVE5s)

## Caractéristiques principales de Pharmaco :

* Ajouter un médicament
* Supprimer un médicament
* Rechercher un médicament par nom ou identifiant
* Afficher la liste des médicaments par type
* Modifier un médicament par son identifiant.
* Créer un menu d'accueil.
* Lister les médicaments en saisissant une lettre alphabétique.
* Afficher le nombre de médicaments en stock.
* Ajouter trois autres fonctionnalités qui vous semblent utiles


  
Pharmaco est conçu pour répondre aux besoins spécifiques des pharmacies, facilitant ainsi la gestion des stocks, 
des ventes et des processus liés à la distribution de médicaments. 
Son objectif est d'améliorer l'efficacité des pharmacies tout en garantissant la sécurité et la traçabilité des transactions.

## Instruction pour compiler et executer le projet:

### Prérequis
* Java Development Kit (JDK) version 11 ou supérieure
* IntelliJ IDEA (ou un autre IDE compatible avec Java et JavaFX)
* Laragon (ou un autre serveur local MySQL)
### Installation et configuration
   ### 1.Cloner le dépôt Git :
```
git clone https://github.com/BabiMumba/Pharmacie.git
```
### 2.Configurer la base de données :

* Démarrez Laragon et lancez le serveur MySQL.
* Créez une nouvelle base de données appelée pharmaco.
* Importez les tables personne et medicament depuis le dossier database du projet.
## 3.Ouvrir le projet dans IntelliJ IDEA :
* Ouvrez IntelliJ IDEA et sélectionnez "Open" ou "Import Project".
* Naviguez jusqu'au dossier du projet cloné et sélectionnez-le.
* Laissez IntelliJ IDEA configurer automatiquement le projet.
### 4.Configurer la connexion à la base de données :
* Ouvrez le fichier DatabaseConnection.java situé dans le package utils.
* Mettez à jour les informations de connexion (URL, nom d'utilisateur, mot de passe) avec les informations de votre serveur MySQL local.
## Compilation et exécution
### 1.Compiler le projet :
* Dans IntelliJ IDEA, allez dans "File" > "Project Structure" > "Project" et assurez-vous que le JDK 11 (ou supérieur) est sélectionné.
* Ensuite, allez dans "File" > "Project Structure" > "Modules" et vérifiez que le module pharmaco est configuré correctement, avec les dépendances JavaFX et MySQL.
* Cliquez sur "Apply" et "OK" pour enregistrer les changements.
Dans la barre d'outils, cliquez sur "Build" > "Build Project" pour compiler le projet.
### 2.Exécuter le projet :
* Dans la barre d'outils, cliquez sur "Run" > "Run 'Main'".
* Le programme Pharmaco devrait démarrer et afficher l'écran de connexion.
### 3.Se connecter à l'application :
* Utilisez les identifiants de connexion suivants :
* Nom d'utilisateur : admin
* Mot de passe : password
* Après vous être connecté, vous pourrez naviguer dans les différentes fonctionnalités de l'application Pharmaco.

