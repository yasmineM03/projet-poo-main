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

public class Main {
    public static void main(String[] args){
        String fichierDonnees = "cartes/carteSujet.map";
        try {
            DonneesSimulation donnees = LecteurDonnees.lire(fichierDonnees);
            GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
            Simulateur simulateur = new Simulateur(gui);
            simulateur.initialize(donnees);

            ChefRobotPompier chef = new ChefRobotPompier(simulateur);
            while (!chef.interventionFinie()) {
                chef.TraitementDuChefPompier();
                try {
                    // On attend 10 secondes avant de reprendre l'itération
                    Thread.sleep(1000); // 10000 millisecondes = 10 secondes
                } catch (InterruptedException e) {
                    // En cas d'interruption du thread, afficher un message d'erreur
                    e.printStackTrace();
                }
            }
            System.out.println("le traitement est terminé");
            return;            

        } catch (FileNotFoundException | DataFormatException e) {
            e.printStackTrace();
        }
}   
}
