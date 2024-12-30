package robots;

import carte.*;
import simulation.*;

/**
 * La classe Drone représente un robot capable de voler et de se déplacer avec une vitesse constante
 * sur tous les types de terrains navigables.
 */
public class Drone extends Robot {

    /**
     * Constructeur de la classe Drone.
     *
     * @param ligne       La ligne de la position initiale du drone.
     * @param colonne     La colonne de la position initiale du drone.
     * @param vitesse     La vitesse initiale du drone, limitée à 150 km/h.
     * @param id          L'identifiant unique du drone.
     * @param carte       La carte sur laquelle le drone évolue.
     * @param classeRobot La classe du robot.
     */
    public Drone(int ligne, int colonne, double vitesse, int id, Carte carte, classeRobot classeRobot) {
        super(ligne, colonne, Math.min(vitesse, 150), id, carte, classeRobot);
        this.reservoir = 10000;
        this.maxReservoir = 10000;
    }

    /**
     * Renvoie la vitesse du drone, qui reste constante sur tous les types de terrains.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse constante du drone.
     */
    @Override
    public double getVitesse(NatureTerrain natureTerrain) {
        return this.vitesse;
    }

    /**
     * Déplace le drone dans une direction spécifiée.
     *
     * @param dir La direction dans laquelle le drone doit se déplacer.
     * @return Un événement de déplacement qui décrit ce mouvement.
     * @throws IllegalArgumentException Si la case voisine n'existe pas.
     */
    @Override
    public Evenement deplacer(Direction dir) {
        // Obtenir la case actuelle du drone
        Case caseRobot = this.carte.getCase(this.ligne, this.colonne);

        if (!this.carte.voisinExiste(caseRobot, dir)) {
            throw new IllegalArgumentException("Aucune case voisine n'existe dans cette direction : " + dir);
        }

        Case caseDarrivee = this.carte.getVoisin(caseRobot, dir);

        // Calculer la durée de déplacement
        double vitesseMparSFloat = (int) (this.getVitesse(caseRobot.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        int dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
        this.ligne = caseDarrivee.getLigne();
        this.colonne = caseDarrivee.getColonne();
        return new EvenementDeplacement(this, dureeDeplacement, caseDarrivee);
    }

    /**
     * Calcule la durée nécessaire pour déplacer le drone d'une case de départ dans une direction donnée.
     *
     * @param caseDepart La case de départ.
     * @param dir        La direction de déplacement.
     * @return La durée de déplacement si possible, sinon -1.
     */
    @Override
    public int deplacerenPartantdUneCase(Case caseDepart, Direction dir) {
        int dureeDeplacement = -1;
        if (!this.carte.voisinExiste(caseDepart, dir)) {
            return dureeDeplacement;
        }

        Case caseDarrivee = this.carte.getVoisin(caseDepart, dir);

        double vitesseMparSFloat = (int) (this.getVitesse(caseDepart.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
        return dureeDeplacement;
    }

    /**
     * Remplit le réservoir du drone lorsqu'il est positionné sur une case d'eau.
     *
     * @param caseEau La case contenant de l'eau.
     * @return Un événement de remplissage avec la durée et la quantité d'eau ajoutée.
     * @throws IllegalArgumentException Si le drone n'est pas superposé à la case d'eau
     *                                  ou si la case ne contient pas d'eau.
     */
    @Override
    public Evenement remplir(Case caseEau) {
        Case caseRobot = new Case(this.ligne, this.colonne, NatureTerrain.ROCHE);

        if (!caseRobot.estSuperposee(caseEau)) {
            throw new IllegalArgumentException("Le drone n'est pas sur la case eau");
        }

        if (caseEau.getNature() != NatureTerrain.EAU) {
            throw new IllegalArgumentException("La case considérée ne contient pas d'eau");
        }

        int dureeRemplissage = 18;
        this.reservoir = 10000;
        return new EvenementRemplissage(this, dureeRemplissage, this.reservoir);
    }

    /**
     * Éteint un incendie en utilisant l'eau du réservoir du drone.
     *
     * @param incendie L'incendie à éteindre.
     * @return Un événement d'extinction avec la durée et la quantité d'eau utilisée.
     */
    @Override
    public Evenement eteindre(Incendie incendie) {
        int quantiteVersee = Math.min(incendie.getLitres(), this.reservoir);
        int dureeExtinction = 30;
        return new EvenementExtinction(this, dureeExtinction, incendie, quantiteVersee);
    }

    /**
     * Retourne une représentation textuelle du drone.
     *
     * @return "DRONE", le type de robot.
     */
    @Override
    public String toString() {
        return "DRONE";
    }
}
