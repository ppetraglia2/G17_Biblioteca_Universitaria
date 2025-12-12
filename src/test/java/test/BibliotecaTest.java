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
import java.util.function.BooleanSupplier;
import static org.junit.jupiter.api.Assertions.*;


public class BibliotecaTest {
    
    private Biblioteca biblioteca;
    
    //DATI PER TEST
    private ArrayList<Autore> listaAutori;
    private Clienti clienti;
    private Libreria libreria;
    private Prestiti prestiti;
    
    
    @BeforeEach
    void setUp(){
        //Inizializzo oggetti di supporto
        listaAutori = new ArrayList<>();

        biblioteca = new Biblioteca();
        biblioteca.getObClienti().clear();
        biblioteca.getObLibreria().clear();
        biblioteca.getObPrestiti().clear();
    }
    
    @Test
    void testCostruttore_InizializzazioneStandard() {
        // Verifica che le classi siano state inizializzate
        assertNotNull(biblioteca.getLibreria(), "Libreria non deve essere null.");
        assertNotNull(biblioteca.getClienti(), "Clienti non deve essere null.");
        assertNotNull(biblioteca.getPrestiti(), "Prestiti non deve essere null.");
        
    }
    
    // --- Test Filtri ---

    @Test
    void testFiltraLibri_Successo() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Libro libro1 = new Libro("Titolo1", nuovaLista, 2000, "9780192595218", 5, 5);
        Libro libro2 = new Libro("Titolo Libro", nuovaLista, 2000, "9780192817808", 5, 5);
        // Aggiungo libri per popolare la lista osservabile
        biblioteca.aggiungiLibro(libro1.getTitolo(), libro1.getAutori(), 1840, libro1.getISBN(), 5, 5);
        biblioteca.aggiungiLibro(libro2.getTitolo(), libro2.getAutori(), 1980, libro2.getISBN(), 2, 2);

