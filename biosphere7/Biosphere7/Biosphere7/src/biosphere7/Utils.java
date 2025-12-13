package biosphere7;

/**
 * Quelques fonctions utiles au projet. Vous devez comprendre ce que font ces
 * méthodes (voir leur documentation), mais pas comment elles le font (leur
 * code).
 *
 * À faire évoluer en fonction des nouvelles natures de case, des nouvelles
 * espèces de plantes, etc.
 */
public class Utils {

    /**
     * Séparateur de colonnes dans l'affichage d'un plateau.
     */
    final static String SEPARATEUR_COLONNES = "|";

    /**
     * Séparateur de colonnes dans l'affichage d'un plateau.
     */
    final static String SEPARATEUR_LIGNES = "-";

    /**
     * Intersection de ligne et colonne dans l'affichage d'un plateau.
     */
    final static String SEPARATEUR_LIGNES_COLONNES = "+";

    /**
     * Taille (en nombre de caractères) d'une case dans le terminal. On la
     * suppose toujours carrée.
     */
    final static int TAILLE_CASE_TERMINAL = 3;

    /**
     * Construit un plateau à partir de sa représentation sour forme texte,
     * comme renvoyé par formatTexte(), avec coordonnées et séparateurs.
     *
     * @param texteOriginal le texte du plateau
     * @return le plateau
     */
    public static Case[][] plateauDepuisTexte(final String texteOriginal) {
        final Case[][] plateau = new Case[Coordonnees.NB_LIGNES][Coordonnees.NB_COLONNES];
        final String[] lignes = texteOriginal.split("\n");
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            final String ligne1 = lignes[2 * lig + 1];
            final String ligne2 = lignes[2 * lig + 2];
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                final String codageLigne1 = ligne1.substring(2 + 4 * col, 2 + 4 * col + 3);
                final String codageLigne2 = ligne2.substring(2 + 4 * col, 2 + 4 * col + 3);
                plateau[lig][col] = caseDepuisCodage(codageLigne1, codageLigne2);
            }
        }
        return plateau;
    }

    /**
     * Construit une case depuis son codage.
     *
     * @param ligne1 codage de la case, première ligne
     * @param ligne2 codage de la case, deuxième ligne
     * @return case correspondante
     */
    public static Case caseDepuisCodage(final String ligne1, final String ligne2) {
        // vérification des arguments
        if (ligne1.length() != 3 || ligne2.length() != 3) {
            throw new IllegalArgumentException(
                    "Un codage de ligne doit être sur 3 caractères par ligne.");
        }
        Case laCase = new Case(Case.CAR_VIDE, Case.CAR_ROUGE, 0, Case.CAR_TERRE);
        //
        // ligne 1
        //
        // 1er caractère : nature
        char carNature = ligne1.charAt(0);
        if (carNature == '-') {
            laCase.nature = Case.CAR_TERRE;
        } else {
            laCase.nature = carNature;
        }
        // 2ème caractère : rien
        // 3ème caractère : rien
        //
        // ligne 2
        //
        // 1er caractère : espèce
        laCase.espece = ligne2.charAt(0);
        // 2ème caractère : couleur
        char carCouleur = ligne2.charAt(1);
        if (laCase.espece == Case.CAR_VIDE) {
            if (carCouleur != Case.CAR_VIDE) {
                throw new IllegalArgumentException("Cette case ne contient pas de plante,"
                        + " donc ne devrait pas avoir de couleur associée.");
            }
            carCouleur = Case.CAR_ROUGE;
        } else {
            if (carCouleur != Case.CAR_BLEU && carCouleur != Case.CAR_ROUGE) {
                throw new IllegalArgumentException(
                        "Caractère couleur non admis : " + carCouleur);
            }
        }
        laCase.couleur = carCouleur;
        // 3ème caractère : vitalité
        char carVitalite = ligne2.charAt(2);
        if (laCase.espece == Case.CAR_VIDE) {
            if (carVitalite != Case.CAR_VIDE) {
                throw new IllegalArgumentException("Cette case ne contient pas de plante,"
                        + " donc ne devrait pas avoir de vitalité associée.");
            }
            laCase.vitalite = 0;
        } else {
            laCase.vitalite = Integer.parseInt("" + carVitalite);
        }
        return laCase;
    }

    /**
     * Affiche un plateau dans le terminal.
     *
     * @param plateau le plateau à afficher
     * @param avecLegende affiche une légende sous le plateau
     * @return la chaîne représentant ce plateau.
     */
    public static String afficherPlateau(final Case[][] plateau,
            final boolean avecLegende) {
        final StringBuilder chaine = new StringBuilder();
        if (avecLegende) {
            chaine.append(legende());
        }
        // noms des colonnes
        chaine.append(" ");
        for (int j = 0; j < Coordonnees.NB_COLONNES; j++) {
            chaine.append("  ").append(Coordonnees.numVersCarColonne(j));
            chaine.append(" ");
        }
        chaine.append("\n");
        // lignes
        for (int i = 0; i < Coordonnees.NB_LIGNES; i++) {
            // 1ère ligne
            chaine.append(" ");
            chaine.append(ligneComplete(plateau, i, 1));
            // 2ème ligne
            chaine.append(Coordonnees.numVersCarLigne(i));
            chaine.append(ligneComplete(plateau, i, 2));
        }
        chaine.append(ligneSeparatrice());
        return chaine.toString();
    }

    /**
     * Affichage d'une ligne complète du plateau.
     *
     * @param cases les cases du plateau
     * @param indiceLigne l'indice de la ligne du plateau à afficher
     * @param numLigne 1ère ou 2ème ligne de l'affichage de cette ligne du
     * plateau
     * @return la chaîne représentant la première ou deuxième ligne de cette
     * ligne du plateau
     */
    public static StringBuilder ligneComplete(final Case[][] cases,
            final int indiceLigne, final int numLigne) {
        final StringBuilder chaine = new StringBuilder();
        final String separateur
                = (numLigne == 1 ? SEPARATEUR_LIGNES_COLONNES : SEPARATEUR_COLONNES);
        for (int j = 0; j < Coordonnees.NB_COLONNES; j++) {
            chaine.append(separateur);
            chaine.append(afficher(cases[indiceLigne][j], numLigne));
        }
        chaine.append(separateur);
        chaine.append("\n");
        return chaine;
    }

    /**
     * Renvoie une ligne horizontale séparant deux lignes de cases.
     *
     * @return ligne horizontale séparant deux lignes de cases
     */
    public static StringBuilder ligneSeparatrice() {
        StringBuilder chaine = new StringBuilder(" ");
        for (int c = 0; c < Coordonnees.NB_COLONNES; c++) {
            chaine.append(SEPARATEUR_LIGNES_COLONNES);
            for (int i = 0; i < TAILLE_CASE_TERMINAL; i++) {
                chaine.append(SEPARATEUR_LIGNES);
            }
        }
        chaine.append(SEPARATEUR_LIGNES_COLONNES + "\n");
        return chaine;
    }

    /**
     * Afficher une case dans le terminal.
     *
     * @param uneCase la case à afficher
     * @param numLigne le numéro de la ligne à afficher (1 ou 2)
     * @return la i-ème ligne de cette case
     */
    public static StringBuilder afficher(final Case uneCase,
            final int numLigne) {
        StringBuilder strCase = new StringBuilder();
        switch (numLigne) {
            case 1 -> {
                // 1er caractère : nature
                char carNature = uneCase.nature;
                if (carNature == Case.CAR_TERRE) {
                    carNature = '-';
                }
                strCase.append(carNature);
                // 2ème caractère : tiret
                strCase.append("-");
                // 3ème caractère : tiret
                strCase.append("-");
            }
            case 2 -> {
                // 1er caractère : espèce
                strCase.append(uneCase.espece);
                // 2ème caractère : couleur
                if (uneCase.plantePresente()) {
                    strCase.append(uneCase.couleur);
                } else { // pas de plante
                    strCase.append(Case.CAR_VIDE);
                }
                // 3ème caractère : vitalité
                if (uneCase.plantePresente()) {
                    final int vitalite = uneCase.vitalite;
                    if (vitalite < 0 || 10 < vitalite) {
                        throw new IllegalArgumentException(
                                "Vitalité en dehors de l'intervalle [0;9] : "
                                        + vitalite);
                    }
                    strCase.append(vitalite);
                } else { // pas d'unité
                    strCase.append(Case.CAR_VIDE);
                }
            }
            default -> throw new IllegalArgumentException(
                        "Numéro de ligne différent de 1 et 2 : " + numLigne);
        }
        return strCase;
    }

    public static StringBuilder legende() {
        final StringBuilder legende = new StringBuilder();
        legende.append("Légende (contenu d'une case) :\n");
        legende.append("+E--+ \n");
        legende.append("|PR7| \n");
        legende.append("+---+ \n");
        legende.append("E = nature de la case : '-'=Terre + autres à partir de certains niveaux\n");
        legende.append("P = espèce (si plante présente) : 'P'=Pommier, + autres dans les autres niveaux\n");
        legende.append("R = couleur de la plante (si présente) : 'R'=Rouge, 'B'=Bleu\n");
        legende.append("7 = vitalité de la plante (si présente)\n");
        return legende;
    }
}
