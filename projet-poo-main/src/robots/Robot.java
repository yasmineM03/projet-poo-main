package robots;

import carte.*;
import simulation.*;

/**
 * La classe abstraite Robot représente un robot générique qui peut se déplacer,
 * éteindre des incendies, et interagir avec son environnement.
 */
public abstract class Robot {
    protected int ligne; // La position en ligne du robot sur la carte.
    protected int colonne; // La position en colonne du robot sur la carte.
    protected double vitesse; // La vitesse du robot en km/h.
    protected int reservoir; // La quantité d'eau actuelle dans le réservoir.
    protected int maxReservoir; // La capacité maximale du réservoir.
    protected int id; // L'identifiant unique du robot.
    protected Carte carte; // La carte sur laquelle le robot se déplace.
    protected classeRobot classeRobot; // La classe du robot.

    /**
     * Constructeur de la classe Robot.
     *
     * @param ligne       La ligne de la position initiale du robot.
     * @param colonne     La colonne de la position initiale du robot.
     * @param vitesse     La vitesse du robot en km/h.
     * @param id          L'identifiant unique du robot.
     * @param carte       La carte sur laquelle le robot évolue.
     * @param classeRobot La classe du robot.
     */
    protected Robot(int ligne, int colonne, double vitesse, int id, Carte carte, classeRobot classeRobot) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.vitesse = vitesse;
        this.id = id;
        this.carte = carte;
        this.classeRobot = classeRobot;
    }

    /**
     * Renvoie la vitesse du robot en fonction du type de terrain.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot sur ce terrain.
     */
    public abstract double getVitesse(NatureTerrain natureTerrain);

    /**
     * Déplace le robot dans une direction spécifiée.
     *
     * @param dir La direction dans laquelle le robot doit se déplacer.
     * @return Un événement de déplacement qui décrit ce mouvement.
     */
    public abstract Evenement deplacer(Direction dir);

    /**
     * Remplit le réservoir d'eau du robot.
     *
     * @param caseActuelle La case actuelle où le robot effectue le remplissage.
     * @return Un événement de remplissage avec les détails de l'opération.
     */
    public abstract Evenement remplir(Case caseActuelle);

    /**
     * Éteint un incendie en utilisant l'eau du réservoir.
     *
     * @param incendie L'incendie à éteindre.
     * @return Un événement d'extinction avec la quantité d'eau utilisée.
     */
    public abstract Evenement eteindre(Incendie incendie);

    /**
     * Calcule la durée nécessaire pour déplacer le robot d'une case de départ
     * dans une direction donnée.
     *
     * @param caseDepart La case de départ.
     * @param dir        La direction de déplacement.
     * @return La durée de déplacement si possible, sinon -1.
     */
    public abstract int deplacerenPartantdUneCase(Case caseDepart, Direction dir);

    /**
     * Renvoie la ligne actuelle du robot.
     *
     * @return La ligne actuelle du robot.
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Renvoie la colonne actuelle du robot.
     *
     * @return La colonne actuelle du robot.
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Renvoie la quantité d'eau dans le réservoir.
     *
     * @return La quantité d'eau actuelle dans le réservoir.
     */
    public int getReservoir() {
        return reservoir;
    }

    /**
     * Définit la ligne de la position actuelle du robot.
     *
     * @param ligne La nouvelle ligne de la position du robot.
     */
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    /**
     * Définit la colonne de la position actuelle du robot.
     *
     * @param colonne La nouvelle colonne de la position du robot.
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Définit la quantité d'eau dans le réservoir.
     *
     * @param reservoir La nouvelle quantité d'eau dans le réservoir.
     */
    public void setReservoir(int reservoir) {
        this.reservoir = reservoir;
    }

    /**
     * Renvoie la capacité maximale du réservoir du robot.
     *
     * @return La capacité maximale du réservoir.
     */
    public int returnMaxReservoir() {
        return this.maxReservoir;
    }

    /**
     * Renvoie l'identifiant unique du robot.
     *
     * @return L'identifiant du robot.
     */
    public int getId() {
        return id;
    }

    /**
     * Renvoie la classe du robot.
     *
     * @return La classe du robot.
     */
    public classeRobot getClasseRobot() {
        return this.classeRobot;
    }
}
