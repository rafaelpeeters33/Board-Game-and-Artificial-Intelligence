package biosphere7;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Votre IA pour le jeu Biosphere7.
 */
public class IABiosphere7 {

    /**
     * Hôte du grand ordonnateur.
     */
    String hote = null;

    /**
     * Port du grand ordonnateur.
     */
    int port = -1;

    /**
     * Couleur de votre joueur (IA) : 'R'ouge ou 'B'leu.
     */
    final char couleur;

    /**
     * Interface pour le protocole du grand ordonnateur.
     */
    TcpGrandOrdonnateur grandOrdo = null;

    /**
     * Nombre maximal de tours de jeu.
     */
    static final int NB_TOURS_JEU_MAX = 40;

    /**
     * Constructeur.
     *
     * @param hote Hôte.
     * @param port Port.
     * @param uneCouleur Couleur de ce joueur
     */
    public IABiosphere7(String hote, int port, char uneCouleur) {
        this.hote = hote;
        this.port = port;
        this.grandOrdo = new TcpGrandOrdonnateur();
        this.couleur = uneCouleur;
    }

    /**
     * Connexion au Grand Ordonnateur.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void connexion() throws IOException {
        System.out.print(
                "Connexion au Grand Ordonnateur : " + hote + " " + port + "...");
        System.out.flush();
        grandOrdo.connexion(hote, port);
        System.out.println(" ok.");
        System.out.flush();
    }

    /**
     * Boucle de jeu : envoi des actions que vous souhaitez jouer, et réception
     * des actions de l'adversaire.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void toursDeJeu() throws IOException {
        // paramètres
        System.out.println("Je suis le joueur " + couleur + ".");
        // le plateau initial
        System.out.println("Réception du plateau initial...");
        Case[][] plateau = grandOrdo.recevoirPlateauInitial();
        System.out.println("Plateau reçu.");
        // compteur de tours de jeu (entre 1 et 40)
        int nbToursJeu = 1;
        // la couleur du joueur courant (change à chaque tour de jeu)
        char couleurTourDeJeu = Case.CAR_ROUGE;
        // booléen pour détecter la fin du jeu
        boolean fin = false;
        while (!fin) {
            boolean disqualification = false;
            if (couleurTourDeJeu == couleur) {
                // à nous de jouer !
                jouer(plateau, nbToursJeu);
            } else {
                // à l'adversaire de jouer
                disqualification = adversaireJoue(plateau, couleurTourDeJeu);
            }
            if (nbToursJeu == NB_TOURS_JEU_MAX || disqualification) {
                // fini
                fin = true;
            } else {
                // au suivant
                nbToursJeu++;
                couleurTourDeJeu = suivant(couleurTourDeJeu);
            }
        }
    }

    /**
     * Fonction exécutée lorsque c'est à notre tour de jouer. Cette fonction
     * envoie donc l'action choisie au serveur.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @throws IOException exception sur les entrées / sorties
     */
    void jouer(Case[][] plateau, int nbToursJeu) throws IOException {
        String actionJouee = actionChoisie(plateau, nbToursJeu);
        if (actionJouee != null) {
            // jouer l'action
            System.out.println("On joue : " + actionJouee);
            grandOrdo.envoyerAction(actionJouee);
            mettreAJour(plateau, actionJouee, couleur);
        } else {
            // Problème : le serveur vous demande une action alors que vous n'en
            // trouvez plus...
            System.out.println("Aucun action trouvée : abandon...");
            grandOrdo.envoyerAction("ABANDON");
        }
    }

