import io.LecteurDonnees;

import simulation.*;
import gui.GUISimulator;

import robots.*;
import carte.*;
import plus_court_chemin.aetoile;
import simulation.ChefRobotPompier;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;



public class testEvenement {
    public static void main(String[] args) {
        String fichierDonnees = "cartes/carteSujet.map";

        try {
            DonneesSimulation donnees = LecteurDonnees.lire(fichierDonnees);
            GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
            Simulateur simulateur = new Simulateur(gui);
            simulateur.initialize(donnees);

            // Déplacement du premier robot (on suppose que c'est un drone)
            Robot robot1 = donnees.getRobots()[0];
            Robot robot2 = donnees.getRobots()[1];
            for (int i = 0; i < 3; i++) {
                simulateur.ajouteEvenement(robot1.deplacer(Direction.NORD));
                
            }
            simulateur.ajouteEvenement(robot2.deplacer(Direction.NORD));
        } catch (FileNotFoundException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
