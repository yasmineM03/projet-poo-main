package plus_court_chemin;

import carte.*;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Arrays;
import robots.*;

/**
 * La classe aetoile implémente l'algorithme de recherche A* pour trouver le chemin
 * le plus court dans une carte, en tenant compte des obstacles naturels et des différents
 * types de robots.
 */
public class aetoile {

    private int time;
    private Stack<Direction> chemin; 
    
    /**
     * Constructeur par défaut de la classe aetoile.
     * Initialise le temps à -1 et le chemin à null.
     */
    public aetoile(){
        this.time = -1;
        this.chemin = null;
    }

    /**
     * Retourne le temps total nécessaire pour parcourir le chemin trouvé.
     *
     * @return Le temps total.
     */
    public int getTime(){
        return this.time;
    }

    /**
     * Retourne la pile des directions représentant le chemin trouvé.
     *
     * @return La pile des directions du chemin.
     */
    public Stack<Direction> getChemin(){
        return this.chemin;
    }

    /**
     * La classe interne node représente un nœud dans la grille de recherche A*.
     * Elle stocke le parent du nœud et les valeurs des fonctions de coût f, g, et h.
     */
    public class node {
        public Case parent; 
        public double f, g, h;
        public int temps;

        /**
         * Constructeur par défaut qui initialise le nœud avec des valeurs par défaut.
         */
        node(){
            parent = new Case(-1, -1, NatureTerrain.ROCHE);
            f = -1;
            g = -1;
            h = -1;
            temps = 0; 
        }

        /**
         * Constructeur qui initialise un nœud avec les valeurs spécifiées.
         *
         * @param parent La case parent de ce nœud.
         * @param f      Valeur de la fonction de coût totale.
         * @param g      Coût du chemin depuis le point de départ.
         * @param h      Coût heuristique estimé pour atteindre la destination.
         * @param temps  Temps nécessaire pour atteindre ce nœud.
         */
        public node(Case parent, double f, double g, double h, int temps){
            this.parent = parent;
            this.f = f;
            this.g = g;
            this.h = h;
            this.temps = temps; 
        }
    }

    /**
     * La classe Details contient les informations concernant une case : 
     * sa valeur de coût et ses coordonnées (i, j).
     */
    public static class Details {
        double value;
        int i;
        int j;

        /**
         * Constructeur qui initialise les détails d'une case avec les valeurs spécifiées.
         *
         * @param value Valeur de coût de la case.
         * @param i     Coordonnée i (ligne) de la case.
         * @param j     Coordonnée j (colonne) de la case.
         */
        public Details(double value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return i + "/ " + j;
        }
    }

    /**
     * Calcule la valeur heuristique (h) entre deux cases.
     * La distance utilisée ici est la distance euclidienne.
     *
     * @param src La case de départ.
     * @param dst La case de destination.
     * @return La distance heuristique (h) entre les deux cases.
     */
    double calculeValeurH(Case src, Case dst){
        return Math.sqrt(Math.pow((src.getLigne() - dst.getLigne()), 2.0) + Math.pow((src.getColonne() - dst.getColonne()), 2.0));
    }

    /**
     * Trace le chemin en utilisant les détails des nœuds calculés par l'algorithme A*.
     * Le chemin est représenté par une pile de directions (NORD, SUD, EST, OUEST).
     *
     * @param nodeDetails Tableau des nœuds calculés lors de la recherche A*.
     * @param src         La case de départ.
     * @param dst         La case de destination.
     * @param carte       La carte utilisée pour obtenir les cases.
     */
    void tracePath(node[][] nodeDetails, Case src, Case dst, Carte carte ){
        Stack<Direction> path = new Stack<>();
        int col = dst.getColonne();
        int lig = dst.getLigne();
        Case nextNode = nodeDetails[lig][col].parent; 
        Stack<Case> path1 = new Stack<>();
        do {
            nextNode = nodeDetails[lig][col].parent;
            if (lig == nextNode.getLigne() - 1){
                path.push(Direction.NORD);
            } else if (lig == nextNode.getLigne() + 1){
                path.push(Direction.SUD);
            } else if (col == nextNode.getColonne() - 1){
                path.push(Direction.OUEST);
            } else if (col == nextNode.getColonne() + 1){
                path.push(Direction.EST);
            } else {
                System.err.println("err");
            }
            path1.push(carte.getCase(lig, col));
            col = nextNode.getColonne();
            lig = nextNode.getLigne();
        } while (nodeDetails[lig][col].parent != nextNode);
        this.chemin = path; 
    }

