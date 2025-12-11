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
import java.util.Collections;

/**
 *
 * @author Pasquale
 */
public class LibreriaTest {
    private Libreria libreria;
    private Libro libro1, libro2;
    
    @BeforeEach
    void setUp(){
        //Inizializzo oggetti di supporto

        ArrayList<Autore> autori1 = new ArrayList<>();
        Collections.addAll(autori1, new Autore("Andrea","Torrente"),new Autore("Piero","Schlesinger"));
        
        ArrayList<Autore> autori2 = new ArrayList<>();
        Collections.addAll(autori2, new Autore("Paolo","Marcellini"),new Autore("Carlo","Sbordone"));
        
        //Oggetti libro
        libro1 = new Libro("Manuale di Diritto Privato", autori1, 2025, "9788828869269", 10, 9);
        libro2 = new Libro("Elementi di Analisi Matematica", autori2, 2002, "9788820733834", 10, 9);
       
        //Oggetto libreria
        libreria = new Libreria();
    }
    
    // --- Test Costruttori e Getter ---

    @Test
    public void testCostruttoreDefault_ListaVuota() {
        // ASSERT
        assertNotNull(libreria.getLibreria(), "La lista libreria non deve essere null dopo il costruttore default.");
        assertTrue(libreria.getLibreria().isEmpty(), "La lista libreria deve essere vuota dopo il costruttore default.");
    }
    
    @Test
    public void testCostruttoreParametrizzato_ListaNonNull() {
        ArrayList<Libro> listaIniziale = new ArrayList<>();
        listaIniziale.add(libro1);

        Libreria nuovaLista = new Libreria(listaIniziale);
       
        // ASSERT
        assertEquals(1, nuovaLista.getLibreria().size(), "La dimensione della lista deve essere pari ad 1.");
        assertTrue(nuovaLista.getLibreria().contains(libro1), "La lista deve contenere il libro1.");
    }

    @Test
    public void testCostruttoreParametrizzato_ListaNull() {
        Libreria listaNull = new Libreria(null);
       
        // ASSERT
        assertNotNull(listaNull.getLibreria(), "La lista deve essere inizializzata vuota se si passa null.");
        assertTrue(listaNull.getLibreria().isEmpty(), "La lista deve essere vuota se si passa null.");
    }
    
    // --- Test Aggiungi Libro ---

    @Test
    public void testAggiungiLibro_Successo() throws Exception {
        libreria.aggiungiLibro(libro1);

        // ASSERT
        assertEquals(1, libreria.getLibreria().size(), "Dopo l'aggiunta, la lista deve contenere 1 libro.");
        assertTrue(libreria.getLibreria().contains(libro1), "Il libro1 deve esistere nella lista.");
    }
    
    @Test
    public void testAggiungiLibro_Duplicato() throws Exception {
        //Aggiungo il libro una volta
        libreria.aggiungiLibro(libro1);
       
        //Provo ad aggiungere lo stesso libro e verifico l'eccezione
        Exception ex = assertThrows(Exception.class, () -> {libreria.aggiungiLibro(libro1);}, 
                                    "Deve lanciare un'eccezione se si aggiunge un duplicato.");
       
        // Verifico il messaggio d'errore
        assertTrue(ex.getMessage().contains("ERRORE DUPLICATO: Libro " + libro1.toString() + " già presente."));
        assertEquals(1, libreria.getLibreria().size(), "La lista non deve aumentare di dimensione dopo l'errore.");
    }
    
    // --- Test Elimina Libro ---

    @Test
    public void testEliminaLibro_Successo() throws Exception {
        libreria.aggiungiLibro(libro1);
        libreria.aggiungiLibro(libro2);
        assertEquals(2, libreria.getLibreria().size(), "La lista deve contenere due libri.");
       
        // ACT
        libreria.eliminaLibro(libro1);
       
        // ASSERT
        assertEquals(1, libreria.getLibreria().size(), "Dopo la rimozione, la lista deve contenere 1 libro.");
        assertFalse(libreria.getLibreria().contains(libro1), "Il libro 1 non deve essere più presente.");
        assertTrue(libreria.getLibreria().contains(libro2), "Il libro 2 (non rimosso) deve ancora esistere.");
    }

    @Test
    public void testEliminaLibro_NonEsistente() {
        //Tenta di rimuovere un libro che non esiste 
       
        Exception exception = assertThrows(Exception.class, () -> {
            libreria.eliminaLibro(libro1);
        }, "Deve lanciare un'eccezione se il libro non è presente.");
    }
    
    // --- Test Modifica Libro --- ?
    
    // --- Test Esiste Libro ---
    
    @Test
    public void testEsisteLibro_Esiste() throws Exception{
        // ASSERT
        assertFalse(libreria.isInLibreria(libro1), "Il libro 1 non deve essere presente");
    }
    
    @Test
    public void testEsisteLibro_NonEsiste() throws Exception{
        libreria.aggiungiLibro(libro1);
       
        // ASSERTl
        assertTrue(libreria.isInLibreria(libro1), "Il libro 1 dev'essere presente");
    }
}
