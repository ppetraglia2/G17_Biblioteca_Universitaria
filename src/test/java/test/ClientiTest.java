/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import Model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
 *
 * @author Pasquale
 */
public class ClientiTest {
    private Clienti clienti;
    private Utente utente1, utente2;
    
    @BeforeEach
    void setUp(){
        //Oggetti utente
        utente1 = new Utente("Pasquale", "Petraglia", "0612708970", "p.petraglia3@studenti.unisa.it", 0);
        utente2 = new Utente("Andrea", "Rossomando", "0612708971", "a.rossomando6@studenti.unisa.it", 0);
        
        //Oggetto clienti
        clienti = new Clienti();
    }
    
    // --- Test Costruttori e Getter ---

    @Test
    public void testCostruttoreDefault_ListaVuota() {
        // ASSERT
        assertNotNull(clienti.getClienti(), "La lista clienti non deve essere null dopo il costruttore default.");
        assertTrue(clienti.getClienti().isEmpty(), "La lista clienti deve essere vuota dopo il costruttore default.");
    }
    
    @Test
    public void testCostruttoreParametrizzato_ListaNonNull() {
        ArrayList<Utente> listaIniziale = new ArrayList<>();
        listaIniziale.add(utente1);

        Clienti nuovaLista = new Clienti(listaIniziale);
        
        // ASSERT
        assertEquals(1, nuovaLista.getClienti().size(), "La dimensione della lista deve essere pari ad 1.");
        assertTrue(nuovaLista.getClienti().contains(utente1), "La lista deve contenere l'utente1.");
    }

    @Test
    public void testCostruttoreParametrizzato_ListaNull() {
        Clienti listaNull = new Clienti(null);
        
        // ASSERT
        assertNotNull(listaNull.getClienti(), "La lista deve essere inizializzata vuota se si passa null.");
        assertTrue(listaNull.getClienti().isEmpty(), "La lista deve essere vuota se si passa null.");
    }
    
    // --- Test Aggiungi Utente ---

    @Test
    public void testAggiungiUtente_Successo() throws Exception {
        clienti.aggiungiUtente(utente1);

        // ASSERT
        assertEquals(1, clienti.getClienti().size(), "Dopo l'aggiunta, la lista deve contenere 1 utente.");
        assertTrue(clienti.getClienti().contains(utente1), "L'utente1 deve esistere nella lista.");
    }
    
    @Test
    public void testAggiungiUtente_Duplicato() throws Exception {
        //Aggiungo l'utente una volta
        clienti.aggiungiUtente(utente1);
        
        //Provo ad aggiungere lo stesso utente e verifico l'eccezione
        Exception ex = assertThrows(Exception.class, () -> {clienti.aggiungiUtente(utente1);}, 
                                    "Deve lanciare un'eccezione se si aggiunge un duplicato.");
        
        // Verifico il messaggio d'errore
        assertTrue(ex.getMessage().contains("ERRORE DUPLICATO: Utente " + utente1.toString() + " già presente."));
        assertEquals(1, clienti.getClienti().size(), "La lista non deve aumentare di dimensione dopo l'errore.");
    }
    
    // --- Test Elimina Utente ---

    @Test
    public void testEliminaUtente_Successo() throws Exception {
        clienti.aggiungiUtente(utente1);
        clienti.aggiungiUtente(utente2);
        assertEquals(2, clienti.getClienti().size(), "La lista deve contenere due utenti.");
        
        // ACT
        clienti.eliminaUtente(utente1);
        
        // ASSERT
        assertEquals(1, clienti.getClienti().size(), "Dopo la rimozione, la lista deve contenere 1 utente.");
        assertFalse(clienti.getClienti().contains(utente1), "L'utente 1 non deve essere più presente.");
        assertTrue(clienti.getClienti().contains(utente2), "L'utente 2 (non rimosso) deve ancora esistere.");
    }

    @Test
    public void testEliminaUtente_NonEsistente() {
        //Tenta di rimuovere un utente che non esiste 
        
        Exception exception = assertThrows(Exception.class, () -> {
            clienti.eliminaUtente(utente1);
        }, "Deve lanciare un'eccezione se l'utente non è presente.");
        
        // Verifica il messaggio d'errore
        assertTrue(exception.getMessage().contains("IMPOSSIBILE ELIMINARE UTENTE! Utente : " + utente1.toString() + " non presente nella lista!"));
    }
    
    // --- Test Modifica Utente --- ?
    
    // --- Test Esiste Utente ---
    
    @Test
    public void testEsisteUtente_Esiste() throws Exception{
        // ASSERT
        assertFalse(clienti.esisteUtente(utente1), "L'utente 1 non deve essere presente");
    }
    
    @Test
    public void testEsisteUtente_NonEsiste() throws Exception{
        clienti.aggiungiUtente(utente1);
        
        // ASSERT
        assertTrue(clienti.esisteUtente(utente1), "L'utente 1 dev'essere presente");
    }
    
    
}
