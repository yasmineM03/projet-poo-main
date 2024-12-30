package simulation;

import carte.*;
import robots.*;

/**
 * La classe EvenementDeplacement représente un événement de déplacement d'un robot
 * d'une case à une autre sur la carte lors de la simulation.
 * Elle hérite de la classe abstraite Evenement.
 */
public class EvenementDeplacement extends Evenement {

    /** La case où le robot doit se déplacer. */
    protected Case caseArrivee;

    /**
     * Constructeur de la classe EvenementDeplacement.
     * Initialise un événement de déplacement avec le robot, la durée du déplacement,
     * et la case de destination.
     *
     * @param robot           Le robot qui se déplace.
     * @param dureeDeplacement La durée du déplacement en unités de temps.
     * @param caseArrivee     La case de destination où le robot doit se rendre.
     */
    public EvenementDeplacement(Robot robot, int dureeDeplacement, Case caseArrivee) {
        super(robot, dureeDeplacement);
        this.caseArrivee = caseArrivee;
    }

    /**
     * Exécute l'événement de déplacement.
     * Déplace le robot à la case de destination en mettant à jour sa position
     * (ligne et colonne) sur la carte.
     */
    public void execute() {
        this.robot.setLigne(this.caseArrivee.getLigne());
        this.robot.setColonne(this.caseArrivee.getColonne());
    }
}
