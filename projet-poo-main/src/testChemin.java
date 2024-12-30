import io.LecteurDonnees;
import simulation.*;
import gui.GUISimulator;
import robots.*;
import carte.*;
import plus_court_chemin.aetoile;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;


public class testChemin {
    public static void main(String[] args){
        String fichierDonnees = "cartes/carteSujet.map";
        try {
            DonneesSimulation donnees = LecteurDonnees.lire(fichierDonnees);
            GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
            Simulateur simulateur = new Simulateur(gui);
            simulateur.initialize(donnees);

            Robot robot1 = simulateur.getDonnees().getRobots()[0];
            Case posRobot = new Case (robot1.getLigne(),robot1.getColonne(),NatureTerrain.TERRAIN_LIBRE);
            System.out.println("Position du robot");
            System.out.println(posRobot.toString());
            Case caseObjectif = new Case(6, 5, NatureTerrain.TERRAIN_LIBRE);
            System.out.println("Position de la case");
            System.out.println(caseObjectif.toString());
            aetoile cheminRobotIncendie = new aetoile();
            cheminRobotIncendie.aEtoileSearch(simulateur.getDonnees().getCarte(), caseObjectif, robot1);
            System.out.println("le chemin trouvé :");
            for (Direction direction : cheminRobotIncendie.getChemin()) {
                System.out.println(direction);
            }
            return;            

        } catch (FileNotFoundException | DataFormatException e) {
            e.printStackTrace();
        }
}   
}

