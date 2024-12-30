import io.LecteurDonnees;
import simulation.*;
import gui.GUISimulator;
import robots.*;
import carte.*;
import plus_court_chemin.aetoile;
import simulation.ChefRobotPompier;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;

public class desertOfDeath {
    public static void main(String[] args) {
        String fichierDonnees = "cartes/desertOfDeath-20x20.map";
        
        try {
            // Lecture des données de simulation
            DonneesSimulation donnees = LecteurDonnees.lire(fichierDonnees);
            GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
            Simulateur simulateur = new Simulateur(gui);
            simulateur.initialize(donnees);

            // Création d'un chef pompier
            ChefRobotPompier chef = new ChefRobotPompier(simulateur);

            // Thread pour l'actualisation de l'affichage toutes les 10ms
            Thread affichageThread = new Thread(() -> {
                while (!chef.interventionFinie()) {
                    // Actualisation de l'affichage
                    chef.getSimulateur().dessinerCarte();
                    try {
                        Thread.sleep(10);  // Pause de 10ms avant de mettre à jour à nouveau l'affichage
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Démarre le thread d'affichage
            affichageThread.start();

            // Thread principal qui gère l'intervention du chef pompier toutes les 10 secondes
            while (!chef.interventionFinie()) {
                chef.TraitementDuChefPompier();
                try {
                    // On attend 10 secondes avant de reprendre l'itération
                    Thread.sleep(1000); // 10000 millisecondes = 10 secondes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Une fois l'intervention terminée, affiche un message
            System.out.println("Le traitement est terminé.");

            // Attendre que le thread d'affichage se termine avant de quitter le programme
            try {
                affichageThread.join(); // Attente que le thread d'affichage termine avant de finir l'exécution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
