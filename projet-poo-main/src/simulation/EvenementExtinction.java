package simulation;

import carte.*;
import robots.*;

/**
 * La classe EvenementExtinction représente un événement d'extinction d'incendie
 * par un robot lors de la simulation.
 * Elle hérite de la classe abstraite Evenement.
 */
public class EvenementExtinction extends Evenement {

    /** L'incendie que le robot doit éteindre. */
    private Incendie incendie;

    /** La quantité d'eau versée par le robot pour éteindre l'incendie. */
    private int quantiteVersement;

    /**
     * Constructeur de la classe EvenementExtinction.
     * Initialise un événement d'extinction d'incendie avec le robot, la durée de l'extinction,
     * l'incendie cible, et la quantité d'eau à verser.
     *
     * @param robot            Le robot qui effectue l'extinction.
     * @param duree            La durée de l'extinction en unités de temps.
     * @param incendie         L'incendie que le robot doit éteindre.
     * @param quantite         La quantité d'eau que le robot verse pour éteindre l'incendie.
     */
    public EvenementExtinction(Robot robot, int duree, Incendie incendie, int quantite) {
        super(robot, duree);
        this.incendie = incendie;
        this.quantiteVersement = quantite;
    }

    /**
     * Exécute l'événement d'extinction d'incendie.
     * Réduit la quantité d'eau dans le réservoir du robot et diminue
     * l'intensité de l'incendie de la quantité d'eau versée.
     * Si le robot est de type DRONE, le réservoir est complètement vidé.
     */
    public void execute() {
        if (this.robot.getClasseRobot() == classeRobot.DRONE) {
            // Vide complètement le réservoir si le robot est un drone
            this.robot.setReservoir(0);
        } else {
            // Réduit le réservoir du robot de la quantité versée
            this.robot.setReservoir(this.robot.getReservoir() - this.quantiteVersement);
        }
        // Diminue l'intensité de l'incendie
        this.incendie.setLitres(this.incendie.getLitres() - this.quantiteVersement);
    }
}