        // Filtro per titolo parziale
        biblioteca.filtraLibri("libro");
        assertEquals(1, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare solo 1 libro.");
        
        // Filtro per titolo parziale
        biblioteca.filtraLibri("T");
        assertEquals(2, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare 2 libri.");
        
        //Filtro per titolo completo
        biblioteca.filtraLibri("Titolo1");
        assertEquals(1, biblioteca.getFlLibreria().size(), "Il filtro per titolo dovrebbe trovare solo 1 libro.");

        // Filtro nullo/vuoto 
        biblioteca.filtraLibri(null);
        assertEquals(2, biblioteca.getFlLibreria().size(), "Il filtro nullo deve mostrare tutti i libri.");
    }
    
    @Test
    void testFiltraUtenti_Successo() throws Exception {
        // Aggiungo utenti
        biblioteca.aggiungiUtente("Simone", "Antico", "0612706541", "s.antico2@studenti.unisa.it", 0);
        biblioteca.aggiungiUtente("Martina", "Iaconi", "0612705632", "m.iaconi3@studenti.unisa.it", 0);

        // Filtro per Cognome
        biblioteca.filtraUtenti("antico");
        assertEquals(1, biblioteca.getFlClienti().size(), "Il filtro per cognome dovrebbe trovare solo 1 utente.");
        
        //Filtro per Cognome Parziale
        biblioteca.filtraUtenti("a");
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro per cognome dovrebbe trovare 2 utenti.");

        // Filtro per Matricola
        biblioteca.filtraUtenti("0612705632");
        assertEquals(1, biblioteca.getFlClienti().size(), "Il filtro per matricola dovrebbe trovare solo 1 utente.");
        
        // Filtro per Matricola Parziale
        biblioteca.filtraUtenti("06127");
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro per matricola dovrebbe trovare 2 utenti.");
        
        // Filtro nullo/vuoto 
        biblioteca.filtraLibri(null);
        assertEquals(2, biblioteca.getFlClienti().size(), "Il filtro nullo deve mostrare tutti gli utenti.");
    }
    
    
    // --- TEST CHECK VALIDITA' CAMPI LIBRO
    @Test
    void testValiditaLibro_TuttiCampiValidi() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertTrue(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 1980, "9789510490044", 5, 5),
                "Dovrebbe essere TRUE con tutti i campi validi.");
    }
    
    @Test
    void testValiditaLibro_ISBN_Breve() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertFalse(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 1980, "9789510", 5, 5),
                "Dovrebbe essere FALSE con ISBN non a 13 cifre.");
    }
    
    @Test
    void testValiditaLibro_ISBNConLettere() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertFalse(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 1980, "9789510abc", 5, 5),
                "Dovrebbe essere FALSE con ISBN non numerico.");
    }

    @Test
    void testValiditaLibro_CopieDispMaggioriTot() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertFalse(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 1980, "9789510490042", 5, 6),
                "Dovrebbe essere FALSE se copie disponibili > copie totali.");
    }
    
    @Test
    void testValiditaLibro_CopieZero() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertTrue(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 1980, "9789510490044", 0, 0),
                "Dovrebbe essere TRUE se copie totali e disponibili sono 0.");
    }

    @Test
    void testValiditaLibro_AnnoZero() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertFalse(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, 0, "9789510490044", 5, 5),
                "Dovrebbe essere FALSE se l'anno è 0.");
    }

    @Test
    void testValiditaLibro_AnnoNegativo() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        assertFalse(biblioteca.checkValiditaCampiLibro("Il Nome della Rosa", lista, -1988, "9789510490044", 5, 5),
                "Dovrebbe essere FALSE se l'anno è negativo.");
    }

    @Test
    void testValiditaLibro_TitoloConCaratteriSpecialiPermessi() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        // Test con caratteri, numeri, apostrofi, virgolette, trattini, virgole, punti, punti interrogativi, punti esclamativi
        String titoloSpeciale = "Il Libro: 'La Grande Sfida' - Vol.1, pt.II?!";
        assertTrue(biblioteca.checkValiditaCampiLibro(titoloSpeciale, lista, 2023, "9789510490041", 5, 5),
                "Dovrebbe essere TRUE con i caratteri speciali permessi nel titolo.");
    }
        
    @Test
    void testValiditaLibro_TitoloConCarattereNonPermesso() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        // Esempio: simbolo di valuta ($) non è nella regex del titolo
        assertFalse(biblioteca.checkValiditaCampiLibro("Titolo $Errato", lista, 2023, "9789510490048", 5, 5),
                "Dovrebbe essere FALSE con caratteri speciali non permessi nel titolo.");
    }

    @Test
    void testValiditaLibro_AutoreNomeNonValido() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        Autore autore3 = new Autore("Nome non valido$$", "CognomeValido");
        listaAutori.add(autore3);
        
        assertFalse(biblioteca.checkValiditaCampiLibro("Titolo Valido", lista, 2023, "9789510490042", 5, 5),
                "Dovrebbe essere FALSE se il nome di un autore contiene caratteri non validi.");
    }
    
    @Test
    void testValiditaLibro_AutoreCognomeNonValido() throws Exception {
        ArrayList<Autore> lista = new ArrayList<>();
        Autore autore3 = new Autore("NomeValido", "Cognome Non Valido $$$");
        listaAutori.add(autore3);
        
        assertFalse(biblioteca.checkValiditaCampiLibro("Titolo Valido", lista, 2023, "9789510490046", 5, 5),
                "Dovrebbe essere FALSE se il cognome di un autore contiene caratteri non validi.");
    }

    @Test
    void testValiditaLibro_ListaAutoriVuota() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        
        assertTrue(biblioteca.checkValiditaCampiLibro("Titolo Valido", nuovaLista, 2023, "9789510490042", 5, 5),
                "Dovrebbe essere TRUE anche con lista autori vuota");
    }

    
    // --- TEST CHECK VALIDITA' CAMPI UTENTE
    @Test
    void testValiditaUtente_TuttiCampiValidi() throws Exception {
        assertTrue(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "a.cristiano8@studenti.unisa.it", 0),
                "Dovrebbe essere TRUE con tutti i campi validi.");
    }
    
    @Test
    void testValiditaUtente_PrestitiAttiviNonZero() throws Exception {
        assertTrue(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "a.cristiano8@studenti.unisa.it", 2),
                "Dovrebbe essere TRUE con con prestiti attivi > 0.");
    }

    @Test
    void testValiditaUtente_NomeNonValido() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio 123", "Cristiano", "0612707766", "a.cristiano8@studenti.unisa.it", 0),
                "Dovrebbe essere FALSE se il nome contiene numeri o caratteri speciali.");
    }

    @Test
    void testValiditaUtente_CognomeNonValido() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano1!", "0612707766", "a.cristiano8@studenti.unisa.it", 0),
                "Dovrebbe essere FALSE se il cognome contiene numeri o caratteri speciali.");
    }
    
    
    @Test
    void testValiditaUtente_MatricolaBreve() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "061276", "a.cristiano8@studenti.unisa.it", 0),
                "Dovrebbe essere FALSE se la matricola non ha 10 cifre.");
    }

    @Test
    void testValiditaUtente_MatricolaNonNumerica() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "ABCDEFGHIJ", "a.cristiano8@studenti.unisa.it", 0),
                "Dovrebbe essere FALSE se la matricola non è solo numerica.");
    }

    @Test
    void testValiditaUtente_EmailNonUnisa() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "a.cristiano8@studenti.unimi.it", 0),
                "Dovrebbe essere FALSE se l'email non è studenti.unisa.it.");
    }
    
    @Test
    void testValiditaUtente_EmailSenzaNumero() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "a.cristiano@studenti.unimi.it", 0),
                "Dovrebbe essere FALSE se l'email (dopo il cognome) non ha un numero.");
    }

    @Test
    void testValiditaUtente_EmailFormatoErrato() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "1.cristiano1@studenti.unisa.it", 0),
                "Dovrebbe essere FALSE se l'email non inizia con una lettera.");
    }
        
    
    @Test
    void testValiditaUtente_PrestitiAttiviNegativi() throws Exception {
        assertFalse(biblioteca.checkValiditaCampiUtente("Antonio", "Cristiano", "0612707766", "a.cristiano8@studenti.unisa.it", -2),
                "Dovrebbe essere FALSE se numPrestitiAttivi è negativo.");
    }
    
    
    // --- TEST AGGIUNGI LIBRO ---
    
    @Test
    void testAggiungiLibro_CampiNonValidi_LanciaEccezione() {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        
        // ISBN non valido
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiLibro("Titolo", nuovaLista, 2023, "12345", 5, 5);
        });
        assertTrue(e.getMessage().contains("Campi non validi!"), "AggiungiLibro deve fallire per campi non validi.");
    }
    
    @Test
    void testAggiungiLibro_LibroGiaPresente_LanciaEccezione() throws Exception {
        // Aggiungo il primo libro
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        biblioteca.aggiungiLibro("Titolo", nuovaLista, 1840,"9788483087565", 5, 5);

        // Aggiungo un libro con lo stesso ISBN
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiLibro("Titolo Diverso", nuovaLista, 1860, "9788483087565", 3, 3);
        });
        assertTrue(e.getMessage().contains("Libro già presente"), "Deve lanciare eccezione se il libro è già presente (ISBN DULICATO).");
    }
    
    
    // --- TEST ELIMINA LIBRO ---
    @Test
    void testEliminaLibro_Successo_AggiornaLista() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Libro libro = new Libro("Titolo", nuovaLista, 1840,"9780192595218", 5, 5);
        biblioteca.aggiungiLibro(libro.getTitolo(), libro.getAutori(), 1840,libro.getISBN(), 5, 5);
        assertEquals(1, biblioteca.getObLibreria().size());
        
        biblioteca.eliminaLibro(libro);
        assertEquals(0, biblioteca.getObLibreria().size(), "Il libro deve essere rimosso dalla lista.");
    }

    @Test
    void testEliminaLibro_InPrestito_LanciaEccezione() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        //Istanzio il libro in prestito (copie disp<copietotali)
        Libro libroInPrestito = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 4);
        biblioteca.aggiungiLibro(libroInPrestito.getTitolo(), libroInPrestito.getAutori(), 2000, libroInPrestito.getISBN(), 5, 4);

        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.eliminaLibro(libroInPrestito);
        });
        assertTrue(e.getMessage().contains("Impossibile eliminare: Libro in prestito"), "Deve lanciare eccezione se il libro è in prestito.");
        assertEquals(1, biblioteca.getObLibreria().size(), "Il libro non deve essere rimosso.");
    }
    
    
    // --- TEST AGGIUNTI UTENTE ---
    
    @Test
    void testAggiungiUtente_CampiNonValidi_LanciaEccezione() {
        // Matricola non valida 
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiUtente("Giacomo", "Palladino", "12345", "g.palladino8@studenti.unisa.it", 0);
        });
        assertTrue(e.getMessage().contains("Campi non validi!"), "AggiungiUtente deve fallire per campi non validi.");
    }
    
    @Test
    void testAggiungiUtente_Successo_AggiornaLista() throws Exception {
        biblioteca.aggiungiUtente("Antonio", "Bianchi", "0612701234", "a.bianchi2@studenti.unisa.it", 0);
        assertEquals(1, biblioteca.getObClienti().size(), "L'utente deve essere aggiunto alla lista.");
    }

    @Test
    void testAggiungiUtente_UtenteGiaRegistrato_LanciaEccezione() throws Exception {
        biblioteca.aggiungiUtente("Antonio", "Bianchi", "0612701234", "a.bianchi2@studenti.unisa.it", 0);

        // Aggiungo un utente con la stessa matricola
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiUtente("Nome Diverso", "Cognome Diverso", "0612701234", "n.diverso9@studenti.unisa.it", 0);
        });
        assertTrue(e.getMessage().contains("Utente già registrato"), "Deve lanciare eccezione se l'utente esiste (stessa matricola).");
    }
    
    // ---TEST ELIMINA UTENTE ---
    @Test
    void testEliminaUtente_Successo_AggiornaLista() throws Exception {
        Utente utente = new Utente("Maria", "Tedesco","0612706543","m.tedesco1@studenti.unisa.it",0);
        biblioteca.aggiungiUtente(utente.getNome(), utente.getCognome(), utente.getMatricola(), utente.getEmail(), 0);
        assertEquals(1, biblioteca.getObClienti().size());

        biblioteca.eliminaUtente(utente);
        assertEquals(0, biblioteca.getObClienti().size(), "L'utente deve essere rimosso dalla lista.");
    }

    @Test
    void testEliminaUtente_InPrestito_LanciaEccezione() throws Exception {
        Utente utente = new Utente("Ferdinando", "Tedesco","0612706545","f.tedesco1@studenti.unisa.it",1);
        biblioteca.aggiungiUtente(utente.getNome(), utente.getCognome(), utente.getMatricola(), utente.getEmail(), 1);

        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.eliminaUtente(utente);
        });
        assertTrue(e.getMessage().contains("Utente possiede prestiti attivi"), "Deve lanciare eccezione se l'utente ha prestiti attivi.");
    }
    
    
    // --- TEST AGGIUNGI PRESTITO
    
    @Test
    void testAggiungiPrestito_Successo_AggiornaTuttiGliStati() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Utente utente = new Utente("Ferdinando", "Cipriano","0612709081","f.cipriano1@studenti.unisa.it",0);
        Libro libroInPrestito = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 4);
        biblioteca.aggiungiUtente(utente.getNome(), utente.getCognome(), utente.getMatricola(), utente.getEmail(), 0);
        biblioteca.aggiungiLibro(libroInPrestito.getTitolo(), libroInPrestito.getAutori(), 1840, libroInPrestito.getISBN(), 5, 5);
        
        int copieIniziali = libroInPrestito.getNumCopieDisponibili();
        int prestitiAttiviIniziali = utente.getNumPrestitiAttivi();

        biblioteca.aggiungiPrestito(utente, libroInPrestito, LocalDate.now());

        assertEquals(1, biblioteca.getObPrestiti().size(), "Il prestito deve essere aggiunto alla lista prestiti.");
        assertEquals(copieIniziali - 1, libroInPrestito.getNumCopieDisponibili(), "Le copie del libro devono essere decrementate.");
        assertEquals(prestitiAttiviIniziali + 1, utente.getNumPrestitiAttivi(), "I prestiti attivi dell'utente devono essere incrementati.");
    }
    
    @Test
    void testAggiungiPrestito_UtenteNullo_LanciaEccezione() {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Libro libroInPrestito = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 4);
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiPrestito(null, libroInPrestito, LocalDate.now());
        });
        assertTrue(e.getMessage().contains("Dati non validi"));
    }
    
    @Test
    void testAggiungiPrestito_LibroNullo_LanciaEccezione() {
        
        Utente utente = new Utente("Ciro", "Cipriano","0612709080","c.cipriano1@studenti.unisa.it",0);
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiPrestito(utente, null, LocalDate.now());
        });
        assertTrue(e.getMessage().contains("Dati non validi"));
    }

    @Test
    void testAggiungiPrestito_CopieNonDisponibili_LanciaEccezione() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Utente utente = new Utente("Vincenzo", "Cipriano","0612709082","v.cipriano1@studenti.unisa.it",0);
        Libro libroEsaurito = new Libro("Esaurito", nuovaLista, 2020, "2222222222222", 1, 0);
        biblioteca.aggiungiUtente(utente.getNome(), utente.getCognome(), utente.getMatricola(), utente.getEmail(), 0);
        biblioteca.aggiungiLibro(libroEsaurito.getTitolo(), libroEsaurito.getAutori(), 2020, libroEsaurito.getISBN(), 1, 0);

        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiPrestito(utente, libroEsaurito, LocalDate.now());
        });
        assertTrue(e.getMessage().contains("Copie del libro non disponibili"));
    }

    @Test
    void testAggiungiPrestito_UtenteLimiteRaggiunto_LanciaEccezione() throws Exception {
        // Istanzio un utente con 3 prestiti attivi
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Utente utenteAlLimite = new Utente("Kevin", "Cipriano","0612709083","k.cipriano1@studenti.unisa.it",3);
        Libro libro = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 4);
        biblioteca.aggiungiUtente(utenteAlLimite.getNome(), utenteAlLimite.getCognome(), utenteAlLimite.getMatricola(), utenteAlLimite.getEmail(), 3);
        biblioteca.aggiungiLibro(libro.getTitolo(), libro.getAutori(), 1840, libro.getISBN(), 5, 5);

        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.aggiungiPrestito(utenteAlLimite, libro, LocalDate.now());
        });
        assertTrue(e.getMessage().contains("L'utente selezionato è già a carico di 3 prestiti"));
    }

    // --- TEST RESTITUISCI PRESTITO
    
    @Test
    void testRestituisciPrestito_Successo_AggiornaTuttiGliStati() throws Exception {
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Utente utente = new Utente("Antonia", "Cipriano","0612709084","a.cipriano2@studenti.unisa.it",0);
        Libro libro = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 5);
        //Aggiungo un prestito
        biblioteca.aggiungiUtente(utente.getNome(), utente.getCognome(), utente.getMatricola(), utente.getEmail(), 0);
        biblioteca.aggiungiLibro(libro.getTitolo(), libro.getAutori(), 1840, libro.getISBN(), 5, 5);
        biblioteca.aggiungiPrestito(utente, libro, LocalDate.now());

        Prestito prestitoAttivo = biblioteca.getObPrestiti().get(0);
        int copieAttuali = libro.getNumCopieDisponibili(); // 4
        int prestitiAttiviAttuali = utente.getNumPrestitiAttivi(); // 1

        //Restituzione
        biblioteca.restituisciPrestito(prestitoAttivo);

        assertEquals(0, biblioteca.getObPrestiti().size(), "Il prestito deve essere rimosso dalla lista.");
        assertEquals(copieAttuali + 1, libro.getNumCopieDisponibili(), "Le copie disponibili devono essere incrementate."); // 5
        assertEquals(prestitiAttiviAttuali - 1, utente.getNumPrestitiAttivi(), "I prestiti attivi devono essere decrementati."); // 0
    }

    @Test
    void testRestituisciPrestito_PrestitoNonEsistente_LanciaEccezione() throws Exception {
        // Prestito fittizio non presente nella lista
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        Utente utente = new Utente("Savina", "Cipriano","0612709085","s.cipriano4@studenti.unisa.it",0);
        Libro libro = new Libro("In Prestito", nuovaLista, 2000, "1111111111111", 5, 5);
        Prestito prestitoFittizio = new Prestito(utente, libro, LocalDate.now());
        
        Exception e = assertThrows(Exception.class, () -> {
            biblioteca.restituisciPrestito(prestitoFittizio);
        });
        assertTrue(e.getMessage().contains("Prestito non trovato per la rimozione."), "Deve lanciare eccezione se il prestito non esiste.");
    }
}
