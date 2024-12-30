package simulation;

import robots.*;

/**
 * La classe EvenementRemplissage représente un événement où un robot remplit son réservoir d'eau.
 * Cet événement est caractérisé par la durée de l'opération et la quantité d'eau avec laquelle le robot est rempli.
 */
public class EvenementRemplissage extends Evenement {
    /** Quantité d'eau que le robot va utiliser pour remplir son réservoir. */
    int quantite;

    /**
     * Constructeur qui initialise un événement de remplissage pour un robot.
     *
     * @param robot               Le robot qui effectue le remplissage.
     * @param duree               La durée de l'opération de remplissage.
     * @param quantiteRemplissage La quantité d'eau avec laquelle le réservoir du robot sera rempli.
     */
    public EvenementRemplissage(Robot robot, int duree, int quantiteRemplissage) {
        super(robot, duree);
        this.quantite = quantiteRemplissage;
    }

    /**
     * Exécute l'événement de remplissage. 
     * Remplit le réservoir du robot avec la quantité spécifiée.
     */
    @Override
    public void execute() {
        this.robot.setReservoir(this.quantite);
    }
}
