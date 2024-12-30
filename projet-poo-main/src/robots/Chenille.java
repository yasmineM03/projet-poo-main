package robots;

import carte.*;
import simulation.*;

/**
 * La classe Chenille représente un robot de type chenille qui se déplace sur différents
 * terrains avec des vitesses ajustées selon la nature du terrain.
 */
public class Chenille extends Robot {

    /**
     * Constructeur de la classe Chenille.
     *
     * @param ligne       La ligne de la position initiale du robot.
     * @param colonne     La colonne de la position initiale du robot.
     * @param vitesse     La vitesse initiale du robot, limitée à 80 km/h.
     * @param id          L'identifiant unique du robot.
     * @param carte       La carte sur laquelle évolue le robot.
     * @param classeRobot La classe du robot.
     */
    public Chenille(int ligne, int colonne, double vitesse, int id, Carte carte, classeRobot classeRobot) {
        super(ligne, colonne, Math.min(vitesse, 80), id, carte, classeRobot);
        this.reservoir = 0; // Initialisation du réservoir
    }

    /**
     * Renvoie la vitesse du robot en fonction de la nature du terrain.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot, réduite de moitié si le terrain est une forêt,
     *         sinon la vitesse reste inchangée.
     */
    @Override
    public double getVitesse(NatureTerrain natureTerrain) {
        if (natureTerrain == NatureTerrain.FORET) {
            return this.vitesse * 0.5;
        }
        return this.vitesse;
    }

    /**
     * Déplace le robot dans une direction spécifiée.
     *
     * @param dir La direction dans laquelle le robot doit se déplacer.
     * @return Un événement de déplacement qui décrit ce mouvement.
     * @throws IllegalArgumentException Si la case voisine n'existe pas ou si la nature
     *                                  du terrain de la case d'arrivée est de type eau ou roche.
     */
    @Override
    public Evenement deplacer(Direction dir) {
        Case caseRobot = new Case(this.ligne, this.colonne, NatureTerrain.ROCHE);

        if (!this.carte.voisinExiste(caseRobot, dir)) {
            throw new IllegalArgumentException("Aucune case voisine n'existe dans cette direction : " + dir);
        }

        Case caseDarrivee = this.carte.getVoisin(caseRobot, dir);

        if (caseDarrivee.getNature() == NatureTerrain.EAU || caseDarrivee.getNature() == NatureTerrain.ROCHE) {
            throw new IllegalArgumentException("Le robot ne peut se déplacer que sur un terrain libre ou "
                    + "un habitat. Nature trouvée : " + caseDarrivee.getNature());
        }

        // Calculer la durée de déplacement
        double vitesseMparSFloat = (int) (this.getVitesse(caseRobot.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        int dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
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

        if (caseDarrivee.getNature() == NatureTerrain.EAU || caseDarrivee.getNature() == NatureTerrain.ROCHE) {
            return dureeDeplacement;
        }

        double vitesseMparSFloat = (int) (this.getVitesse(caseDepart.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
        return dureeDeplacement;
    }

    /**
     * Remplit le réservoir du robot en utilisant une case d'eau adjacente.
     *
     * @param caseEau La case contenant de l'eau.
     * @return Un événement de remplissage avec la durée et la quantité d'eau ajoutée.
     * @throws IllegalArgumentException Si le robot n'est pas voisin de la case d'eau
     *                                  ou si la case spécifiée ne contient pas d'eau.
     */
    @Override
    public Evenement remplir(Case caseEau) {
        Case caseRobot = new Case(this.ligne, this.colonne, NatureTerrain.ROCHE);

        if (!caseRobot.estVoisine(caseEau)) {
            throw new IllegalArgumentException("Le robot n'est pas voisin de cette case eau");
        }

        if (caseEau.getNature() != NatureTerrain.EAU) {
            throw new IllegalArgumentException("La case considérée ne contient pas d'eau");
        }

        int dureeRemplissage = 300;
        int quantiteRemplissage = 2000;
        return new EvenementRemplissage(this, dureeRemplissage, quantiteRemplissage);
    }

    /**
     * Éteint un incendie en versant de l'eau.
     *
     * @param incendie L'incendie à éteindre.
     * @return Un événement d'extinction avec la durée et la quantité d'eau utilisée.
     */
    @Override
    public Evenement eteindre(Incendie incendie) {
        int quantiteVersee = Math.min(incendie.getLitres(), this.reservoir);
        int nombreVersements = quantiteVersee / 100;
        int dureeExtinction = nombreVersements * 8 / 100;
        return new EvenementExtinction(this, dureeExtinction, incendie, quantiteVersee);
    }

    /**
     * Retourne une représentation textuelle du robot.
     *
     * @return "CHENILLE", le type de robot.
     */
    @Override
    public String toString() {
        return "CHENILLE";
    }
}
