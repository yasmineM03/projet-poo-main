package simulation;
import robots.*;

/**
 * La classe abstraite Evenement représente un événement qui se produit
 * lors de la simulation d'une intervention de robots. Chaque événement est associé
 * à un robot et possède une durée.
 */
public abstract class Evenement {

    /** Le robot associé à cet événement. */
    protected Robot robot;

    /** La durée restante de l'événement. */
    protected int duree;

    /** La durée initiale de l'événement. */
    protected int initialDuree;

    /**
     * Constructeur de la classe Evenement.
     * Initialise l'événement avec un robot et une durée spécifiée.
     *
     * @param robot Le robot associé à cet événement.
     * @param duree La durée initiale de l'événement.
     */
    protected Evenement(Robot robot, int duree) {
        this.robot = robot;
        this.duree = duree;
        this.initialDuree = duree;
    }

    /**
     * Retourne la durée restante de l'événement.
     *
     * @return La durée restante en unités de temps.
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Définit la durée restante de l'événement.
     *
     * @param duree La nouvelle durée en unités de temps.
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Réinitialise la durée de l'événement à sa valeur initiale.
     */
    public void resetDuree() {
        this.duree = this.initialDuree;
    }

    /**
     * Exécute l'action associée à cet événement.
     * La méthode doit être implémentée par les sous-classes.
     */
    public abstract void execute();
}
