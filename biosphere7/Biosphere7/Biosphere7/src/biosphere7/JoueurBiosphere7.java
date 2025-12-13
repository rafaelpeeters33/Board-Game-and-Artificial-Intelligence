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
        // ajout des actions "planter pommier"
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                Case laCase = plateau[lig][col];
                if (!laCase.plantePresente()) {
                    ajoutActionPlanter(coord, actions, vitalites, couleurJoueur);
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
}
