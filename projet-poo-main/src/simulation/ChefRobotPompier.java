package simulation;
import io.LecteurDonnees;
import gui.GUISimulator;
import robots.*;
import carte.*;
import plus_court_chemin.aetoile;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChefRobotPompier{
    private Simulateur simulateur;
    private Incendie[] incendies;
    private Robot[] robots;
    private List<Case> casesEau;

    public ChefRobotPompier(Simulateur simulateur) {
        this.simulateur = simulateur;
        this.incendies = simulateur.getDonnees().getIncendies();
        this.robots = simulateur.getDonnees().getRobots();
        this.casesEau = simulateur.getDonnees().getCarte().getCasesEau();
    }

    public boolean interventionFinie() {
        for (int idIncendie = 0; idIncendie < this.incendies.length; idIncendie++) {
            Incendie incendie = this.incendies[idIncendie];
            if (incendie.getLitres()!= 0) {
                return false;
            }
        }  
        return true;
    }

    public Simulateur getSimulateur() {
        return simulateur;
    }

    public void TraitementDuChefPompier() {
        //on traite incendie par incendie, en choisissant le robot le plus proche
        for (int idIncendie = 0; idIncendie < this.incendies.length; idIncendie++) {
            //si l'incendie n'est pas éteint
            Incendie incendie = this.incendies[idIncendie];
            if (incendie.getLitres()!= 0) {
                //on parcourt les robots pour trouver le plus adapté
                Robot robotChoisi = null;
                int meilleurTemps = 10000;
                Stack<Direction> meilleurChemin = null;

                for (int iRobot = 0; iRobot < this.robots.length; iRobot++) {
                    //on regarde si le robot considéré est libre et qu'il a assez d'eau
                    Robot robot = this.robots[iRobot];
                    if (this.simulateur.robotEstLibre(robot)&&
                    ((robot.getReservoir()==robot.returnMaxReservoir())||robot.getClasseRobot() == classeRobot.PATTES)) {

                        aetoile cheminRobotIncendie = new aetoile();
                        cheminRobotIncendie.aEtoileSearch(simulateur.getDonnees().getCarte(), incendie.getPosition(), robot);
                        if (cheminRobotIncendie.getTime()<meilleurTemps) {
                            meilleurTemps = cheminRobotIncendie.getTime();
                            robotChoisi = robot;
                            meilleurChemin = cheminRobotIncendie.getChemin();
                        }
                    }
                }
                //on regarde si on a trouvé un robot pour cet incendie :
                if (robotChoisi != null) {
                    while (!meilleurChemin.isEmpty()) {
                        Direction dir = meilleurChemin.pop();
                        Evenement deplacement = robotChoisi.deplacer(dir);
                        this.simulateur.ajouteEvenement(deplacement);
                    }
                    Evenement extinction = robotChoisi.eteindre(incendie);
                    this.simulateur.ajouteEvenement(extinction);
                }
            }
        }
        // aucun robot n'est à même d'éteindre cette incendie, on remplit donc tous les robots non occupés
        for (int iRobot = 0; iRobot < this.robots.length; iRobot++) {
            //on regarde si le robot considéré est libre et qu'il a assez d'eau
            Robot robot = this.robots[iRobot];
            if (this.simulateur.robotEstLibre(robot)) {
                remplirCeRobot(robot);
            }
        }
    }
    

    public void remplirCeRobot(Robot robot) {
        Case caseChoisie = null;
        int meilleurTemps = 10000;
        Stack<Direction> meilleurChemin = null;
        Case[] tabEau = casesEau.toArray(new Case[0]);
        for (int idEau = 0; idEau < tabEau.length; idEau++) {
            aetoile cheminRobotEau = new aetoile();
            cheminRobotEau.aEtoileSearch(simulateur.getDonnees().getCarte(), tabEau[idEau], robot);
            if (cheminRobotEau.getTime()<meilleurTemps) {
                meilleurTemps = cheminRobotEau.getTime();
                caseChoisie = tabEau[idEau];
                meilleurChemin = cheminRobotEau.getChemin();
                //vont tous à la case 2/2 parce q
            }
        }
        if (caseChoisie != null) {
            while (meilleurChemin.size() > 1) {
                Direction dir = meilleurChemin.pop();
                Evenement deplacement = robot.deplacer(dir);
                this.simulateur.ajouteEvenement(deplacement);
            }

            if (robot.getClasseRobot()==classeRobot.DRONE) {
                Direction dir = meilleurChemin.pop();
                Evenement deplacement = robot.deplacer(dir);
                this.simulateur.ajouteEvenement(deplacement);
            }

            Evenement remplissage = robot.remplir(caseChoisie);
            this.simulateur.ajouteEvenement(remplissage);
        }
    }

}

    