    /**
     * L'action choisie par notre IA.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @return l'action choisie sous forme de chaîne
     */
    public String actionChoisie(Case[][] plateau, int nbToursJeu) {
        // Je récupere mes action possible 
        JoueurBiosphere7 joueurBiosphere7 = new JoueurBiosphere7();
        String[] actionPossible = ActionsPossibles.nettoyerTableau(joueurBiosphere7.actionsPossibles(plateau, couleur, 8));
        String meilleurCoupPossible = null;
        int meilleurScorePerso = -1000;
        // boucle sur mes coups possibles
        for (String action : actionPossible) {
            // On crée un nouveau tableau temporaire similaire
            Case[][] plateauTempo = clonerPlateau(plateau);
            // On applique l'action sur le tableau copié
            mettreAJour(plateauTempo, ActionsPossibles.enleverVitalites(action), couleur);
            // On calculle les vitalite apres le coup 
            int scoreTempo = diffVitalitePlateau(plateauTempo, couleur);
            char couleurAdverse;
            if (couleur == 'R') {
                couleurAdverse = 'B';
            } else {
                couleurAdverse = 'R';
            }

            // Action possible de l'adversaire
            String[] actionAdversaire = joueurBiosphere7.actionsPossibles(plateauTempo, couleurAdverse, 8);
            int meilleurScoreAdverse = -1000;
            // boucle sur les coups possibles de l'adversaire
            for (String actionAdverse : actionAdversaire) {
                // On regarde si c'est la meilleur action action de l'adversaire pour l'instant
                int scoreTemporaire = calculerDiff(actionAdverse, couleurAdverse);
                if (scoreTemporaire > meilleurScoreAdverse) {
                    meilleurScoreAdverse = scoreTemporaire;
                }
            }
            // Score suivant correspond au score de l'action que l'ia joue moins le scrore que l'adversaire joue 
            // A noté qu'on supose que l'adversaire joue son meilleur coup
            int scoreSuivant = scoreTempo - meilleurScoreAdverse;
            char typeAction = action.charAt(0);
            // On favorise la coupe 
            if (typeAction == 'C') {
                scoreSuivant += 1;
            }
            //  On favorise encore plus la plantation de légume car cela rapporte 3
            if (typeAction == 'D' || typeAction == 'T' || typeAction == 'H') {
                scoreSuivant += 3;
            }
            //On regarde si l'action qu'on teste est meilleure que celle qu'on avait retenue avant.
            if (scoreSuivant > meilleurScorePerso) {
                meilleurScorePerso = scoreSuivant;
                meilleurCoupPossible = action;
            }
        }

        return ActionsPossibles.enleverVitalites(meilleurCoupPossible);
    }

    /**
     * Copie du plateau pour la simulation
     */
    private Case[][] clonerPlateau(Case[][] plateauOriginal) {
        Case[][] plateauCopie = new Case[plateauOriginal.length][plateauOriginal.length];
        for (int i = 0; i < plateauCopie.length; i++) {
            for (int j = 0; j < plateauCopie[i].length; j++) {
                plateauCopie[i][j] = new Case(plateauOriginal[i][j].espece, plateauOriginal[i][j].couleur, plateauOriginal[i][j].vitalite, plateauOriginal[i][j].nature);
            }
        }
        return plateauCopie;
    }

    /**
     * Calcule la différence de vitalité total du tableau entre les deux joueurs
     *
     */
    private int diffVitalitePlateau(Case[][] p, char couleurJoueur) {
        int joueur = 0;
        int adversaire = 0;
        for (Case[] ligne : p) {
            for (Case c : ligne) {
                if (c.plantePresente()) {
                    if (c.couleur == couleurJoueur) {
                        joueur += c.vitalite;
                    } else {
                        adversaire += c.vitalite;
                    }
                }
            }
        }
        return joueur - adversaire;
    }

    /**
     * Méthode qui calcule la différence entre les vitalités bleues et rouges et
     * la renvoie.
     *
     * @param action l'action à appliquer
     * @return un entier désignant la différence
     */
    int calculerDiff(String action, char couleurJoueur) {
        //on crée un tableau de String contenant action et vitalités
        String[] actionSplit = action.split(",");
        int vitRouge = Integer.valueOf(actionSplit[1]);
        int vitBleu = Integer.valueOf(actionSplit[2]);
        int diff;
        //on calcul la différence selon la couleur du joueur entrée en paramètre
        if (couleurJoueur == 'R') {
            diff = vitRouge - vitBleu;
        } else {
            diff = vitBleu - vitRouge;
        }
        return diff;
    }

