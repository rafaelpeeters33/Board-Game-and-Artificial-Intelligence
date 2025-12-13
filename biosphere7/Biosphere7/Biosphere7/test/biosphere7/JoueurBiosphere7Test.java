package biosphere7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe JoueurBiosphere7.
 */
public class JoueurBiosphere7Test {

    /**
     * Test de la fonction actionsPossibles.
     */
    @Test
    public void testActionsPossibles() {
        //testActionsPossibles_niveau1();
        testActionsPossibles_niveau2();
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 1.
     */
    public void testActionsPossibles_niveau1() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // un plateau sur lequel on veut tester actionsPossibles()
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        // on choisit la couleur du joueur
        char couleur = 'R';
        // on choisit le niveau
        int niveau = 1;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on peut aussi tester si une action est dans les actions possibles :
        assertTrue(actionsPossibles.contient("PaB,1,0"));
        // on peut aussi tester si une action n'est pas dans les actions 
        // possibles :
        assertFalse(actionsPossibles.contient("PaO,1,0"));
        assertFalse(actionsPossibles.contient("PaA,0,0"));
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,1,0"));
        assertTrue(actionsPossibles.contient("PnA,1,0"));
        assertTrue(actionsPossibles.contient("PaN,1,0"));
        assertTrue(actionsPossibles.contient("PnN,1,0"));
        // vérifions s'il y a le bon nombre d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 2.
     */
    public void testActionsPossibles_niveau2() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        char couleur = 'B';
        int niveau = 2;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,2,3"));
        assertTrue(actionsPossibles.contient("PnA,2,3"));
        assertFalse(actionsPossibles.contient("PaN,2,3"));
        assertTrue(actionsPossibles.contient("PnN,2,3"));
        // on peut poser sur une case quelconque vide :
        assertTrue(actionsPossibles.contient("PkD,2,3"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        // nombre correct d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - 4,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction ajoutActionPlanter.
     */
    @Test
    public void testAjoutActionPlanter() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('f', 'D'), actions,
                vitalites, Case.CAR_ROUGE);
        // l'action est devenue possible
        assertTrue(actions.contient("PfD,1,0"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('b', 'H'), actions,
                vitalites, Case.CAR_ROUGE);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("PbH,1,0"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }

    @Test
    public void testVitalitesPlateau() {
        // plateau : rouge 0, bleu 0
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Vitalites vita1 = JoueurBiosphere7.vitalitesPlateau(plateau1);
        assertEquals(0, vita1.vitalitesRouge);
        assertEquals(0, vita1.vitalitesBleu);
        // plateau : rouge 2, bleu 2
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        Vitalites vita2 = JoueurBiosphere7.vitalitesPlateau(plateau2);
        assertEquals(2, vita2.vitalitesRouge);
        assertEquals(2, vita2.vitalitesBleu);
    }

    /**
     * Un plateau de base, sous forme de chaîne.
     */
    final String PLATEAU_VIDE
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    /**
     * Un plateau pour tester le niveau 2.
     */
    final String PLATEAU_NIVEAU2
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
}
