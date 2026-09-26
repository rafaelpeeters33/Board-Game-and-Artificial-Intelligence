package biosphere7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe IABiosphere7
 */
public class IABiosphere7Test {
    
  
    @Test
    public void testMettreAJour() {
        Case[][] plateau;
        // planter sur une case vide, loin de tout
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('l', 'H', plateau);
        verifierVide('k', 'H', plateau);
        IABiosphere7.mettreAJour(plateau, "HlH", Case.CAR_BLEU);
        verifierCase('l', 'H', 'H', 1, Case.CAR_BLEU, plateau);
        verifierVide('k', 'H', plateau);
        // couper une plante isolée
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierCase('c', 'C', Case.CAR_POMMIER, 6, Case.CAR_BLEU, plateau);
        verifierVide('c', 'B', plateau);
        IABiosphere7.mettreAJour(plateau, "CcC", Case.CAR_BLEU);
        verifierVide('c', 'C', plateau);
        verifierVide('c', 'C', plateau);
        // couper une plante avec des voisines, loin des bords
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierCase('c', 'J', Case.CAR_POMMIER, 1, Case.CAR_BLEU, plateau);
        verifierVide('c', 'I', plateau);
        verifierCase('b', 'J', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
        verifierCase('c', 'K', Case.CAR_POMMIER, 8, Case.CAR_ROUGE, plateau);
        verifierCase('d', 'J', Case.CAR_POMMIER, 2, Case.CAR_BLEU, plateau);
        verifierCase('d', 'K', Case.CAR_POMMIER, 1, Case.CAR_BLEU, plateau);
        IABiosphere7.mettreAJour(plateau, "CcJ", Case.CAR_BLEU);
        verifierVide('c', 'J', plateau);
        verifierVide('c', 'I', plateau);
        verifierCase('b', 'J', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
        verifierCase('c', 'K', Case.CAR_POMMIER, 9, Case.CAR_ROUGE, plateau);
        verifierCase('d', 'J', Case.CAR_POMMIER, 3, Case.CAR_BLEU, plateau);
        verifierCase('d', 'K', Case.CAR_POMMIER, 1, Case.CAR_BLEU, plateau);
        // couper une plante avec des voisines, proche des bords
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierCase('n', 'N', Case.CAR_POMMIER, 3, Case.CAR_ROUGE, plateau);
        verifierCase('n', 'M', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('m', 'N', Case.CAR_POMMIER, 2, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "CnN", Case.CAR_BLEU);
        verifierVide('n', 'N', plateau);
        verifierCase('n', 'M', Case.CAR_POMMIER, 5, Case.CAR_BLEU, plateau);
        verifierCase('m', 'N', Case.CAR_POMMIER, 3, Case.CAR_ROUGE, plateau);
        // planter avec symbiose
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('i', 'E', plateau);
        verifierCase('i', 'D', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
        verifierCase('h', 'E', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('h', 'F', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('i', 'F', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
        verifierVide('j', 'E', plateau);
        IABiosphere7.mettreAJour(plateau, "BiE", Case.CAR_BLEU);
        verifierCase('i', 'E', 'B', 3, Case.CAR_BLEU, plateau);
        verifierCase('i', 'D', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
        verifierCase('h', 'E', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('h', 'F', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('i', 'F', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
        verifierVide('j', 'E', plateau);
        // étouffement
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('g', 'F', plateau);
        verifierCase('f', 'F', Case.CAR_POMMIER, 6, Case.CAR_BLEU, plateau);
        verifierCase('g', 'E', 'D', 2, Case.CAR_BLEU, plateau);
        verifierCase('h', 'F', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('m', 'B', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "PgF", Case.CAR_BLEU);
        verifierCase('g', 'F', Case.CAR_POMMIER, 4, Case.CAR_BLEU, plateau);
        verifierCase('f', 'F', Case.CAR_POMMIER, 6, Case.CAR_BLEU, plateau);
        verifierVide('g', 'E', plateau);
        verifierVide('h', 'F', plateau);
        verifierCase('m', 'B', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
        // pas d'étouffement sur un bord
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('e', 'N', plateau);
        IABiosphere7.mettreAJour(plateau, "SeN", Case.CAR_ROUGE);
        verifierCase('e', 'N', 'S', 1, Case.CAR_ROUGE, plateau);
        // planter des pommes de terre
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('e', 'N', plateau);
        IABiosphere7.mettreAJour(plateau, "DeN", Case.CAR_ROUGE);
        verifierCase('e', 'N', 'D', 1, Case.CAR_ROUGE, plateau);
        // planter des tomates
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierVide('e', 'N', plateau);
        IABiosphere7.mettreAJour(plateau, "TeN", Case.CAR_ROUGE);
        verifierCase('e', 'N', 'T', 1, Case.CAR_ROUGE, plateau);
        // fertiliser : catégories
        plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
        verifierCase('m', 'B', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FmB", Case.CAR_ROUGE);
        verifierCase('m', 'B', Case.CAR_POMMIER, 2, Case.CAR_ROUGE, plateau);
        verifierCase('l', 'B', 'S', 3, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FlB", Case.CAR_ROUGE);
        verifierCase('l', 'B', 'S', 4, Case.CAR_ROUGE, plateau);
        verifierCase('n', 'B', 'B', 6, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FnB", Case.CAR_ROUGE);
        verifierCase('n', 'B', 'B', 8, Case.CAR_ROUGE, plateau);
        verifierCase('g', 'E', 'D', 2, Case.CAR_BLEU, plateau);
        IABiosphere7.mettreAJour(plateau, "FgE", Case.CAR_ROUGE);
        verifierCase('g', 'E', 'D', 5, Case.CAR_BLEU, plateau);
        verifierCase('m', 'C', 'T', 1, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FmC", Case.CAR_ROUGE);
        verifierCase('m', 'C', 'T', 4, Case.CAR_ROUGE, plateau);
        verifierCase('g', 'D', 'H', 3, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FgD", Case.CAR_ROUGE);
        verifierCase('g', 'D', 'H', 6, Case.CAR_ROUGE, plateau);
        // fertiliser : limite de 9
        verifierCase('a', 'N', 'D', 7, Case.CAR_ROUGE, plateau);
        IABiosphere7.mettreAJour(plateau, "FaN", Case.CAR_ROUGE);
        verifierCase('a', 'N', 'D', 9, Case.CAR_ROUGE, plateau);
    }

    /**
     * Vérifier les attributs d'une case.
     *
     * @param carLigne ligne de la case à vérifier
     * @param carColonne colonne de la case à vérifier
     * @param espece l'espèce attendue dans cette case
     * @param vitalite la vitalité attendue dans cette case
     * @param couleur la couleur attendue dans cette case
     * @param plateau le plateau considéré
     */
    void verifierCase(char carLigne, char carColonne,
            char espece, int vitalite, char couleur, Case[][] plateau) {
        Coordonnees coord = Coordonnees.depuisCars(carLigne, carColonne);
        Case laCase = plateau[coord.ligne][coord.colonne];
        assertEquals(espece, laCase.espece);
        assertEquals(vitalite, laCase.vitalite);
        assertEquals(couleur, laCase.couleur);
    }

    /**
     * Vérifier qu'une case est vide.
     *
     * @param carLigne ligne de la case à vérifier
     * @param carColonne colonne de la case à vérifier
     * @param plateau le plateau considéré
     */
    void verifierVide(char carLigne, char carColonne, Case[][] plateau) {
        verifierCase(carLigne, carColonne,
                Case.CAR_VIDE, 0, Case.CAR_ROUGE, plateau);
    }

    @Test
    public void testSuivant() {
        assertEquals(Case.CAR_ROUGE, IABiosphere7.suivant(Case.CAR_BLEU));
        assertEquals(Case.CAR_BLEU, IABiosphere7.suivant(Case.CAR_ROUGE));
    }

    @Test
    public void testPlanter() {
        {
            // planter sur une case isolée
            Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
            Coordonnees coord = Coordonnees.depuisCars('h', 'M');
            verifierVide('h', 'M', plateau);
            IABiosphere7.planter(coord, plateau, Case.CAR_BLEU, 'P');
            verifierCase('h', 'M', Case.CAR_POMMIER, 1, Case.CAR_BLEU, plateau);
        }
        {
            // planter au milieu de voisines de plusieurs couleurs
            Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
            Coordonnees coord = Coordonnees.depuisCars('b', 'K');
            verifierVide('b', 'K', plateau);
            IABiosphere7.planter(coord, plateau, Case.CAR_ROUGE, 'P');
            verifierCase('b', 'K', Case.CAR_POMMIER, 3, Case.CAR_ROUGE, plateau);
            // cases voisines
            verifierCase('b', 'J', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
            verifierCase('b', 'L', Case.CAR_POMMIER, 1, Case.CAR_ROUGE, plateau);
            verifierVide('a', 'K', plateau);
            verifierCase('c', 'K', Case.CAR_POMMIER, 8, Case.CAR_ROUGE, plateau);
        }
    }

    @Test
    public void testNbVoisinesJoueur() {
        Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
        // une case loin de tout
        assertEquals(0, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('b', 'B'), plateau, Case.CAR_ROUGE));
        // une case avec une voisine appartenant à l'adversaire
        assertEquals(0, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('b', 'I'), plateau, Case.CAR_ROUGE));
        // une case avec une voisine du joueur, et d'autres jouxtant en diagonale
        assertEquals(1, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('c', 'I'), plateau, Case.CAR_BLEU));
        // une case avec 2 voisines du joueur, d'autres de l'adversaire
        assertEquals(2, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('b', 'K'), plateau, Case.CAR_ROUGE));
        // test dans un coin
        assertEquals(1, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('n', 'N'), plateau, Case.CAR_ROUGE));
        assertEquals(1, IABiosphere7.nbVoisinesJoueur(
                Coordonnees.depuisCars('n', 'N'), plateau, Case.CAR_BLEU));
    }
    
    @Test
    public void testNbVoisines() {
        Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
        // Une case loin de tou
        assertEquals(0, IABiosphere7.nbVoisines(
                Coordonnees.depuisCars('b', 'B'), plateau));
        // Une case avec une seule voisine 
        assertEquals(1, IABiosphere7.nbVoisines(
                Coordonnees.depuisCars('b', 'I'), plateau));
        // Une case avec plusieurs voisines de couleurs différentes
        assertEquals(3, IABiosphere7.nbVoisines(
                Coordonnees.depuisCars('b', 'K'), plateau));
        // Test dans un coin 
        assertEquals(2, IABiosphere7.nbVoisines(
                Coordonnees.depuisCars('n', 'N'), plateau));
    }
    
    @Test
    public void testCouper() {
        {
            // couper un arbre isolé
            Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
            Coordonnees coord = Coordonnees.depuisCars('c', 'C');
            verifierCase('c', 'C', Case.CAR_POMMIER, 6, Case.CAR_BLEU, plateau);
            verifierVide('b', 'C', plateau);
            verifierVide('d', 'C', plateau);
            verifierVide('c', 'B', plateau);
            verifierVide('c', 'D', plateau);
            IABiosphere7.couper(coord, plateau);
            verifierVide('c', 'C', plateau);
            verifierVide('b', 'C', plateau);
            verifierVide('d', 'C', plateau);
            verifierVide('c', 'B', plateau);
            verifierVide('c', 'D', plateau);
        }
        {
            // couper un arbre avec des voisins
            Case[][] plateau = Utils.plateauDepuisTexte(UN_PLATEAU);
            Coordonnees coord = Coordonnees.depuisCars('c', 'J');
            verifierCase('c', 'J', Case.CAR_POMMIER, 1, Case.CAR_BLEU, plateau);
            verifierCase('b', 'J', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
            verifierCase('d', 'J', Case.CAR_POMMIER, 2, Case.CAR_BLEU, plateau);
            verifierCase('c', 'K', Case.CAR_POMMIER, 8, Case.CAR_ROUGE, plateau);
            verifierVide('c', 'I', plateau);
            IABiosphere7.couper(coord, plateau);
            verifierVide('c', 'J', plateau);
            verifierCase('b', 'J', Case.CAR_POMMIER, 9, Case.CAR_BLEU, plateau);
            verifierCase('d', 'J', Case.CAR_POMMIER, 3, Case.CAR_BLEU, plateau);
            verifierCase('c', 'K', Case.CAR_POMMIER, 9, Case.CAR_ROUGE, plateau);
            verifierVide('c', 'I', plateau);
        }
    }

    @Test
    public void testFertiliser() {
        {
            Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
            // On prend un Pommier de vitalité 6
            Coordonnees coord = Coordonnees.depuisCars('c', 'C');
            IABiosphere7.fertiliser(coord, plateau);
            // On fertilise donc vitalité = 7
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 7);
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité = 8
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 8);
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité = 9
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 9);
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité reste à 9
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 9);
        }
        {
            Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
            // On prend un Haricot de vitalité 3
            Coordonnees coord = Coordonnees.depuisCars('g', 'D');
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité = 6 (+3)
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 6);
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité = 9 (+3)
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 9);
            IABiosphere7.fertiliser(coord, plateau);
            // Donc vitalité reste à 9
            assertTrue(plateau[coord.ligne][coord.colonne].vitalite == 9);
        }
    }

    @Test
    public void testEtouffement() {
        {
            // Etouffer 2 plantes en même temps : 
            Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
            // Vérification état initial
            verifierCase('g', 'E', 'D', 2, Case.CAR_BLEU, plateau);
            verifierCase('h', 'F', 'P', 4, Case.CAR_BLEU, plateau);
            // On plante afin d'étouffer les voisines
            Coordonnees coord = Coordonnees.depuisCars('g', 'F');
            IABiosphere7.planter(coord, plateau, Case.CAR_BLEU, 'P');
            // La nouvelle plante est bien là
            verifierCase('g', 'F', 'P', 4, Case.CAR_BLEU, plateau);
            // Les deux plantes voisines ont été étouffées (devenues vides)
            verifierVide('g', 'E', plateau);
            verifierVide('h', 'F', plateau);
        }
        {
            // Cas d'un bord (pas etouffement possible)
            Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU_8);
            // Vérification état initial
            verifierVide('e', 'N', plateau);
            // On plante sur le bord
            Coordonnees coord = Coordonnees.depuisCars('e', 'N');
            IABiosphere7.planter(coord, plateau, Case.CAR_ROUGE, 'S');
            // La plante dN doit survivre car elle est sur le bord
            verifierCase('d', 'N', 'P', 6, Case.CAR_BLEU, plateau);
        }
    }

    final String UN_PLATEAU
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |PB9|   |PR1|   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |PB6|   |   |   |   |   |   |PB1|PR8|   |   |PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |PB2|PB1|   |PR2|PB6|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |PR1|PB6|   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |PR3|PB2|   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |PB4|PB4|PR5|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |PR1|   |PB9|   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|PR1|PR1|PR1|   |   |   |   |   |   |   |   |   |   |PR2|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |PR1|   |   |   |   |   |   |   |   |   |   |PB4|PR3|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    final String PLATEAU_NIVEAU_8
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |DR7|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |PB9|   |PR1|   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |PB6|   |   |   |   |   |   |PB1|PR8|   |   |PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |PB2|PB1|   |PR2|PB6|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |PR1|PB6|   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |HR3|DB2|   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |PB4|PB4|PR5|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |PR1|   |PB9|   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |SR3|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|PR1|PR1|TR1|   |   |   |   |   |   |   |   |   |   |PR2|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |BR6|   |   |   |   |   |   |   |   |   |   |PB4|PR3|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
}