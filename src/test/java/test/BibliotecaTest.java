/**
 * @file BibliotecaTest.java
 * @brief Test unitario per la classe Biblioteca.
 */
package test;

import Controller.Biblioteca;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class BibliotecaTest {
    
    private Biblioteca biblioteca;
    
    //DATI PER TEST
    private Autore autore1,autore2;
    private ArrayList<Autore> listaAutori;
    private Libro libro1, libro2;
    private Utente utente1, utente2;
    private Clienti clienti;
    private Libreria libreria;
    private Prestiti prestiti;
    
    
    @BeforeEach
    void setUp(){
        //Inizializzo oggetti di supporto
        autore1 = new Autore("Alessandro", "Manzoni");
        autore2 = new Autore("Umberto", "Eco");
        listaAutori.add(autore1);
        listaAutori.add(autore2);

        // Libro 1 
        libro1 = new Libro("I Promessi Sposi", listaAutori, 1840, "9788838439193", 5, 5);
        // Libro 2 
        libro2 = new Libro("Il Nome della Rosa", listaAutori, 1980, " 9789510490044", 2, 2);

        // Utente 1 
        utente1 = new Utente("Mario", "Sabia", "0612701062", "m.sabia1@studenti.unisa.it", 0);
        // Utente 2 
        utente2 = new Utente("Luigi", "Bianchi", "0612701001", "l.bianchi2@studenti.unisa.it", 0);
        
        
        biblioteca = new Biblioteca();
    }
    
    @Test
    void testCostruttore_InizializzazioneStandard() {
        // Verifica che le classi Model siano state inizializzate
        assertNotNull(biblioteca.getLibreria(), "Libreria non deve essere null.");
        assertNotNull(biblioteca.getClienti(), "Clienti non deve essere null.");
        assertNotNull(biblioteca.getPrestiti(), "Prestiti non deve essere null.");
        
    }
    
    // --- Test Filtri ---

    @Test
    void testFiltraLibri_Successo() throws Exception {
        // Aggiungo libri per popolare la lista osservabile
        biblioteca.aggiungiLibro(libro1.getTitolo(), libro1.getAutori(), 1840, libro1.getISBN(), 5, 5);
        biblioteca.aggiungiLibro(libro2.getTitolo(), libro2.getAutori(), 1980, libro2.getISBN(), 2, 2);

        // Filtro per titolo parziale
        biblioteca.filtraLibri("promessi");
        assertEquals(1, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare solo 1 libro.");
        
        // Filtro per titolo parziale
        biblioteca.filtraLibri("I");
        assertEquals(2, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare 2 libri.");
        
        //Filtro per titolo completo
        biblioteca.filtraLibri("Il Nome della Rosa");
        assertEquals(1, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare solo 1 libro.");

        // Filtro nullo/vuoto 
        biblioteca.filtraLibri(null);
        assertEquals(2, biblioteca.getFlLibreria().size(), "Il filtro nullo deve mostrare tutti i libri.");
    }
    
    @Test
    void testFiltraUtenti_Successo() throws Exception {
        // Aggiungo utenti
        biblioteca.aggiungiUtente(utente1.getNome(), utente1.getCognome(), utente1.getMatricola(), utente1.getEmail(), 0);
        biblioteca.aggiungiUtente(utente2.getNome(), utente2.getCognome(), utente2.getMatricola(), utente2.getEmail(), 0);

        // Filtro per Cognome
        biblioteca.filtraUtenti("rossi");
        assertEquals(1, biblioteca.getFlClienti().size(), "Il filtro per cognome dovrebbe trovare solo 1 utente.");
        
        //Filtro per Cognome Parziale
        biblioteca.filtraUtenti("a");
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro per cognome dovrebbe trovare 2 utenti.");

        // Filtro per Matricola
        biblioteca.filtraUtenti("0612701001");
        assertEquals(1, biblioteca.getFlClienti().size(), "Il filtro per matricola dovrebbe trovare solo 1 utente.");
        
        // Filtro per Matricola Parziale
        biblioteca.filtraUtenti("06127");
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro per matricola dovrebbe trovare 2 utenti.");
        
        // Filtro nullo/vuoto 
        biblioteca.filtraLibri(null);
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro nullo deve mostrare tutti gli utenti.");
    }
    



    
    
    
    
}
