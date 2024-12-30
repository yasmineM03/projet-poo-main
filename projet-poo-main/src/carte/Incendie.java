package carte;

/**
 * La classe Incendie représente un incendie sur la carte, incluant sa position et la quantité de litres nécessaires pour l'éteindre.
 * Elle permet de gérer les informations relatives à un incendie, telles que la position et la quantité d'eau requise pour l'éteindre.
 */
public class Incendie {
    private Case position;            
    private int litresNecessaires;    
    /**
     * Constructeur de la classe Incendie.
     * 
     * @param position La case représentant la position de l'incendie.
     * @param litresNecessaires La quantité d'eau nécessaire pour éteindre l'incendie.
     */
    public Incendie(Case position, int litresNecessaires) {
        this.position = position;
        this.litresNecessaires = litresNecessaires;
    }

    /**
     * Retourne la position de l'incendie.
     *
     * @return La case représentant la position de l'incendie.
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * Retourne la quantité d'eau nécessaire pour éteindre l'incendie.
     *
     * @return Le nombre de litres d'eau nécessaires.
     */
    public int getLitres() {
        return this.litresNecessaires;
    }

    /**
     * Définit la quantité d'eau nécessaire pour éteindre l'incendie.
     *
     * @param litresNecessaires La nouvelle quantité d'eau nécessaire.
     */
    public void setLitres(int litresNecessaires) {
        this.litresNecessaires = litresNecessaires;
    }

    /**
     * Retourne la ligne de la case où l'incendie est situé.
     *
     * @return Le numéro de ligne de la position de l'incendie.
     */
    public int getLigne() {
        return this.position.getLigne();
    }

    /**
     * Retourne la colonne de la case où l'incendie est situé.
     *
     * @return Le numéro de colonne de la position de l'incendie.
     */
    public int getColonne() {
        return this.position.getColonne();
    }
}
