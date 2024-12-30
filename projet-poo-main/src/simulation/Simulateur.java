package simulation;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.Color;
import carte.*;
import robots.*;
import gui.Rectangle;
import gui.Oval;
import gui.ImageElement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class Simulateur implements Simulable {
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private int date = 0;
    private long dateSimulation;
    private Queue<Evenement>[] tableauxEvenements;
    private long startTime;  
    private int[] robotPositionsLignes;
    private int[] robotPositionsColonnes;
    private int[] incendieQuantites;
    private List<Evenement> evenementsOriginaux;

    public Simulateur(GUISimulator gui) {
        this.gui = gui;
        gui.setSimulable(this);
        this.startTime = System.currentTimeMillis();  
        this.evenementsOriginaux = new ArrayList<>();
    }

    public void initialize(DonneesSimulation donnees) {
    
    this.donnees = donnees;
    sauvegarderEtatInitial(); 
    dessinerCarte();
    InitialiseStructureEvenement();  
}   

    public DonneesSimulation getDonnees() {
        return this.donnees;
    }

private void sauvegarderEtatInitial() {
        Robot[] robots = donnees.getRobots();
        Incendie[] incendies = donnees.getIncendies();

        // Sauvegarde des positions initiales des robots
        robotPositionsLignes = new int[robots.length];
        robotPositionsColonnes = new int[robots.length];
        for (int i = 0; i < robots.length; i++) {
            robotPositionsLignes[i] = robots[i].getLigne();
            robotPositionsColonnes[i] = robots[i].getColonne();
        }

        // Sauvegarde des quantités d'eau nécessaires des incendies
        incendieQuantites = new int[incendies.length];
        for (int i = 0; i < incendies.length; i++) {
            incendieQuantites[i] = incendies[i].getLitres();
        }
    }


    public void dessinerCarte() {
        gui.reset(); 
        Carte carte = donnees.getCarte();
        int lin = carte.getNbLignes();
        int col = carte.getNbColonnes();
        Incendie[] incendies = donnees.getIncendies();
        Robot[] robots = donnees.getRobots();
    
        int tailleCaseX = 1000 / col;  
        int tailleCaseY = 1000 / lin;  

        int decalageX = tailleCaseX / 2;
        int decalageY = tailleCaseY / 2;

        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                Case caseActuelle = carte.getCase(i, j);
                Color couleur = Color.WHITE; // Couleur par défaut
                    gui.addGraphicalElement(new Rectangle(
                        j * tailleCaseX + decalageX,  
                        i * tailleCaseY + decalageY, 
                        Color.BLACK, couleur, tailleCaseX 
                    ));
                    gui.addGraphicalElement(new ImageElement(
                        j * tailleCaseX,  
                        i * tailleCaseY,  
                        "src/ressources/" + caseActuelle.getNature().toString() +".jpg",    //va chercher la texture dans /src/ressources
                        tailleCaseX,                    
                        tailleCaseY,                    
                        null                            
                    ));
            }
        }

        // Draw fires
        for (Incendie inc : incendies) {
            if (inc.getLitres() != 0){
            Case pos = inc.getPosition();
            int ipos = pos.getLigne();
            int jpos = pos.getColonne();
            gui.addGraphicalElement(new ImageElement(
                        jpos * tailleCaseX,  
                        ipos * tailleCaseY, 
                        "src/ressources/" + pos.getNature().toString() +"feu.jpg",     
                        tailleCaseX,                  
                        tailleCaseY,                    
                        null                            
                    ));
        }
        }

        // Draw robots
        for (Robot rob : robots) {
            int irpos = rob.getLigne();
            int jrpos = rob.getColonne();
                gui.addGraphicalElement(new ImageElement(
                            jrpos * tailleCaseX,  
                            irpos * tailleCaseY, 
                            "src/ressources/" + rob.toString() + carte.getCase(rob.getLigne(), rob.getColonne()).getNature().toString() +".jpg",     
                            tailleCaseX,                  
                            tailleCaseY,                    
                            null                            
                        ));

        }
    }

    private void InitialiseStructureEvenement() {
        Robot[] robots = donnees.getRobots();
        Queue<Evenement>[] tableauxFilesEvenements = new LinkedList[robots.length];

        for (int i = 0; i < robots.length; i++) {
            tableauxFilesEvenements[i] = new LinkedList<>();  
        }

        this.tableauxEvenements = tableauxFilesEvenements;
    }

    public void ajouteEvenement(Evenement evenement) {
    if (this.tableauxEvenements == null) {
        System.out.println("Erreur : tableauxEvenements n'est pas initialisé !");
        return;
    }
    
    int robotId = evenement.robot.getId();
    Queue<Evenement> robotQueue = tableauxEvenements[robotId];

    if (!robotQueue.isEmpty()) {
        Evenement lastEvent = ((LinkedList<Evenement>) robotQueue).getLast();
        int newEventTime = lastEvent.getDuree() + evenement.getDuree();
        evenement.setDuree(newEventTime);
    }
    robotQueue.add(evenement);
    if (!evenementsOriginaux.contains(evenement)) {
        evenementsOriginaux.add(evenement);
    }
}

    public boolean robotEstLibre(Robot robot) {
        if (this.tableauxEvenements == null) {
            throw new IllegalArgumentException("Erreur : tableauxEvenements n'est pas initialisé !");
        }
        
        int robotId = robot.getId();
        Queue<Evenement> robotQueue = tableauxEvenements[robotId];
    
        return robotQueue.isEmpty();
    }


    private void incrementeDate() {
    if (this.tableauxEvenements == null) {
        System.out.println("Erreur : tableauxEvenements n'est pas initialisé !");
        return;
    }

    for (int i = 0; i < tableauxEvenements.length; i++) {
        Queue<Evenement> robotQueue = tableauxEvenements[i];

        
        while (!robotQueue.isEmpty()) {
            Evenement event = robotQueue.peek(); 
            int eventTime = event.getDuree(); 
            
            if (date >= eventTime) { 
                robotQueue.poll(); 
                event.execute(); 
            } else {
                break; // No more events to execute
            }
        }
    }
   
    date++;  
}

    

    public boolean simulationTerminee() {
        for (Queue<Evenement> fileEvenements : this.tableauxEvenements) {
            if (!fileEvenements.isEmpty()) {
                return false;
            }
        }
        return true;    
    }

    @Override
    public void next() {
        incrementeDate();
        dessinerCarte();
    }
    
@Override
public void restart() {
    Robot[] robots = donnees.getRobots();
    Incendie[] incendies = donnees.getIncendies();

    // Réinitialiser les positions des robots et les quantités d'eau des incendies
    for (int i = 0; i < robots.length; i++) {
        robots[i].setLigne(robotPositionsLignes[i]);
        robots[i].setColonne(robotPositionsColonnes[i]);
    }

    for (int i = 0; i < incendies.length; i++) {
        incendies[i].setLitres(incendieQuantites[i]);
    }

    // Clear event queues
    for (Queue<Evenement> file : tableauxEvenements) {
        file.clear();
    }

    // Réinitialiser les événements en utilisant une copie de evenementsOriginaux
    for (Evenement evt : new ArrayList<>(evenementsOriginaux)) {
        evt.resetDuree(); // Reset each event's duration
        this.ajouteEvenement(evt); // Re-add to the queue
    }

    date = 0; // Réinitialiser la date de simulation
    this.startTime = System.currentTimeMillis(); // Reset start time
    dessinerCarte(); // Redraw the initial state
}
}