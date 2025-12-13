package biosphere7;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un
 * niveau donné.
 */
public class JoueurBiosphere7 implements IJoueurBiosphere7 {

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes
     * les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param couleurJoueur couleur du joueur
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, char couleurJoueur, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        // se préparer à stocker les actions possibles
        ActionsPossibles actions = new ActionsPossibles();
        // calculer les vitalités sur le plateau initial
        Vitalites vitalites = vitalitesPlateau(plateau);
        // ajout des actions "planter pommier" et "couper plante"
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                Case laCase = plateau[lig][col];
                if (!laCase.plantePresente()) {
                    ajoutActionPlanter(coord, actions, vitalites, couleurJoueur);
                } else if (laCase.plantePresente()) {
                    ajoutActionCouper(coord, actions, vitalites, laCase, plateau);
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }

    /**
     * Méthode de classe qui renvoie la somme des vitalités des plantes de
     * chaque joueur sur le plateau.
     *
     * @param plateau le plateau
     * @return la somme des vitalités des plantes de chaque joueur
     */
    static Vitalites vitalitesPlateau(Case[][] plateau) {
        int vitaliteRouge = 0;
        int vitaliteBleu = 0;
        // on parcourt le plateau dans son entièreté
        for (Case[] plateau1 : plateau) {
            for (Case laCase : plateau1) {
                // vérifie la présence d'une plante & de la couleur et indique la vitalité
                if (laCase.plantePresente() == true) {
                    if (laCase.couleur == 'R') {
                        vitaliteRouge += laCase.vitalite;
                    } else if (laCase.couleur == 'B') {
                        vitaliteBleu += laCase.vitalite;
                    }
                }
            }
        }
        return new Vitalites(vitaliteRouge, vitaliteBleu);
    }

    /**
     * Méthode de classe qui renvoie les coordonnées voisines d'une case selon
     * ses coordonnées.
     *
     * @param coord coordonnées de la case considérée
     * @return un tableau de Coordonnees contenant ses coordonnées voisines
     */
    static Coordonnees[] coordonneesVoisines(Coordonnees coord) {
        Coordonnees[] coordVoisines = new Coordonnees[4];
        int lig = coord.ligne;
        int col = coord.colonne;
        // voisin Nord 
        coordVoisines[0] = new Coordonnees(lig - 1, col);
        // voisin Sud 
        coordVoisines[1] = new Coordonnees(lig + 1, col);
        // voisin Ouest 
        coordVoisines[2] = new Coordonnees(lig, col - 1);
        // voisin Est 
        coordVoisines[3] = new Coordonnees(lig, col + 1);
        return coordVoisines;
    }

    /**
     * Méthode d'instance qui ajoute une action de plantation de pommier dans
     * l'ensemble des actions possibles.
     *
     * @param coord coordonnées de la case où planter le pommier
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param couleur la couleur du pommier à ajouter
     */
    void ajoutActionPlanter(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleur) {
        // on modifie les vitalités
        int vitalitesRouge = vitalites.vitalitesRouge;
        int vitalitesBleu = vitalites.vitalitesBleu;
        if (couleur == 'R') {
            vitalitesRouge++;
        } else if (couleur == 'B') {
            vitalitesBleu++;
        }
        // ajout de l'action planter
        String action = "P" + coord.carLigne() + coord.carColonne() + ","
                + (vitalitesRouge) + ","
                + (vitalitesBleu);
        actions.ajouterAction(action);
    }

    /**
     * Méthode d'instance qui ajoute une action de coupe de plante dans
     * l'ensemble des actions possibles.
     *
     * @param coord coordonnées de la case où couper la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param laCase la case considérée
     */
    void ajoutActionCouper(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, Case laCase, Case[][] plateau) {
        // on modifie les vitalités 
        int vitalitesRouge = vitalites.vitalitesRouge;
        int vitalitesBleu = vitalites.vitalitesBleu;
        if (laCase.couleur == Case.CAR_ROUGE) {
            vitalitesRouge -= laCase.vitalite;
        } else if (laCase.couleur == Case.CAR_BLEU) {
            vitalitesBleu -= laCase.vitalite;
        }
        // on calcule les boosts
        Vitalites boost = calculBoostVoisins(coord, plateau);
        vitalitesRouge += boost.vitalitesRouge;
        vitalitesBleu += boost.vitalitesBleu;
        // ajout de l'action couper
        String action = "C" + coord.carLigne() + coord.carColonne() + ","
                + (vitalitesRouge) + ","
                + (vitalitesBleu);
        actions.ajouterAction(action);
    }

    /**
     * Méthode de classe qui calcule l'impact du boost de coupe sur les 4
     * voisins d'une case.
     *
     * @param coord coordonnées de la case considérée
     * @param plateau le plateau considéré
     * @return un objet Vitalites contenant le total des points de boost à
     * ajouter à chaque joueur
     */
    static Vitalites calculBoostVoisins(Coordonnees coord, Case[][] plateau) {
        int boostRouge = 0;
        int boostBleu = 0;
        Coordonnees[] voisins = coordonneesVoisines(coord);
        // on parcourt les voisins
        for (Coordonnees coordVoisin : voisins) {
            int lig = coordVoisin.ligne;
            int col = coordVoisin.colonne;
            // on vérifie que la coordonnée est dans les limites du plateau
            if (lig >= 0 && lig < Coordonnees.NB_LIGNES
                    && col >= 0 && col < Coordonnees.NB_COLONNES) {
                // on vérifie la présence d'une plante
                Case caseVoisine = plateau[lig][col];
                if (caseVoisine.plantePresente()) {
                    int vitaliteActuelle = caseVoisine.vitalite;
                    // on appplique le boost
                    if (vitaliteActuelle < 9) {
                        if (caseVoisine.couleur == Case.CAR_ROUGE) {
                            boostRouge++;
                        } else if (caseVoisine.couleur == Case.CAR_BLEU) {
                            boostBleu++;
                        }
                    }
                }
            }
        }
        return new Vitalites(boostRouge, boostBleu);
    }
}
