/**
 * @file PrestitoTest.java
 * @brief Test unitario per la classe Prestito.
 */
package test;
import Model.Prestito;
import Model.Utente;
import Model.Libro;
import Model.Autore; 
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PrestitoTest {
    
    private Prestito prestito;
    
    //Dato che Prestito dipende da Utente, Libro usiamo oggetti di supporto (Stubs)
    private Utente utenteIniziale;
    private Libro libroIniziale;
    
    //DATI PER TEST
    private final LocalDate OGGI = LocalDate.now();
    private final LocalDate DATA_NON_SCADUTA = OGGI.plusDays(14);
    private final LocalDate DATA_SCADUTA = OGGI.minusDays(14);
    
    @BeforeEach
    void setUp(){
        //Inizializzo oggetti di supporto
        utenteIniziale = new Utente ("Marco", "Rossi", "0612707788", "m.rossi4@studenti.unisa.it",1);
        
        // Per Libro serve una lista di autori
        ArrayList<Autore> autori = new ArrayList<>();
        autori.add(new Autore("Dante", "Alighieri"));

        libroIniziale = new Libro("La Divina Commedia", autori, 1320, "9788809750730", 10, 9);
        
        //Inizializzo il prestito base
        this.prestito = new Prestito(utenteIniziale,libroIniziale,DATA_NON_SCADUTA);
    }
    
    
    // --- Test dei Getter ---

    @Test
    public void testGetter() {
        // ASSERT
        assertEquals(utenteIniziale, prestito.getUtente(), "L'utente deve corrispondere a quello passato nel costruttore.");
        assertEquals(libroIniziale, prestito.getLibro(), "Il libro deve corrispondere a quello passato nel costruttore.");
        assertEquals(DATA_NON_SCADUTA, prestito.getDataRestituzione(), "La data deve corrispondere a quella passata nel costruttore.");
    }

    // --- Test dei Setter ---
    @Test
    public void testSetUtente() {
        // Creo Nuovo utente
        Utente nuovoUtente = new Utente("Giulia", "Russo", "0612708750", "g.russo7@studenti.unisa.it",0);

        prestito.setUtente(nuovoUtente);

        // ASSERT
        assertEquals(nuovoUtente, prestito.getUtente(), "Il setter deve aggiornare l'utente.");
        assertEquals(libroIniziale, prestito.getLibro(), "Il setter dell'Utente non deve modificare il libro.");
        assertEquals(DATA_NON_SCADUTA, prestito.getDataRestituzione(), "Il setter dell'Utente non deve modificare la data di restituzione.");
    }

    @Test
    public void testSetLibro() {
        
        // 1. Creazione dell'Autore
        ArrayList<Autore> autoriOrwell = new ArrayList<>();
        autoriOrwell.add(new Autore("George", "Orwell"));
        
        //Creo Nuovo libro
        Libro nuovoLibro = new Libro("1984",autoriOrwell,1949, "9788809750731", 5,5);

        prestito.setLibro(nuovoLibro);

        // ASSERT
        assertEquals(nuovoLibro, prestito.getLibro(), "Il setter deve aggiornare il libro.");
        assertEquals(utenteIniziale, prestito.getUtente(), "Il setter del libro non deve modificare l'utente.");
        assertEquals(DATA_NON_SCADUTA, prestito.getDataRestituzione(), "Il setter del libro non deve modificare la data.");
    }

    @Test
    public void testSetDataRestituzione() {
        // ARRANGE: Nuova data
        LocalDate nuovaData = OGGI.plusDays(30);

        prestito.setDataRestituzione(nuovaData);

        // ASSERT
        assertEquals(nuovaData, prestito.getDataRestituzione(), "Il setter deve aggiornare la data.");
        assertEquals(utenteIniziale, prestito.getUtente(), "Il setter della data di restituzione non deve modificare l'utente.");
        assertEquals(libroIniziale, prestito.getLibro(), "Il setter della data di restituzione non deve modificare il libro.");
    }
    
    
    // --- Test Logica di Ritardo ---

    @Test
    public void testControllaRitardo_NonInRitardo() {
        // prestito è impostato con DATA_NON_SCADUTA (oggi + 14 giorni)
        boolean inRitardo = prestito.controllaRitardo();

        // ASSERT
        assertFalse(inRitardo, "Il prestito non dovrebbe essere in ritardo se la data di scadenza è dopo la data di oggi.");
    }

    @Test
    public void testControllaRitardo_Scaduto() {
        //Imposto una data di restituzione nel passato (oggi - 14 giorni)
        Prestito prestitoScaduto = new Prestito(utenteIniziale, libroIniziale, DATA_SCADUTA);

        // ACT
        boolean inRitardo = prestitoScaduto.controllaRitardo();

        // ASSERT
        assertTrue(inRitardo, "Il prestito dovrebbe essere in ritardo se la data di scadenza è nel passato.");
    }
    
    @Test
    public void testControllaRitardo_ScadenzaOggi() {
        //Imposto una data di restituzione a oggi.
        Prestito prestitoOggi = new Prestito(utenteIniziale, libroIniziale, OGGI);

        boolean inRitardo = prestitoOggi.controllaRitardo();

        // ASSERT: LocalDate.now().isAfter(OGGI) è falso.
        assertFalse(inRitardo, "Il prestito non dovrebbe essere in ritardo se la scadenza è esattamente oggi.");
    }
    
    
    // --- Test toString ---

    @Test
    public void testToString_NonInRitardo() {
        // ASSERT
        String expectedString = String.format("Prestito: %s a %s %s. --- Scadenza: %s (Ritardo: NO)",
                libroIniziale.getTitolo(), utenteIniziale.getNome(), utenteIniziale.getCognome(), DATA_NON_SCADUTA.toString());

        assertEquals(expectedString, prestito.toString(), "toString() deve restituire la stringa formattata.");
    }

    @Test
    public void testToString_InRitardo() {
        //Prestito scaduto
        Prestito prestitoScaduto = new Prestito(utenteIniziale, libroIniziale, DATA_SCADUTA);

        // ASSERT
        String expectedString = String.format("Prestito: %s a %s %s. --- Scadenza: %s (Ritardo: SI)",
                libroIniziale.getTitolo(), utenteIniziale.getNome(), utenteIniziale.getCognome(), DATA_SCADUTA.toString());

        assertEquals(expectedString, prestitoScaduto.toString(), "toString() deve restituire la stringa formattata.");
    }
    
    
    // --- Test Equals ---

    @Test
    public void testEquals_stessaIstanza() {
        // ASSERT
        assertTrue(prestito.equals(prestito), "Un oggetto deve essere uguale a sé stesso.");
    }

    @Test
    public void testEquals_OggettiUguali() {
        //Istanzio un oggetto con gli stessi dati
        Prestito prestitoUguale = new Prestito(utenteIniziale, libroIniziale, DATA_NON_SCADUTA);

        // ASSERT
        assertTrue(prestito.equals(prestitoUguale), "Oggetti con gli stessi campi devono essere uguali.");
    }
    
    @Test
    public void testEquals_DiversoUtente() {
        //Istanzio un oggetto diverso solo per il campo Utente
        Utente altroUtente = new Utente("Giuseppe", "Ferrari", "0612709943", "g.ferrari78@studenti.unisa.it", 1);
        Prestito prestitoDiverso = new Prestito(altroUtente, libroIniziale, DATA_NON_SCADUTA);

        // ASSERT
        assertFalse(prestito.equals(prestitoDiverso), "Oggetti con Utente diverso non devono essere uguali.");
    }
    
    @Test
    public void testEquals_DiversoLibro() {
        //Istanzio un oggetto diverso solo per Libro
        Libro altroLibro = new Libro("Cime Tempestose", new ArrayList<>(), 1847, "9780195807332", 2, 2);
        Prestito prestitoDiverso = new Prestito(utenteIniziale, altroLibro, DATA_NON_SCADUTA);

        // ASSERT
        assertFalse(prestito.equals(prestitoDiverso), "Oggetti con Libro diverso non devono essere uguali.");
    }

    @Test
    public void testEquals_DiversaData() {
        //Istanzio un oggetto diverso solo per DataRestituzione
        Prestito prestitoDiverso = new Prestito(utenteIniziale, libroIniziale, OGGI.plusDays(10));

        // ASSERT
        assertFalse(prestito.equals(prestitoDiverso), "Oggetti con DataRestituzione diversa non devono essere uguali.");
    }
    
    @Test
    public void testEquals_NULL(){
        //Assert
        assertFalse(prestito.equals(null), "L'oggetto non deve essere uguale a NULL.");
    }
    
    @Test
    public void testEquals_AltraClasse(){
        //Istanzio oggetto appartenente ad un'altra classe
        Object altraClasse = new Object(); 
        
        //Assert
        assertFalse(prestito.equals(altraClasse), "L'oggetto non deve essere uguale ad un oggetto di un'altra classe.");
    }
    
    // --- Test di hashCode() ---
    
    @Test
    void testHashCode_OggettiUguali() {
        //Istanzio due oggetti con gli stessi dati
        Prestito prestito2 = new Prestito(utenteIniziale, libroIniziale, DATA_NON_SCADUTA);
        
        //Assert
        assertEquals(prestito.hashCode(),prestito2.hashCode(),"L'hashcode deve essere uguale per due oggetti uguali."); 
    }
    
    @Test
    void testHashCode_OggettiDiversi() {
        //Istanzio due oggetti con dati diversi
        Utente altroUtente = new Utente("Antonio", "Saggio", "0612709941", "a.saggio8@studenti.unisa.it", 1);
        Libro altroLibro = new Libro("Elementi di Analisi Matematica", new ArrayList<>(), 1847, "9788808062550", 1, 1);
        Prestito prestito2 = new Prestito(altroUtente, altroLibro, DATA_NON_SCADUTA);
        
        //Assert
        assertNotEquals(prestito.hashCode(),prestito2.hashCode(),"L'hashcode non deve essere uguale per due oggetti diversi."); 
    }

}
