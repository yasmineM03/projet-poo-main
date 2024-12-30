package robots;

import carte.*;
import simulation.*;

/**
 * La classe Roue représente un robot équipé de roues qui peut se déplacer
 * rapidement sur des terrains libres et des habitats. Ce robot possède un
 * réservoir d'eau pour éteindre les incendies.
 */
public class Roue extends Robot { 

    /**
     * Constructeur de la classe Roue.
     *
     * @param ligne       La ligne de la position initiale du robot.
     * @param colonne     La colonne de la position initiale du robot.
     * @param vitesse     La vitesse du robot en km/h.
     * @param id          L'identifiant unique du robot.
     * @param carte       La carte sur laquelle le robot évolue.
     * @param classeRobot La classe du robot.
     */
    public Roue(int ligne, int colonne, double vitesse, int id, Carte carte, classeRobot classeRobot) {
        super(ligne, colonne, vitesse, id, carte, classeRobot);
        this.maxReservoir = 5000;
        this.reservoir = 5000;
    }

    /**
     * Renvoie la vitesse du robot. La vitesse reste constante sur tous les terrains
     * où il peut se déplacer.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot en km/h.
     */
    @Override
    public double getVitesse(NatureTerrain natureTerrain) {
        return this.vitesse;
    }

    /**
     * Déplace le robot dans une direction spécifiée.
     *
     * @param dir La direction dans laquelle le robot doit se déplacer.
     * @return Un événement de déplacement qui décrit ce mouvement.
     * @throws IllegalArgumentException Si aucune case voisine n'existe dans cette direction
     *                                  ou si le terrain n'est pas un terrain libre ou un habitat.
     */
    @Override
    public Evenement deplacer(Direction dir) {
        Case caseRobot = this.carte.getCase(this.ligne, this.colonne);

        if (!this.carte.voisinExiste(caseRobot, dir)) {
            throw new IllegalArgumentException("Aucune case voisine n'existe dans cette direction : " + dir);
        }

        Case caseDarrivee = this.carte.getVoisin(caseRobot, dir);

        if (caseDarrivee.getNature() != NatureTerrain.HABITAT &&
            caseDarrivee.getNature() != NatureTerrain.TERRAIN_LIBRE) {
            throw new IllegalArgumentException("Le robot ne peut se déplacer que sur un terrain libre ou un habitat. Nature trouvée : " + caseDarrivee.getNature());
        }

        double vitesseMparSFloat = (int) (this.getVitesse(caseRobot.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        int dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;

        this.ligne = caseDarrivee.getLigne();
        this.colonne = caseDarrivee.getColonne();
        return new EvenementDeplacement(this, dureeDeplacement, caseDarrivee);
    }

    /**
     * Calcule la durée de déplacement d'une case de départ vers une direction donnée.
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

        if (caseDarrivee.getNature() != NatureTerrain.HABITAT &&
            caseDarrivee.getNature() != NatureTerrain.TERRAIN_LIBRE) {
            return dureeDeplacement;
        }

        double vitesseMparSFloat = (int) (this.getVitesse(caseDepart.getNature()) + this.getVitesse(caseDarrivee.getNature())) / (2 * 3.6);
        int vitesseMparS = (int) vitesseMparSFloat;
        dureeDeplacement = (this.carte.getTailleCases() / 100) / vitesseMparS;
        return dureeDeplacement;
    }

    /**
     * Remplit le réservoir d'eau du robot si une case voisine contient de l'eau.
     *
     * @param caseActuelle La case actuelle où le robot effectue le remplissage.
     * @return Un événement de remplissage avec les détails de l'opération.
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
     * Éteint un incendie en utilisant l'eau du réservoir.
     *
     * @param incendie L'incendie à éteindre.
     * @return Un événement d'extinction avec la quantité d'eau utilisée.
     */
    @Override
    public Evenement eteindre(Incendie incendie) {
        int quantiteVersee = Math.min(incendie.getLitres(), this.reservoir);
        int nombreVersements = quantiteVersee / 100;
        int dureeExtinction = nombreVersements * 5 / 100;
        return new EvenementExtinction(this, dureeExtinction, incendie, quantiteVersee);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères du robot.
     *
     * @return "ROUE"
     */
    @Override
    public String toString() {
        return "ROUE";
    }
}
