/**
 * @file UtenteTest.java
 * @brief Test unitario per la classe Utente.
 */
package test;
import Model.Utente;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {
    private Utente utente;
    
    //DATI PER TEST
    private static final String NOME_INIZIALE = "Pasquale";
    private static final String COGNOME_INIZIALE = "Petraglia";
    private static final String MATRICOLA_INIZIALE = "0612708970";
    private static final String EMAIL_INIZIALE = "p.petraglia3@studenti.unisa.it";
    private static final int NUM_PRESTITI_ATTIVI = 1;
    
    private static final String NOME_NUOVO = "Andrea";
    private static final String COGNOME_NUOVO = "Rossomando";
    private static final String MATRICOLA_NUOVA = "0612708971";
    private static final String EMAIL_NUOVA = "a.rossomando6@studenti.unisa.it";
    
    // --- Test del Costruttore e dei Getter ---
    @Test
    public void testConstructorEGetter() {
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
    
        // 2. Assert
        assertEquals(NOME_INIZIALE, utente.getNome(), "Il nome deve corrispondere al valore passato nel costruttore.");
        assertEquals(COGNOME_INIZIALE, utente.getCognome(), "Il cognome deve corrispondere al valore passato nel costruttore.");
        assertEquals(MATRICOLA_INIZIALE,utente.getMatricola(), "La matricola deve corrispondere al valore passato nel costruttore.");
        assertEquals(EMAIL_INIZIALE,utente.getEmail(), "L'email deve corrispondere al valore passato nel costruttore.");
        assertEquals(NUM_PRESTITI_ATTIVI,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi deve corrispondere al valore passato nel costruttore");
    }
    
     
    // --- Test dei Setter ---
    @Test
    public void testSetNome(){
        utente.setNome(NOME_NUOVO);
        
        // Assert
        assertEquals(NOME_NUOVO, utente.getNome(), "Il nome deve essere aggiornato dal setter del nome.");
        assertEquals(COGNOME_INIZIALE, utente.getCognome(), "Il cognome non deve essere modificato dal setter del nome.");
        assertEquals(MATRICOLA_INIZIALE,utente.getMatricola(), "La matricola non deve essere modificato dal setter del nome.");
        assertEquals(EMAIL_INIZIALE,utente.getEmail(), "L'email non deve essere modificato dal setter del nome.");
        assertEquals(NUM_PRESTITI_ATTIVI,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi non deve essere modificato dal setter del nome.");
        
    }
    
    @Test
    public void testSetCognome(){
        utente.setCognome(COGNOME_NUOVO);
        
        // Assert
        assertEquals(NOME_INIZIALE, utente.getNome(), "Il nome non deve essere aggiornato dal setter del cognome.");
        assertEquals(COGNOME_NUOVO, utente.getCognome(), "Il cognome deve essere modificato dal setter del cognome.");
        assertEquals(MATRICOLA_INIZIALE,utente.getMatricola(), "La matricola non deve essere modificato dal setter del cognome.");
        assertEquals(EMAIL_INIZIALE,utente.getEmail(), "L'email non deve essere modificato dal setter del cognome.");
        assertEquals(NUM_PRESTITI_ATTIVI,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi non deve essere modificato dal setter del cognome.");
    
    }
    
    @Test
    public void testSetMatricola(){
        utente.setMatricola(MATRICOLA_NUOVA);
        
        // Assert
        assertEquals(NOME_INIZIALE, utente.getNome(), "Il nome non deve essere aggiornato dal setter della matricola.");
        assertEquals(COGNOME_INIZIALE, utente.getCognome(), "Il cognome non deve essere modificato dal setter della matricola.");
        assertEquals(MATRICOLA_NUOVA,utente.getMatricola(), "La matricola deve essere modificata dal setter della matricola.");
        assertEquals(EMAIL_INIZIALE,utente.getEmail(), "L'email non deve essere modificato dal setter della matricola.");
        assertEquals(NUM_PRESTITI_ATTIVI,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi non deve essere modificato dal setter della matricola.");
        
    }
    
    @Test
    public void testSetEmail(){
        utente.setEmail(EMAIL_NUOVA);
        
        // Assert
        assertEquals(NOME_INIZIALE, utente.getNome(), "Il nome non deve essere aggiornato dal setter dell'email.");
        assertEquals(COGNOME_INIZIALE, utente.getCognome(), "Il cognome non deve essere modificato dal setter dell'email.");
        assertEquals(MATRICOLA_INIZIALE,utente.getMatricola(), "La matricola non deve essere modificato dal setter dell'email.");
        assertEquals(EMAIL_NUOVA,utente.getEmail(), "L'email deve essere modificato dal setter dell'email.");
        assertEquals(NUM_PRESTITI_ATTIVI,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi non deve essere modificato dal setter dell'email.");
        
    }
    
    
    @Test
    public void testSetNumPrestitiAttivi(){
        utente.setNumPrestitiAttivi(5);
        
        // Assert
        assertEquals(NOME_INIZIALE, utente.getNome(), "Il nome non deve essere aggiornato dal setter dei prestiti.");
        assertEquals(COGNOME_INIZIALE, utente.getCognome(), "Il cognome non deve essere modificato dal setter dei prestiti.");
        assertEquals(MATRICOLA_INIZIALE,utente.getMatricola(), "La matricola non deve essere modificato dal setter dei prestiti.");
        assertEquals(EMAIL_INIZIALE,utente.getEmail(), "L'email non deve essere modificato dal setter dei prestiti.");
        assertEquals(5,utente.getNumPrestitiAttivi(),"Il numero di prestiti attivi deve essere modificato dal setter dei prestiti.");
        
    }
    
    // --- Test della Logica di Business ---

    /**
     * Test sul metodo limitePrestiti(): il limite è 3.
     */
    @Test
    public void testLimitePrestiti_SottoLimiteBasso(){
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,0);
        assertFalse(utente.limitePrestiti(), "Deve ritornare FALSE se il numero di prestiti attivi è <= 3.");
    }
    
    public void testLimitePrestiti_SottoLimite(){
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,1);
        assertFalse(utente.limitePrestiti(), "Deve ritornare FALSE se il numero di prestiti attivi è <= 3.");
    }
    
    public void testLimitePrestiti_SottoLimiteAlto(){
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,2);
        assertFalse(utente.limitePrestiti(), "Deve ritornare FALSE se il numero di prestiti attivi è <= 3.");
    }
    
    public void testLimitePrestiti_AlLimite(){
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,3);
        assertFalse(utente.limitePrestiti(), "Deve ritornare TRUE se il numero di prestiti attivi è = 3.(al limite)");
    }
    
    public void testLimitePrestiti_SopraLimite(){
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,4);
        assertFalse(utente.limitePrestiti(), "Deve ritornare TRUE se il numero di prestiti attivi è >= 3.(sopra al limite)");
    }
    
    
    // --- Test della Logica di Business: inPrestito() ---
    // Restituisce true se numPrestitiAttivi > 0

    @Test
    void testInPrestito_Zero() {
        // Utente senza prestiti attivi
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,0);
        assertFalse(utente.inPrestito(), "inPrestito() deve essere FALSE con 0 prestiti.");
    }

    @Test
    void testInPrestito_Uno() {
        // Utente con prestiti attivi
        utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,1);
        assertTrue(utente.inPrestito(), "inPrestito() deve essere TRUE con prestito > 0");
    }
    
    
    @Test
    void testIncrementaPrestitiAttivi() {
        // 1. Arrange: Inizializza con 1 prestito
        Utente utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
        
        // 2. Act: Incrementa
        utente.incrementaPrestitiAttivi();
        
        // 3. Assert: Verifica che sia passato a 2
        assertEquals(2, utente.getNumPrestitiAttivi(), "Dopo l'incremento, i prestiti attivi dovrebbero essere 2.");
    }
    
    @Test
    void testDecrementaPrestitiAttivi() {
        // 1. Arrange: Inizializza con 1 prestito
        Utente utente = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,2);
        
        // 2. Act: Incrementa
        utente.decrementaPrestitiAttivi();
        
        // 3. Assert: Verifica che sia passato a 2
        assertEquals(1, utente.getNumPrestitiAttivi(), "Dopo la diminuzione, i prestiti attivi dovrebbero essere 1.");
    }
    
    
    // --- Test di toString() ---
    @Test
    public void testToString(){
        String expectedString = String.format("%s %s (Matr: %s, Email: %s) - Prestiti Attivi: %d", 
            NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
        //Assert
        assertEquals(expectedString, utente.toString(), "toString() deve restituire la stringa formattata.");
    }
    
    
    // --- Test di equals() ---
    @Test
    public void testEquals_stessaIstanza(){
        assertTrue(utente.equals(this),"L'oggetto deve essere uguale a sé stesso.");
    }
    
    @Test
    public void testEquals_OggettiUguali(){
        //Istanzio due autori con gli stessi dati
        Utente utente1 = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
        Utente utente2 = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI); 
        
        //Assert
        assertTrue(utente1.equals(utente2),"Due oggetti con stessi dati devono essere uguali.");
    }
    
    @Test
    public void testEquals_MatricolaUguale(){
        //Istanzio due autori con stessa matricola
        Utente utente1 = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
        Utente utente2 = new Utente(NOME_NUOVO,COGNOME_NUOVO,MATRICOLA_INIZIALE,EMAIL_NUOVA,NUM_PRESTITI_ATTIVI); 
        
        //Assert
        assertTrue(utente1.equals(utente2),"Due oggetti con la stessa matricola devono essere uguali.");  
    }
    
    @Test
    public void testEquals_MatricolaDiversa(){
        //Istanzio due autori con matricola diversa
        Utente utente1 = new Utente(NOME_INIZIALE,COGNOME_INIZIALE,MATRICOLA_INIZIALE,EMAIL_INIZIALE,NUM_PRESTITI_ATTIVI);
        Utente utente2 = new Utente(NOME_NUOVO,COGNOME_NUOVO,MATRICOLA_NUOVA,EMAIL_NUOVA,NUM_PRESTITI_ATTIVI); 
        
        //Assert
        assertFalse(utente1.equals(utente2),"Due oggetti con la matricola diversa non devono essere uguali."); 
    }
    
    @Test
    public void testEquals_NULL(){
        //Assert
        assertFalse(utente.equals(null), "L'oggetto non deve essere uguale a NULL.");
    }
    
    @Test
    public void testEquals_AltraClasse(){
        //Istanzio oggetto appartenente ad un'altra classe
        Object altraClasse = new Object(); 
        
        //Assert
        assertFalse(utente.equals(altraClasse), "L'oggetto non deve essere uguale ad un oggetto di un'altra classe.");
    }
}

