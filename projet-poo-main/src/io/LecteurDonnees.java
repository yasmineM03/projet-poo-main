package io;

import carte.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import robots.*;
import simulation.*;

public class LecteurDonnees {

    private static Scanner scanner;

    /**
     * Lit et retourne les donnees de simulation (Carte, Incendies, Robots)
     * @param fichierDonnees nom du fichier à lire
     * @return une instance de DonneesSimulation contenant les objets créés
     */
    public static DonneesSimulation lire(String fichierDonnees)
            throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier " + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);

        // Lire et créer les objets pour la simulation
        Carte carte = lecteur.lireCarte();
        Incendie[] incendies = lecteur.lireIncendies(carte);
        Robot[] robots = lecteur.lireRobots(carte);

        scanner.close();
        System.out.println("\n == Lecture terminee");

        // Retourner une instance de DonneesSimulation
        return new DonneesSimulation(carte,incendies, robots);
    }

    // Constructeur prive
    private LecteurDonnees(String fichierDonnees) throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et retourne une Carte
     * @return une instance de Carte
     */
    private Carte lireCarte() throws DataFormatException {
    ignorerCommentaires();
    try {
        int nbLignes = scanner.nextInt();
        int nbColonnes = scanner.nextInt();
        int tailleCases = scanner.nextInt(); 
        Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);

        for (int lig = 0; lig < nbLignes; lig++) {
            for (int col = 0; col < nbColonnes; col++) {
                lireCase(carte, lig, col);
            }
        }
        return carte;

    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. Attendu: nbLignes nbColonnes tailleCases");
    }
}

    private void lireCase(Carte carte,int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature.toUpperCase());
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            
            verifieLigneTerminee();
            Case nouvelleCase = new Case(lig, col, nature);
            carte.setCase(nouvelleCase, lig, col);
            if(nature == NatureTerrain.EAU){
                carte.ajouterEau(nouvelleCase);
            }


        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }

    /**
     * Lit et retourne un tableau d'Incendies.
     */
    private Incendie[] lireIncendies(Carte carte) throws DataFormatException {
    ignorerCommentaires();
    try {
        int nbIncendies = scanner.nextInt();
        Incendie[] incendies = new Incendie[nbIncendies];

        for (int i = 0; i < nbIncendies; i++) {
            incendies[i] = lireIncendie(i, carte);
            carte.ajouterIncendie(incendies[i]);  // Ajout des incendies à la carte
        }

        return incendies;

    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. Attendu: nbIncendies");
    }
}

    /**
     * Lit et retourne un tableau de Robots.
     */
    private Robot[] lireRobots(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robot[] robots = new Robot[nbRobots]; 
            for (int i = 0; i < nbRobots; i++) {
                robots[i] = lireRobot(i,carte);
            }
            return robots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbRobots");
        }
    }

    private Incendie lireIncendie(int i,Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("L'intensité de l'incendie doit être > 0");
            }
            verifieLigneTerminee();
        
            Case position = carte.getCase(lig, col);
            return new Incendie(position, intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format d'incendie invalide. Attendu: ligne, colonne, intensité.");
        }
    }

    /**
     * Lit et crée un Robot.
     */
private Robot lireRobot(int i, Carte carte) throws DataFormatException {
    try {
        int lig = scanner.nextInt();
        int col = scanner.nextInt();
        String type = scanner.next();

        double vitesse = 0;
        String s = scanner.findInLine("(\\d+(\\.\\d+)?)");  // Gère les doubles
        if (s == null) {
        } else {
            vitesse = Double.parseDouble(s);
        }
        verifieLigneTerminee();

        // Création du robot selon son type en passant la carte en argument
        switch (type.toUpperCase()) {
            case "DRONE":
                if (s == null) {
                    vitesse = 100.0;  // Par défaut si vitesse non spécifiée
                }
                return new Drone(lig, col, vitesse, i, carte, classeRobot.DRONE);  // Passe carte
            case "ROUES":
                if (s == null) {
                    vitesse = 80.0;  // Par défaut si vitesse non spécifiée
                }
                return new Roue(lig, col, vitesse, i, carte, classeRobot.ROUE);  // Passe carte
            case "CHENILLES":
                if (s == null) {
                    vitesse = 60.0;  // Par défaut si vitesse non spécifiée
                }
                return new Chenille(lig, col, vitesse, i, carte, classeRobot.CHENILLE);  // Passe carte
            case "PATTES":
                return new Pattes(lig, col, i, carte, classeRobot.PATTES);  // Les pattes n'ont pas de vitesse
            default:
                throw new DataFormatException("Type de robot inconnu: " + type);
        }
    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format de robot invalide. Attendu: ligne, colonne, type, vitesse.");
    }
}



    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}

