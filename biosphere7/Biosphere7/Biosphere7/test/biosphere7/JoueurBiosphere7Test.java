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
     * Test de la méthode actionsPossibles.
     */
    @Test
    public void testActionsPossibles() {
        //testActionsPossibles_niveau1();
        //testActionsPossibles_niveau2();
        //testActionsPossibles_niveau3();
        //testActionsPossibles_niveau4();
        //testActionsPossibles_niveau5();
        //testActionsPossibles_niveau6();
        testActionsPossibles_niveau7();
    }

    /**
     * Test de la méthode actionsPossibles, au niveau 1.
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
     * Test de la méthode actionsPossibles, au niveau 2.
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
     * Test de la méthode actionsPossibles, au niveau 3.
     */
    public void testActionsPossibles_niveau3() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        char couleur = 'B';
        int niveau = 3;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut poser sur une case quelconque vide
        assertTrue(actionsPossibles.contient("PcA,4,5"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PbH,4,5"));
        assertFalse(actionsPossibles.contient("CaM,4,5"));
        // on peut couper une plante Rouge en bH 
        assertTrue(actionsPossibles.contient("CbH,3,4"));
        // on peut couper une plante Bleue en aM 
        assertTrue(actionsPossibles.contient("CaM,4,3"));
        // on peut couper une autre plante Bleue en eK 
        assertTrue(actionsPossibles.contient("CeK,4,3"));
        // vérifions s'il y a le bon nombre d'actions possibles 
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la méthode actionsPossibles, au niveau 4.
     */
    public void testActionsPossibles_niveau4() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU4);
        char couleur = 'B';
        int niveau = 4;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut planter sur une case quelconque vide         
        assertTrue(actionsPossibles.contient("PcA,16,17"));
        // on ne peut pas planter sur une case occupée )
        assertFalse(actionsPossibles.contient("PeB,16,17"));
        // on peut couper une plante Bleue en hJ 
        assertTrue(actionsPossibles.contient("ChJ,16,15"));
        // on peut couper une plante Bleue en bI
        assertTrue(actionsPossibles.contient("CbI,15,16"));
        // on ne peut pas couper une case vide 
        assertFalse(actionsPossibles.contient("PgA,15,16"));
        // vérifions s'il y a le bon nombre d'actions possibles 
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la méthode actionsPossibles, au niveau 5.
     */
    public void testActionsPossibles_niveau5() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU5);
        char couleur = 'B';
        int niveau = 5;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut planter sur une case quelconque vide
        assertTrue(actionsPossibles.contient("PdH,16,22"));
        // on ne peut pas planter sur une case occupée 
        assertFalse(actionsPossibles.contient("PeG,16,19"));
        // on peut couper une plante Rouge en aG 
        assertTrue(actionsPossibles.contient("CaG,16,18"));
        // on peut couper une plante Bleue en cE 
        assertTrue(actionsPossibles.contient("CkC,16,18"));
        // on ne peut pas couper une case vide 
        assertFalse(actionsPossibles.contient("CeJ,16,18"));
        // vérifions s'il y a le bon nombre d'actions possibles 
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la méthode actionsPossibles, au niveau 6.
     */
    public void testActionsPossibles_niveau6() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU6);
        char couleur = 'B';
        int niveau = 6;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut planter sur une case quelconque vide 
        assertTrue(actionsPossibles.contient("PmC,11,9"));
        // on ne peut pas planter sur une case occupée 
        assertFalse(actionsPossibles.contient("PLK,11,6"));
        // on peut couper une plante Bleue en bC 
        assertTrue(actionsPossibles.contient("CbC,12,6"));
        // on peut couper une plante Rouge en bJ 
        assertTrue(actionsPossibles.contient("CbJ,10,7"));
        // on ne peut pas couper une case vide
        assertFalse(actionsPossibles.contient("CeJ,11,5"));
        // vérifions s'il y a le bon nombre d'actions possibles 
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }
    
    /**
     * Test de la méthode actionsPossibles, au niveau 7.
     */
    public void testActionsPossibles_niveau7() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        char couleur = 'B';
        int niveau = 7;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // vitalités rouges : 19, vitalités bleues : 17
        // on peut planter sur une case quelconque vide
        assertTrue(actionsPossibles.contient("PaB,19,18"));
        // on ne peut pas planter sur une case occupée 
        assertFalse(actionsPossibles.contient("PaA,18,17"));
        // on peut planter en provoquant un étouffement 
        assertTrue(actionsPossibles.contient("PeE,17,18"));
        // on ne peut pas planter sur une case qui étoufferait immédiatement 
        assertFalse(actionsPossibles.contient("PfE,17,18"));
        // on ne peut pas couper une case vide 
        assertFalse(actionsPossibles.contient("PeE,18,18"));
        // vérifions s'il y a le bon nombre d'actions possibles 
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }
    
    /**
     * Test de la méthode vitalitesPlateau.
     */
    @Test
    public void testVitalitesPlateau() {
        // plateau 1 : rouge 0, bleu 0
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Vitalites vita1 = JoueurBiosphere7.vitalitesPlateau(plateau1);
        assertEquals(0, vita1.vitalitesRouge);
        assertEquals(0, vita1.vitalitesBleu);
        // plateau 2 : rouge 2, bleu 2
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        Vitalites vita2 = JoueurBiosphere7.vitalitesPlateau(plateau2);
        assertEquals(2, vita2.vitalitesRouge);
        assertEquals(2, vita2.vitalitesBleu);
        // plateau 3 : rouge 4, bleu 4
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        Vitalites vita3 = JoueurBiosphere7.vitalitesPlateau(plateau3);
        assertEquals(4, vita3.vitalitesRouge);
        assertEquals(4, vita3.vitalitesBleu);
          Case[][] plateau5 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        Vitalites vita = JoueurBiosphere7.vitalitesPlateau(plateau5);
        assertEquals(19, vita.vitalitesRouge);
        assertEquals(17, vita.vitalitesBleu);
    }

    /**
     * Test de la méthode coordonneesVoisines.
     */
    @Test
    public void testCoordonneesVoisines() {
        // on teste un point au hasard 
        Coordonnees point = Coordonnees.depuisCars('f', 'D');
        Coordonnees[] voisins = JoueurBiosphere7.coordonneesVoisines(point);
        // on vérifie qu'il y a bien 4 voisins
        assertEquals(4, voisins.length);
        // voisin Nord 
        assertEquals(4, voisins[0].ligne);
        assertEquals(3, voisins[0].colonne);
        // voisin Sud 
        assertEquals(6, voisins[1].ligne);
        assertEquals(3, voisins[1].colonne);
        // voisin Ouest 
        assertEquals(5, voisins[2].ligne);
        assertEquals(2, voisins[2].colonne);
        // voisin Est 
        assertEquals(5, voisins[3].ligne);
        assertEquals(4, voisins[3].colonne);
    }

    /**
     * Test de la méthode ajoutActionPlanter.
     */
    @Test
    public void testAjoutActionPlanter() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        Vitalites vitalites = new Vitalites(0, 0);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('f', 'D'), actions,
                vitalites, Case.CAR_ROUGE, plateau);
        // l'action est devenue possible
        assertTrue(actions.contient("PfD,1,0"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('b', 'H'), actions,
                vitalites, Case.CAR_ROUGE, plateau);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("PbH,1,0"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }

    /**
     * Test de la méthode ajoutActionCouper.
     */
    @Test
    public void testAjoutActionCouper() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        Vitalites vitalites = new Vitalites(4, 4);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        Coordonnees coord1 = Coordonnees.depuisCars('b', 'H');
        Case laCase1 = plateau[coord1.ligne][coord1.colonne];
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutActionCouper(coord1, actions, vitalites, laCase1, plateau);
        // l'action est devenue possible
        assertTrue(actions.contient("CbH,3,4"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("CaM,4,3"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        Coordonnees coord2 = Coordonnees.depuisCars('a', 'M');
        Case laCase2 = plateau[coord2.ligne][coord2.colonne];
        joueur.ajoutActionCouper(coord2, actions, vitalites, laCase2, plateau);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("CaM,4,3"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }

    /**
     * Test de la méthode calculBoostVoisins.
     */
    @Test
    public void testCalculBoostVoisins() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU4);
        // cas 1 : case centrale
        Coordonnees coordCentrale = Coordonnees.depuisCars('g', 'J');
        Vitalites boostCentre = JoueurBiosphere7.calculBoostVoisins(coordCentrale, plateau);
        assertEquals(1, boostCentre.vitalitesRouge);
        assertEquals(2, boostCentre.vitalitesBleu);
        // cas 2 : bord vertical
        Coordonnees coordBordVertical = Coordonnees.depuisCars('g', 'A');
        Vitalites boostBordVertical = JoueurBiosphere7.calculBoostVoisins(coordBordVertical, plateau);
        assertEquals(0, boostBordVertical.vitalitesRouge);
        assertEquals(0, boostBordVertical.vitalitesBleu);
        // cas 3 : bord horizontal
        Coordonnees coordBordHorizontal = Coordonnees.depuisCars('a', 'B');
        Vitalites boostBordHorizontal = JoueurBiosphere7.calculBoostVoisins(coordBordHorizontal, plateau);
        assertEquals(0, boostBordHorizontal.vitalitesRouge);
        assertEquals(2, boostBordHorizontal.vitalitesBleu);
    }

    /**
     * Test de la méthode calculNombreVoisins.
     */
    @Test
    public void testCalculNombreVoisins() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU6);
        char couleurRouge = 'R';
        // cas 1 : 2 voisins
        Coordonnees coordCentre = Coordonnees.depuisCars('c', 'J');
        int voisinsCentre = JoueurBiosphere7.calculNombreVoisins(coordCentre, couleurRouge, plateau);
        assertEquals(2, voisinsCentre);
        // cas 2 : pas de voisins 
        Coordonnees coordBord = Coordonnees.depuisCars('f', 'A');
        int voisinsBord = JoueurBiosphere7.calculNombreVoisins(coordBord, couleurRouge, plateau);
        assertEquals(0, voisinsBord);
        // cas 3 : 1 voisin
        Coordonnees coordPlante = Coordonnees.depuisCars('b', 'H');
        int voisinsPlante = JoueurBiosphere7.calculNombreVoisins(coordPlante, couleurRouge, plateau);
        assertEquals(1, voisinsPlante);
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

    /**
     * Un plateau pour tester le niveau 3.
     */
    final String PLATEAU_NIVEAU3
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |PB1|   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |PR1|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |PB1|   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |PR1|   |   |PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |PB1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    /**
     * Un plateau pour tester le niveau 4.
     */
    final String PLATEAU_NIVEAU4
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|PB2|   |PB2|   |   |   |   |PB1|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |PB1|   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |PB1|   |   |   |   |   |   |PR1|   |   |PR1|   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |PB1|PB1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|PR1|   |   |   |   |   |   |   |PR1|   |PB1|   |PR2|   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |PB1|   |PR2|PB1|   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |PB1|   |   |PB1|   |   |PR1|   |   |   |   |   |PR1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |PR1|PR1|   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |PB1|   |   |   |   |PB1|   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |PR1|PR1|   |   |PR1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    /**
     * Un plateau pour tester le niveau 5.
     */
    final String PLATEAU_NIVEAU5
            = """
                A   B   C   D   E   F   G   H   I   J   K   L   M   N 
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             a|   |   |   |   |   |   |PR1|PR2|PB2|   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             b|   |   |   |   |   |   |   |   |   |   |PR1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             c|   |PR1|   |   |PB1|   |   |PB1|   |   |   |   |PR1|   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             d|   |   |   |PR1|   |   |PB1|   |PB1|   |   |PB1|   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             e|   |   |   |   |   |   |PR2|   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             f|   |   |   |   |   |   |   |   |   |   |   |   |   |PR1|
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             g|   |   |   |   |   |   |PB1|   |PR1|   |PR1|PB2|   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             h|   |   |   |   |   |   |PB2|   |   |PB2|   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             i|   |   |   |   |   |   |   |   |   |PR1|   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             k|   |PB2|PB1|   |PR1|   |   |   |PR1|   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             n|   |   |PB1|   |PR1|   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    final String PLATEAU_NIVEAU6
            = """
                A   B   C   D   E   F   G   H   I   J   K   L   M   N 
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             a|   |   |   |   |   |   |   |   |   |   |PB1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             b|   |   |PB1|   |   |   |   |   |PR1|PR2|   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             c|   |   |PR1|   |   |   |   |   |PR2|   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             e|   |   |   |   |   |   |   |   |   |   |   |   |PR2|PR2|
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             i|   |   |   |   |   |   |   |   |   |   |PB1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             l|   |   |   |   |   |   |   |   |   |   |PB1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             m|   |PR1|   |   |PB1|   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             n|   |   |PB1|   |   |   |   |PB1|   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             """;
    
    final String PLATEAU_NIVEAU7
            = """
                A   B   C   D   E   F   G   H   I   J   K   L   M   N 
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             a|PR1|   |   |PR1|   |   |   |PB1|   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             b|   |PR1|PB1|   |   |   |   |   |   |   |   |   |PR1|   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             c|PB1|   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             f|   |   |   |PR1|PR2|PB1|   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             g|   |   |   |   |PB1|   |   |   |PR1|   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             h|   |   |   |   |   |   |   |PB1|   |   |PB1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             j|   |PB1|   |   |   |   |PR1|   |   |   |   |   |   |PB1|
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             k|PB2|   |PR2|PR1|   |PR2|   |   |   |   |   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             l|PB1|   |   |   |   |PR1|   |   |   |PR1|   |   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             m|   |   |PB1|   |   |   |   |   |   |   |   |   |PB1|PB2|
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
             n|   |   |   |   |   |PR2|PR1|   |   |   |PB1|   |   |   |
              +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
}
