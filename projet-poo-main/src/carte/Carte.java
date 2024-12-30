package carte;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Carte représente une carte composée de cases disposées en grille,
 * qui peuvent contenir des incendies ou être adjacentes à des sources d'eau.
 */
public class Carte {
    private int nbLignes;
    private int nbColonnes;
    private int tailleCases;
    private Case[][] cases;
    private List<Incendie> incendies;  
    private List<Case> caseseau;

    /**
     * Constructeur de la classe Carte.
     * Initialise la carte avec un nombre de lignes, de colonnes et une taille de case,
     * puis crée toutes les cases comme étant des terrains libres.
     *
     * @param nbLignes Le nombre de lignes de la carte.
     * @param nbColonnes Le nombre de colonnes de la carte.
     * @param tailleCases La taille de chaque case sur la carte.
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.cases = new Case[nbLignes][nbColonnes];
        this.incendies = new ArrayList<>();
        this.caseseau = new ArrayList<>();
        
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                this.cases[i][j] = new Case(i, j, NatureTerrain.TERRAIN_LIBRE); 
            }
        }
    }

    /**
     * Ajoute un incendie à la liste des incendies sur la carte.
     *
     * @param incendie L'incendie à ajouter.
     */
    public void ajouterIncendie(Incendie incendie) {
        this.incendies.add(incendie);
    }

    /**
     * Ajoute une case contenant de l'eau à la liste des cases d'eau sur la carte.
     *
     * @param eau La case contenant de l'eau à ajouter.
     */
    public void ajouterEau(Case eau){
        this.caseseau.add(eau);
    }

    /**
     * Retourne la case située aux coordonnées spécifiées (ligne et colonne).
     *
     * @param ligne La ligne de la case.
     * @param colonne La colonne de la case.
     * @return La case située à la position donnée.
     */
    public Case getCase(int ligne, int colonne) {
        return this.cases[ligne][colonne];
    }

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes.
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes.
     */
    public int getNbColonnes() {
        return nbColonnes;
    }

    /**
     * Retourne la taille des cases de la carte.
     *
     * @return La taille des cases.
     */
    public int getTailleCases() {
        return tailleCases;
    }

    /**
     * Retourne la liste des cases contenant de l'eau.
     *
     * @return La liste des cases d'eau.
     */
    public List<Case> getCasesEau() {
        return caseseau;
    }

    /**
     * Vérifie si une case voisine existe dans la direction spécifiée.
     *
     * @param src La case source.
     * @param dir La direction dans laquelle vérifier la présence d'une case voisine.
     * @return true si la case voisine existe, false sinon.
     */
    public boolean voisinExiste(Case src, Direction dir) {
        int ligne = src.getLigne();
        int colonne = src.getColonne();

        switch (dir) {
            case NORD:
                return ligne > 0;
            case SUD:
                return ligne < nbLignes - 1;
            case EST:
                return colonne < nbColonnes - 1;
            case OUEST:
                return colonne > 0;
        }
        return false;
    }

    /**
     * Retourne la case voisine dans la direction spécifiée si elle existe,
     * sinon retourne la case source.
     *
     * @param src La case source.
     * @param dir La direction dans laquelle récupérer la case voisine.
     * @return La case voisine dans la direction spécifiée.
     */
    public Case getVoisin(Case src, Direction dir) {
        if (voisinExiste(src, dir)) {
            int ligne = src.getLigne();
            int colonne = src.getColonne();
            switch (dir) {
                case NORD:
                    return new Case(ligne - 1, colonne, cases[ligne - 1][colonne].getNature());
                case SUD:
                    return new Case(ligne + 1, colonne, cases[ligne + 1][colonne].getNature());
                case EST:
                    return new Case(ligne, colonne + 1, cases[ligne][colonne + 1].getNature());
                case OUEST:
                    return new Case(ligne, colonne - 1, cases[ligne][colonne - 1].getNature());
            }
        }
        return src;
    }

    /**
     * Modifie la case située aux coordonnées spécifiées par la nouvelle case.
     *
     * @param nouvelleCase La nouvelle case à insérer.
     * @param ligne La ligne de la case à modifier.
     * @param colonne La colonne de la case à modifier.
     * @throws IndexOutOfBoundsException Si les coordonnées sont en dehors des limites de la carte.
     */
    public void setCase(Case nouvelleCase, int ligne, int colonne) {
        if (ligne >= 0 && ligne < nbLignes && colonne >= 0 && colonne < nbColonnes) {
            cases[ligne][colonne] = nouvelleCase;
        } else {
            throw new IndexOutOfBoundsException("Position (" + ligne + ", " + colonne + ") en dehors des limites de la carte.");
        }
    }

    /**
     * Retourne la liste des incendies présents sur la carte.
     *
     * @return La liste des incendies.
     */
    public List<Incendie> getIncendies() {
        return incendies;
    }

    /**
     * Vérifie si un incendie est présent sur la case spécifiée.
     *
     * @param c La case à vérifier.
     * @return L'incendie présent sur la case, ou null si aucun incendie n'est présent.
     */
    public Incendie incendieSurCase(Case c) {
        for (Incendie incendie : incendies) {
            if (incendie.getLigne() == c.getLigne() && incendie.getColonne() == c.getColonne()) {
                return incendie;
            }
        }
        return null;
    }
}