    /**
     * Effectue la recherche A* pour trouver le chemin le plus court entre deux cases.
     * Cette méthode gère également les déplacements des robots, y compris les drones.
     *
     * @param carte La carte sur laquelle effectuer la recherche.
     * @param dst   La case de destination.
     * @param robot Le robot qui effectue la recherche.
     */
    public void aEtoileSearch(Carte carte, Case dst, Robot robot){
        // Traitement spécifique pour le robot de type DRONE
        // if(robot.getClasseRobot() == classeRobot.DRONE){
        //     Stack<Direction> path = new Stack<>();
        //     int temps = robot.deplacerenPartantdUneCase(dst, Direction.NORD);
        //     int i = robot.getLigne();
        //     int j = robot.getColonne();
        //     if (i < dst.getLigne()){
        //         do {
        //             path.push(Direction.SUD); 
        //             this.time += temps;
        //             i++;
        //         } while (i != dst.getLigne());
        //     }
        //     if (i > dst.getLigne()){
        //         do {
        //             path.push(Direction.NORD); 
        //             this.time += temps;
        //             i++;
        //         } while (i != dst.getLigne());
        //     }
        //     if (j < dst.getColonne()){
        //         do {
        //             path.push(Direction.EST); 
        //             this.time += temps;
        //             j++;
        //         } while (j != dst.getColonne());
        //     }
        //     if (j > dst.getColonne()){
        //         do {
        //             path.push(Direction.OUEST); 
        //             this.time += temps;
        //             j++;
        //         } while (j != dst.getLigne());
        //     }
        //     this.chemin = path; 
        //     this.time = 0;
        //     return;
        // }
        boolean[][] closedList = new boolean[carte.getNbLignes()][carte.getNbColonnes()];
        node[][] nodeDetails = new node[carte.getNbLignes()][carte.getNbColonnes()];
        for (int row = 0; row < carte.getNbLignes(); row++) {
         Arrays.fill(nodeDetails[row], null);  // Initially set all elements to null
        }

        Case src = carte.getCase(robot.getLigne(), robot.getColonne()); 
        int i = src.getLigne();
        int j = src.getColonne();
        nodeDetails[i][j] = new node();
        nodeDetails[i][j].f = 0.0;
        nodeDetails[i][j].g = 0.0;
        nodeDetails[i][j].h = 0.0;
        nodeDetails[i][j].parent = carte.getCase(i, j);

        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.value - o2.value));
        openList.add(new Details(0.0, i, j));

        while (!openList.isEmpty()) {
            Details p = openList.peek();
            i = p.i;
            j = p.j;
            openList.poll();
            closedList[i][j] = true;
            Case position = carte.getCase(i, j);
            for (Direction direction : Direction.values()){
                if(carte.voisinExiste(position, direction)) {
                    Case voisin = carte.getVoisin(position, direction);
                    if(nodeDetails[voisin.getLigne()] == null){ 
                        nodeDetails[voisin.getLigne()] = new node[carte.getNbColonnes()]; 
                    }
                    if (nodeDetails[voisin.getLigne()][voisin.getColonne()] == null) {
                        nodeDetails[voisin.getLigne()][voisin.getColonne()] = new node();
                    }
                    int temps = robot.deplacerenPartantdUneCase(position, direction);
                    if(voisin.isSame(dst)){
                        nodeDetails[voisin.getLigne()][voisin.getColonne()].parent = carte.getCase(i, j);
                        this.time = nodeDetails[position.getLigne()][position.getColonne()].temps + temps;
                        tracePath(nodeDetails, src, dst, carte);
                        return; 
                    } else if (!closedList[voisin.getLigne()][voisin.getColonne()] && temps != -1) {
                        double gNew = nodeDetails[i][j].g + temps;
                        double hNew = calculeValeurH(voisin, dst);
                        double fNew = gNew + hNew;
                        if (nodeDetails[voisin.getLigne()][voisin.getColonne()].f == -1 || nodeDetails[voisin.getLigne()][voisin.getColonne()].f > fNew){
                            openList.add(new Details(fNew, voisin.getLigne(), voisin.getColonne()));
                            nodeDetails[voisin.getLigne()][voisin.getColonne()].f = fNew;
                            nodeDetails[voisin.getLigne()][voisin.getColonne()].g = gNew;
                            nodeDetails[voisin.getLigne()][voisin.getColonne()].h = hNew;
                            nodeDetails[voisin.getLigne()][voisin.getColonne()].parent = carte.getCase(i, j);
                        }
                    }
                }
            }
        }
    }
}