    /**
     * L'adversaire joue : on récupère son action, met à jour le plateau, et
     * signale toute disqualification.
     *
     * @param plateau le plateau de jeu
     * @param couleurAdversaire couleur de l'adversaire
     * @return l'action choisie sous forme de chaîne
     */
    boolean adversaireJoue(Case[][] plateau, char couleurAdversaire) {
        boolean disqualification = false;
        System.out.println("Attente de réception action adversaire...");
        String actionAdversaire = grandOrdo.recevoirAction();
        System.out.println("Action adversaire reçue : " + actionAdversaire);
        if ("Z".equals(actionAdversaire)) {
            System.out.println("L'adversaire est disqualifié.");
            disqualification = true;
        } else {
            System.out.println("L'adversaire joue : "
                    + actionAdversaire + ".");
            mettreAJour(plateau, actionAdversaire, couleurAdversaire);
        }
        return disqualification;
    }

    /**
     * Calcule la couleur du prochain joueur.
     *
     * @param couleurCourante la couleur du joueur courant
     * @return la couleur du prochain joueur
     */
    static char suivant(char couleurCourante) {
        return couleurCourante == Case.CAR_ROUGE
                ? Case.CAR_BLEU : Case.CAR_ROUGE;
    }

    /**
     * Mettre à jour le plateau suite à une action, supposée valide.
     *
     * @param plateau le plateau
     * @param action l'action à appliquer
     * @param couleurCourante couleur du joueur courant
     */
    static void mettreAJour(Case[][] plateau, String action, char couleurCourante) {
        if (plateau == null || action == null || action.length() != 3) {
            return;
        }
        Coordonnees coord = Coordonnees.depuisCars(action.charAt(1), action.charAt(2));
        switch (action.charAt(0)) {
            // on réalise les actions selon le caractère donné
            case 'P', 'S', 'B', 'T', 'H', 'D' ->
                planter(coord, plateau, couleurCourante, action.charAt(0));
            case 'C' ->
                couper(coord, plateau);
            case 'F' ->
                fertiliser(coord, plateau);
            default ->
                System.out.println("Type d'action incorrect : " + action.charAt(0));
        }
    }

    /**
     * Planter une plante sur une case donnée.
     *
     * @param coord coordonnées de la case
     * @param plateau le plateau de jeu
     * @param couleurCourante la couleur du joueur courant
     */
    static void planter(Coordonnees coord, Case[][] plateau, char couleurCourante, char espece) {
        Case laCase = plateau[coord.ligne][coord.colonne];
        laCase.couleur = couleurCourante;
        laCase.espece = espece;
        laCase.vitalite = 1 + nbVoisinesJoueur(coord, plateau, couleurCourante);
        etouffement(coord, plateau);
    }

    /**
     * Nombre de cases voisines d'une case et contenant une plante du joueur.
     *
     * @param coord la case dont on souhaite analyser les voisines
     * @param plateau le plateau courant
     * @param couleurCourante la couleur du joueur courant
     * @return le nombre de cases voisines contenant une plante du joueur
     */
    static int nbVoisinesJoueur(Coordonnees coord, Case[][] plateau, char couleurCourante) {
        return (int) voisines(coord)
                .map(v -> plateau[v.ligne][v.colonne])
                .filter(c -> c.plantePresente())
                .filter(c -> c.couleur == couleurCourante)
                .count();
    }

