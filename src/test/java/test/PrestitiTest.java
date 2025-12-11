/**
 * @file PrestitiTest.java
 * @brief Test unitario per la classe Prestiti.
 */
package test;
import Model.Prestiti;
import Model.Prestito;
import Model.Utente;
import Model.Libro;
import Model.Autore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class PrestitiTest {
    private Prestiti prestiti;
    private Prestito prestito1,prestito2;
    
    //Dato che Prestiti dipende da Utente, Libro usiamo oggetti di supporto (Stubs)
    private Utente utente1;
    private Libro libro1;
    
    
    @BeforeEach
    void setUp(){
        //Inizializzo oggetti di supporto
        utente1 = new Utente ("Luca", "Cipriano", "0612707386", "l.cipriano12@studenti.unisa.it",0);
        
        // Per Libro serve una lista di autori
        ArrayList<Autore> autori = new ArrayList<>();
        Collections.addAll(autori, new Autore("Andrea","Torrente"),new Autore("Piero","Schlesinger"));
        
        libro1 = new Libro("Manuale di Diritto Privato", autori, 2025, "9788828869269", 10, 9);
        
        //Oggetti Prestito
        LocalDate dataScadenza1 = LocalDate.now().plusDays(14);
        LocalDate dataScadenza2 = LocalDate.now().plusDays(5);
        
        prestito1 = new Prestito(utente1,libro1,dataScadenza1);
        prestito2 = new Prestito(utente1,libro1,dataScadenza2);
        
        //Oggetto Prestiti
        prestiti = new Prestiti();
    }
    
    
    // --- Test Costruttori e Getter ---

    @Test
    public void testCostruttoreDefault_ListaVuota() {
        // ASSERT
        assertNotNull(prestiti.getPrestiti(), "La lista prestiti non deve essere null dopo il costruttore default.");
        assertTrue(prestiti.getPrestiti().isEmpty(), "La lista prestiti deve essere vuota dopo il costruttore default.");
    }
    
    @Test
    public void testCostruttoreParametrizzato_ListaNonNull() {
        ArrayList<Prestito> listaIniziale = new ArrayList<>();
        listaIniziale.add(prestito1);

        Prestiti nuovaLista = new Prestiti(listaIniziale);
        
        // ASSERT
        assertEquals(1, nuovaLista.getPrestiti().size(), "La dimensione della lista deve essere pari ad 1.");
        assertTrue(nuovaLista.getPrestiti().contains(prestito1), "La lista deve contenere il prestito1.");
    }

    @Test
    public void testCostruttoreParametrizzato_ListaNull() {
        Prestiti listaNull = new Prestiti(null);
        
        // ASSERT
        assertNotNull(listaNull.getPrestiti(), "La lista deve essere inizializzata vuota se si passa null.");
        assertTrue(listaNull.getPrestiti().isEmpty(), "La lista deve essere vuota se si passa null.");
    }
    
    // --- Test Aggiungi Prestito ---

    @Test
    public void testAggiungiPrestito_Successo() throws Exception {
        prestiti.aggiungiPrestito(prestito1);

        // ASSERT
        assertEquals(1, prestiti.getPrestiti().size(), "Dopo l'aggiunta, la lista deve contenere 1 prestito.");
        assertTrue(prestiti.getPrestiti().contains(prestito1), "Il prestito A deve esistere nella lista.");
    }
    
    @Test
    public void testAggiungiPrestito_Duplicato() throws Exception {
        //Aggiungo il prestito una volta
        prestiti.aggiungiPrestito(prestito1);
        
        //Provo ad aggiungere lo stesso prestito e verifico l'eccezione
        Exception ex = assertThrows(Exception.class, () -> {prestiti.aggiungiPrestito(prestito1);}, 
                                    "Deve lanciare un'eccezione se si aggiunge un duplicato.");
        
        // Verifico il messaggio d'errore
        assertTrue(ex.getMessage().contains("ERRORE DUPLICATO: Il prestito risulta già registrato nel sistema."));
        assertEquals(1, prestiti.getPrestiti().size(), "La lista non deve aumentare di dimensione dopo l'errore.");
    }
    
    // --- Test Rimuovi Prestito ---

    @Test
    public void testRimuoviPrestito_Successo() throws Exception {
        prestiti.aggiungiPrestito(prestito1);
        prestiti.aggiungiPrestito(prestito2);
        assertEquals(2, prestiti.getPrestiti().size(), "La lista deve contenere due prestiti.");
        
        // ACT
        prestiti.rimuoviPrestito(prestito1);
        
        // ASSERT
        assertEquals(1, prestiti.getPrestiti().size(), "Dopo la rimozione, la lista deve contenere 1 prestito.");
        assertFalse(prestiti.getPrestiti().contains(prestito1), "Il prestito 1 non deve essere più presente.");
        assertTrue(prestiti.getPrestiti().contains(prestito2), "Il prestito 2 (non rimosso) deve ancora esistere.");
    }

    @Test
    public void testRimuoviPrestito_NonEsistente() {
        //Tenta di rimuovere un prestito che non esiste 
        
        Exception exception = assertThrows(Exception.class, () -> {
            prestiti.rimuoviPrestito(prestito1);
        }, "Deve lanciare un'eccezione se il prestito non è presente.");
        
        // Verifica il messaggio d'errore
        assertTrue(exception.getMessage().contains("ERRORE RIMOZIONE: Il prestito da rimuovere non è presente"));
    }
}
