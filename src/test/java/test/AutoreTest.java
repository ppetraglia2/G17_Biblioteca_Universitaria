/**
 * @file AutoreTest.java
 * @brief Test unitario per la classe Autore.
 */
package test;
import Model.Autore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class AutoreTest {
    private Autore autore;
    
    //DATI PER TEST
    private static final String NOME_INIZIALE = "Alessandro";
    private static final String COGNOME_INIZIALE = "Manzoni";
    private static final String NOME_NUOVO = "Italo";
    private static final String COGNOME_NUOVO = "Calvino";
    
    
    @BeforeEach
    void setUp() {
        // Inizializza l'oggetto base prima di ogni test
        this.autore = new Autore(NOME_INIZIALE, COGNOME_INIZIALE);
    }

    // --- Test del Costruttore e dei Getter ---
    @Test
    public void testGetter() {
        // 2. Assert
        assertEquals(NOME_INIZIALE, autore.getNome(), "Il nome deve corrispondere al valore passato nel costruttore.");
        assertEquals(COGNOME_INIZIALE, autore.getCognome(), "Il cognome deve corrispondere al valore passato nel costruttore.");
        
    }
    
    // --- Test dei Setter ---
    @Test
    public void testSetNome(){
        autore.setNome(NOME_NUOVO);
        
        // Assert
        assertEquals(NOME_NUOVO, autore.getNome(), "Il nome deve essere aggiornato dal setter.");
        assertEquals(COGNOME_INIZIALE, autore.getCognome(), "Il cognome non deve essere modificato dal setter del nome.");
    }
    
    @Test
    public void testSetCognome(){
        autore.setCognome(COGNOME_NUOVO);
        
        // Assert
        assertEquals(NOME_INIZIALE, autore.getNome(), "Il nome non deve essere aggiornato dal setter del cognome.");
        assertEquals(COGNOME_NUOVO, autore.getCognome(), "Il cognome deve essere modificato dal setter");
    }
    
    // --- Test di toString() ---
    @Test
    public void testToString(){
        String expectedString = NOME_INIZIALE + " " + COGNOME_INIZIALE;

        //Assert
        assertEquals(expectedString, autore.toString(), "toString() deve restituire la stringa 'nome cognome'.");
    }
    
    
    // --- Test di equals() ---
    @Test
    public void testEquals_stessaIstanza(){
        assertTrue(autore.equals(autore),"L'oggetto deve essere uguale a sé stesso.");
    }
    
    @Test
    public void testEquals_OggettiUguali(){
        //Istanzio due autori con gli stessi dati
        Autore autore2 = new Autore(NOME_INIZIALE, COGNOME_INIZIALE); 
        
        //Assert
        assertTrue(autore.equals(autore2),"Due oggetti con stesso nome e cognome devono essere uguali.");
    }
    
    @Test
    public void testEquals_NomeDiverso(){
        //Istanzio due autori con nomi diversi
        Autore autore2 = new Autore(NOME_NUOVO, COGNOME_INIZIALE);
        
        //Assert 
        assertFalse(autore.equals(autore2), "Due oggetti con nome diverso non devono essere uguali.");  
    }
    
    @Test
    public void testEquals_CognomeDiverso(){
        //Istanzio due autori con cognomi diversi
        Autore autore2 = new Autore(NOME_INIZIALE, COGNOME_NUOVO);
        
        //Assert 
        assertFalse(autore.equals(autore2), "Due oggetti con cognome diverso non devono essere uguali.");
    }
    
    @Test
    public void testEquals_NULL(){
        //Assert
        assertFalse(autore.equals(null), "L'oggetto non deve essere uguale a NULL.");
    }
    
    @Test
    public void testEquals_AltraClasse(){
        //Istanzio oggetto appartenente ad un'altra classe
        Object altraClasse = new Object(); 
        
        //Assert
        assertFalse(autore.equals(altraClasse), "L'oggetto non deve essere uguale ad un oggetto di un'altra classe.");
    }
    
    
    // --- Test di hashCode() ---
    
    @Test
    void testHashCode_OggettiUguali() {
        //Istanzio due autori con gli stessi dati
        Autore autore2 = new Autore(NOME_INIZIALE, COGNOME_INIZIALE);
        
        //Assert
        assertEquals(autore.hashCode(),autore2.hashCode(),"L'hashcode deve essere uguale per due oggetti uguali."); 
    }
    
    @Test
    void testHashCode_OggettiDiversi() {
        //Istanzio due autori con dati diversi
        Autore autore2 = new Autore(NOME_NUOVO, COGNOME_NUOVO);
        
        //Assert
        assertNotEquals(autore.hashCode(),autore2.hashCode(),"L'hashcode non deve essere uguale per due oggetti diversi."); 
    }
    
}
