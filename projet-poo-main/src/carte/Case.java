package carte;

/**
 * La classe Case représente une case sur la carte, identifiée par ses coordonnées
 * (ligne et colonne) et la nature du terrain qu'elle contient.
 */
public class Case {
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    /**
     * Constructeur de la classe Case.
     * Initialise une case avec les coordonnées spécifiées et la nature du terrain.
     *
     * @param ligne La ligne de la case.
     * @param colonne La colonne de la case.
     * @param nature La nature du terrain de la case (par exemple, terrain libre, eau, etc.).
     */
    public Case(int ligne, int colonne, NatureTerrain nature) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }

    /**
     * Retourne la ligne de la case.
     *
     * @return La ligne de la case.
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Retourne la colonne de la case.
     *
     * @return La colonne de la case.
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des coordonnées de la case.
     * La chaîne est au format "ligne/colonne".
     *
     * @return La chaîne de caractères représentant les coordonnées de la case.
     */
    public String toString(){
        return this.ligne + "," + this.colonne;  
    }

    /**
     * Retourne la nature du terrain de la case.
     *
     * @return La nature du terrain de la case.
     */
    public NatureTerrain getNature() {
        return nature;
    }

    /**
     * Vérifie si la case spécifiée est voisine de la case courante.
     * Deux cases sont voisines si elles sont adjacentes (nord, sud, est, ou ouest).
     *
     * @param autreCase La case à comparer.
     * @return true si l'autre case est voisine, false sinon.
     */
    public boolean estVoisine(Case autreCase) {
        int diffX = Math.abs(this.ligne - autreCase.ligne);
        int diffY = Math.abs(this.colonne - autreCase.colonne);
        return (diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1);
    }

    /**
     * Vérifie si la case spécifiée a les mêmes coordonnées que la case courante.
     *
     * @param autreCase La case à comparer.
     * @return true si les deux cases ont les mêmes coordonnées, false sinon.
     */
    public boolean estSuperposee(Case autreCase) {
        return (this.ligne == autreCase.ligne && this.colonne == autreCase.colonne);
    }

    /**
     * Vérifie si les coordonnées de la case courante sont identiques à celles de la case spécifiée.
     *
     * @param src La case à comparer.
     * @return true si les coordonnées de la case source sont identiques à celles de la case courante, false sinon.
     */
    public boolean isSame(Case src){
        int i = src.getLigne();
        int j = src.getColonne();
        return (i == this.ligne && j == this.colonne);
    }
}