    /**
     * Nombre de cases voisines d'une case.
     *
     * @param coord la case dont on souhaite analyser les voisines
     * @param plateau le plateau courant
     * @return le nombre de cases voisines contenant une plante
     */
    static int nbVoisines(Coordonnees coord, Case[][] plateau) {
        return (int) voisines(coord)
                .map(v -> plateau[v.ligne][v.colonne])
                .filter(c -> c.plantePresente())
                .count();
    }

    /**
     * Les coordonnées des cases voisines dans le plateau.
     *
     * @param coord les coordonnées de la case d'origine
     * @return les coordonnées des cases voisines
     */
    static Stream<Coordonnees> voisines(final Coordonnees coord) {
        return Stream.of(new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}})
                .map(d -> new Coordonnees(coord.ligne + d[0], coord.colonne + d[1]))
                .filter(v -> 0 <= v.ligne && v.ligne < Coordonnees.NB_LIGNES)
                .filter(v -> 0 <= v.colonne && v.colonne < Coordonnees.NB_COLONNES);
    }

    /**
     * Couper une plante sur une case donnée.
     *
     * @param coord coordonnées de la case
     * @param plateau le plateau de jeu
     */
    static void couper(Coordonnees coord, Case[][] plateau) {
        retirerPlante(plateau[coord.ligne][coord.colonne]);
        voisines(coord)
                .map(v -> plateau[v.ligne][v.colonne])
                .filter(c -> c.plantePresente())
                .forEach(c -> c.vitalite = Math.min(9, c.vitalite + 1));
    }

    /**
     * Fertiliser une plante sur une case donnée.
     *
     * @param coord coordonnées de la case
     * @param plateau le plateau de jeu
     */
    static void fertiliser(Coordonnees coord, Case[][] plateau) {
        Case laCase = plateau[coord.ligne][coord.colonne];
        switch (laCase.espece) {
            // on augmente la vitalité selon l'espèce de plante
            case 'P', 'S' ->
                laCase.vitalite += 1;
            case 'B' ->
                laCase.vitalite += 2;
            default ->
                laCase.vitalite += 3;
        }
        if (laCase.vitalite > 9) {
            laCase.vitalite = 9;
        }
    }

    /**
     * Retire une plante si elle est étouffée.
     *
     * @param coord coordonnées de la case
     * @param plateau le plateau de jeu
     */
    static void etouffement(Coordonnees coord, Case[][] plateau) {
        Stream<Coordonnees> voisin = voisines(coord);
        voisin.forEach(v -> {
            int nbVoisins = nbVoisines(v, plateau);
            if (nbVoisins == 4) {
                Case caseObserver = plateau[v.ligne][v.colonne];
                retirerPlante(caseObserver);
            }
        });
    }

    /**
     * Retirer une plante d'une case.
     *
     * @param laCase la case dont on doit retirer la plante
     */
    static void retirerPlante(Case laCase) {
        laCase.espece = Case.CAR_VIDE;
        laCase.couleur = Case.CAR_ROUGE;
        laCase.vitalite = 0;
    }

    /**
     * Programme principal. Il sera lancé automatiquement, ce n'est pas à vous
     * de le lancer.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("Démarrage le " + format.format(new Date()));
        System.out.flush();
        // « create » du protocole du grand ordonnateur.
        final String USAGE
                = System.lineSeparator()
                + "\tUsage : java " + IABiosphere7.class.getName()
                + " <hôte> <port> <ordre>";
        if (args.length != 3) {
            System.out.println("Nombre de paramètres incorrect." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        String hote = args[0];
        int port = -1;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        int ordre = -1;
        try {
            ordre = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("L'ordre doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        try {
            char couleurJoueur = (ordre == 1 ? 'R' : 'B');
            IABiosphere7 iaLowatem = new IABiosphere7(hote, port, couleurJoueur);
            iaLowatem.connexion();
            iaLowatem.toursDeJeu();
        } catch (IOException e) {
            System.out.println("Erreur à l'exécution du programme : \n" + e);
            System.out.flush();
            System.exit(1);
        }
    }
}
