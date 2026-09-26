package biosphere7;

import java.util.Random;

/**
 * Une IA qui choisit aléatoirement une action parmi celles renvoyées par
 * JoueurBiosphere7.actionsPossibles().
 */
class MonTacheron {

    /**
     * Couleur de mon joueur Tacheron.
     */
    char couleur;

    /**
     * Constructeur.
     *
     * @param uneCouleur couleur du tacheron
     */
    MonTacheron(char uneCouleur) {
        couleur = uneCouleur;
    }

    /**
     * L'action choisie par cette IA : au hasard parmi les actions possibles.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @return l'action choisie sous forme de chaîne
     */
    String actionChoisie(Case[][] plateau, int nbToursJeu) {
        // on instancie votre implémentation
        JoueurBiosphere7 joueurBiosphere7 = new JoueurBiosphere7();
        // choisir aléatoirement une action possible
        String[] actionsPossibles = ActionsPossibles.nettoyerTableau(
                joueurBiosphere7.actionsPossibles(plateau, couleur, 8));
        String actionJouee = null;
        if (actionsPossibles.length > 0) {
            Random r = new Random();
            int indiceAleatoire = r.nextInt(actionsPossibles.length);
            actionJouee = ActionsPossibles.enleverVitalites(
                    actionsPossibles[indiceAleatoire]);
        }
        return actionJouee;
    }
    

    /**
     * Boucle de jeu : envoi des actions que vous souhaitez jouer, et réception
     * des actions de l'adversaire.
     *
     * @param couleurIA couleur de l'IA
     * @param couleurTacheron couleur du tacheron
     */
    static void toursDeJeu(char couleurIA, char couleurTacheron) {
        // paramètres
        System.out.println("IABiosphere7 est " + couleurIA + ".");
        // instantiation de mon IA
        IABiosphere7 monIA = new IABiosphere7("", -1, couleurIA);
        MonTacheron monTacheron = new MonTacheron(couleurTacheron);
        // le plateau initial : vide
        Case[][] plateau = plateauInitial();
        // compteur de tours de jeu (entre 1 et 40)
        int nbToursJeu = 1;
        // la couleur du joueur courant (change à chaque tour de jeu)
        char couleurTourDeJeu = Case.CAR_BLEU;
        // booléen pour détecter la fin du jeu
        boolean fin = false;
        while (!fin) {
            // choisir l'action
            String actionChoisie;
            String joueur;
            if (couleurTourDeJeu == couleurIA) {
                actionChoisie = monIA.actionChoisie(plateau, nbToursJeu);
                joueur = "IABiosphere7";
            } else {
                actionChoisie = monTacheron.actionChoisie(plateau, nbToursJeu);
                joueur = "MonTacheron";
            }
            System.out.println(joueur + " joue : " + actionChoisie);
            // mettre à jour le plateau
            IABiosphere7.mettreAJour(plateau, actionChoisie, couleurTourDeJeu);
            if (nbToursJeu == IABiosphere7.NB_TOURS_JEU_MAX) {
                // fini
                fin = true;
            } else {
                // au suivant
                nbToursJeu++;
                couleurTourDeJeu = IABiosphere7.suivant(couleurTourDeJeu);
            }
        }
    }

    /**
     * Construit le plateau initial.
     *
     * @return un plateau initial
     */
    static Case[][] plateauInitial() {
        Case[][] plateau = new Case[Coordonnees.NB_LIGNES][Coordonnees.NB_COLONNES];
        for (Case[] ligne : plateau) {
            for (int j = 0; j < ligne.length; j++) {
                ligne[j] = new Case(Case.CAR_VIDE, Case.CAR_BLEU, 0, Case.CAR_TERRE);
                IABiosphere7.retirerPlante(ligne[j]);
            }
        }
        return plateau;
    }

    /**
     * Lancer une partie entre votre IA et votre tacheron.
     *
     * @param args arguments de la ligne de commande (inutilisés)
     */
    public static void main(String[] args) {
        // choisir la couleur de l'IA
        char couleurIA = Case.CAR_BLEU;
        // monTacheron prend l'autre couleur
        char couleurTacheron = IABiosphere7.suivant(couleurIA);
        // lancement
        toursDeJeu(couleurIA, couleurTacheron);
    }
}
