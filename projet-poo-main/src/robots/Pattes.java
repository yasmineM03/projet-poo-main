package robots;

import carte.*;
import simulation.*;

/**
 * La classe Pattes représente un robot qui se déplace sur différents terrains
 * en utilisant la marche, avec certaines limitations sur les types de terrains traversables.
 */
public class Pattes extends Robot {

    /**
     * Constructeur de la classe Pattes.
     *
     * @param ligne       La ligne de la position initiale du robot.
     * @param colonne     La colonne de la position initiale du robot.
     * @param id          L'identifiant unique du robot.
     * @param carte       La carte sur laquelle le robot évolue.
     * @param classeRobot La classe du robot.
     */
    public Pattes(int ligne, int colonne, int id, Carte carte, classeRobot classeRobot) {
        super(ligne, colonne, 30, id, carte, classeRobot);
        this.reservoir = 999999;
        this.maxReservoir = 999999; // Ne se vide jamais
    }

    /**
     * Renvoie la vitesse du robot en fonction du type de terrain.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot, 10 km/h si le terrain est rocheux, ou la vitesse par défaut.
     */
    @Override
    public double getVitesse(NatureTerrain natureTerrain) {
        if (natureTerrain == NatureTerrain.ROCHE) {
            return 10;
        }
        return this.vitesse;
    }

    /**
     * Déplace le robot dans une direction spécifiée.
     *
     * @param dir La direction dans laquelle le robot doit se déplacer.
     * @return Un événement de déplacement qui décrit ce mouvement.
     * @throws IllegalArgumentException Si la case voisine n'existe pas ou si le robot ne peut pas se déplacer sur la case d'arrivée.
     */
    @Override
    public Evenement deplacer(Direction dir) {
        Case caseRobot = new Case(this.ligne, this.colonne, NatureTerrain.ROCHE);

        if (!this.carte.voisinExiste(caseRobot, dir)) {
            throw new IllegalArgumentException("Aucune case voisine n'existe dans cette direction : " + dir);
        }

        Case caseDarrivee = this.carte.getVoisin(caseRobot, dir);

        if (caseDarrivee.getNature() == NatureTerrain.EAU) {
            throw new IllegalArgumentException("Le robot ne peut se déplacer que sur un terrain libre ou un habitat. Nature trouvée : " + caseDarrivee.getNature());
        }

        double vitesseMparSFloat = (int) (this.getVitesse(caseRobot.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        int dureeDeplacement = (this.carte.getTailleCases() / 1000) / vitesseMparS;
        this.ligne = caseDarrivee.getLigne();
        this.colonne = caseDarrivee.getColonne();
        return new EvenementDeplacement(this, dureeDeplacement, caseDarrivee);
    }

    /**
     * Calcule la durée nécessaire pour déplacer le robot d'une case de départ dans une direction donnée.
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

        if (caseDarrivee.getNature() == NatureTerrain.EAU) {
            return dureeDeplacement;
        }

        double vitesseMparSFloat = (int) (this.getVitesse(caseDepart.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
        return dureeDeplacement;
    }

    /**
     * Remplit le réservoir du robot si une case d'eau est voisine.
     *
     * @param caseActuelle La case actuelle du robot.
     * @return Un événement de remplissage avec la durée de remplissage.
     * @throws IllegalArgumentException Si aucune case d'eau voisine n'est trouvée.
     */
    @Override
    public Evenement remplir(Case caseActuelle) {
        Case caseRobot = this.carte.getCase(this.ligne, this.colonne);

        Case caseNord = this.carte.getVoisin(caseRobot, Direction.NORD);
        Case caseSud = this.carte.getVoisin(caseRobot, Direction.SUD);
        Case caseEst = this.carte.getVoisin(caseRobot, Direction.EST);
        Case caseOuest = this.carte.getVoisin(caseRobot, Direction.OUEST);

        if ((caseNord != null && caseNord.getNature() == NatureTerrain.EAU) ||
            (caseSud != null && caseSud.getNature() == NatureTerrain.EAU) ||
            (caseEst != null && caseEst.getNature() == NatureTerrain.EAU) ||
            (caseOuest != null && caseOuest.getNature() == NatureTerrain.EAU)) {
            int dureeRemplissage = 18;
            this.reservoir = this.maxReservoir;
            return new EvenementRemplissage(this, dureeRemplissage, this.reservoir);
        } else {
            throw new IllegalArgumentException("Aucune case d'eau voisine trouvée.");
        }
    }

    /**
     * Éteint un incendie en utilisant l'eau du réservoir du robot.
     *
     * @param incendie L'incendie à éteindre.
     * @return Un événement d'extinction avec la durée et la quantité d'eau utilisée.
     */
    @Override
    public Evenement eteindre(Incendie incendie) {
        int quantiteVersee = Math.min(incendie.getLitres(), this.reservoir);
        int nombreVersements = quantiteVersee / 10;
        int dureeExtinction = nombreVersements * 1 / 100;
        return new EvenementExtinction(this, dureeExtinction, incendie, quantiteVersee);
    }

    /**
     * Retourne une représentation textuelle du robot.
     *
     * @return "PATTES", le type de robot.
     */
    @Override
    public String toString() {
        return "PATTES";
    }
}
