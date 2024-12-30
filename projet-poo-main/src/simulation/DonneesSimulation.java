package simulation;
import carte.*;
import robots.*;

    /**
    * La classe DonneesSimulation regroupe toutes les données nécessaires
    * pour simuler une intervention de robots pompiers sur une carte avec des incendies.
    * Elle contient une carte, un tableau d'incendies et un tableau de robots.
    */
    public class DonneesSimulation {
    /** La carte sur laquelle la simulation se déroule. */
    private Carte carte;

    /** Le tableau des incendies présents sur la carte. */
    private Incendie[] incendies;

    /** Le tableau des robots qui interviennent sur les incendies. */
    private Robot[] robots;         

    /**
     * Constructeur de la classe DonneesSimulation.
     * Initialise la carte, les incendies et les robots pour la simulation.
     *
     * @param carte    La carte de la simulation.
     * @param incendies Le tableau des incendies présents sur la carte.
     * @param robots   Le tableau des robots intervenant dans la simulation.
     */
    public DonneesSimulation(Carte carte, Incendie[] incendies, Robot[] robots) {
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
    }

    /**
     * Retourne la carte de la simulation.
     *
     * @return La carte sur laquelle la simulation se déroule.
     */

    public Carte getCarte() {
        return this.carte;
    }

    /**
     * Retourne le tableau des incendies présents sur la carte.
     *
     * @return Un tableau d'objets Incendie.
     */

    public Incendie[] getIncendies() {
        return this.incendies;
    }

    /**
     * Retourne le tableau des robots qui interviennent dans la simulation.
     *
     * @return Un tableau d'objets Robot.
     */

    public Robot[] getRobots() {
        return this.robots;
    }
    }
