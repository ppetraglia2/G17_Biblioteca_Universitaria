/**
 * @file Biblioteca.java
 * @brief Questo file rappresenta il Controller del sistema di gestione della biblioteca.
 * 
 * La classe Biblioteca funge da coordinatore tra i vari moduli, infatti gestisce l'aggregazione
 * delle classi Clienti,Libreria e Prestiti ma comunica anche con il package View in quanto è
 * responsabile di liste osservabili per l'interfaccia utente
 */
package Controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import Model.*;
import java.util.ArrayList;

//Necessari per JavaFX(ObservableList, FilteredList)
import javafx.collections.ObservableList; 
import javafx.collections.transformation.FilteredList;
import javafx.collections.FXCollections;

public class Biblioteca implements Serializable{
    
    private Libreria libreria;
    private Clienti clienti;
    private Prestiti prestiti;
    
    private ObservableList<Libro> obLibreria;
    private ObservableList<Utente> obClienti;
    private ObservableList<Prestito> obPrestiti;

    private FilteredList<Libro> flLibreria;
    private FilteredList<Utente> flClienti;
    
    private final String filename = "";
    
    ///Costruttore della classe Biblioteca
    public Biblioteca(){
        this.libreria = new Libreria(new ArrayList<Libro>()); 
        this.clienti = new Clienti(new ArrayList<Utente>()); 
        this.prestiti = new Prestiti(new ArrayList<Prestito>());
        
        // Inizializzazione delle liste ObservableList/FilteredList
        inizializzaListe();
    }
    
    //Getter di Libreria
    public Libreria getLibreria() { return libreria; }
    
    //Getter di Clienti
    public Clienti getClienti() { return clienti; }
    
    //Getter di Prestiti
    public Prestiti getPrestiti() { return prestiti; }
    
    //Getter di FilteredList<Libro>
    public FilteredList<Libro> getFlLibreria() { return flLibreria; }
    
    //Getter di FilteredList<Utente>
    public FilteredList<Utente> getFlClienti() { return flClienti; }
    
    //Getter di ObservableList<Libro>
    public ObservableList<Libro> getObLibreria() { return obLibreria; }
    
    //Getter di ObservableList<Utente>
    public ObservableList<Utente> getObClienti() { return obClienti; }
    
    //Getter di ObservableList<Prestito>
    public ObservableList<Prestito> getObPrestiti() { return obPrestiti; }
    
    /**
     * @brief Inizializza le liste osservabili e filtrate.
     *
     * Crea le ObservableList a partire dalle liste interne dei gestori(Libreria,Prestiti,Clienti) e
     * crea le FilteredList, agganciandole per le operazioni di UI.
     *
     */
    public void inizializzaListe() {
        // Crea le ObservableList, avvolgendo le liste interne dei gestori
        this.obLibreria = FXCollections.observableArrayList(libreria.getLibreria());
        this.obClienti = FXCollections.observableArrayList(clienti.getClienti());
        this.obPrestiti = FXCollections.observableArrayList(prestiti.getPrestiti());

        // Crea le FilteredList basate sulle ObservableList
        this.flLibreria = new FilteredList<>(obLibreria, p -> true); // Inizializza con tutti gli elementi
        this.flClienti = new FilteredList<>(obClienti, p -> true);   // Inizializza con tutti gli elementi
    }
    
    /**
     * @brief Applica un filtro alla lista dei Libri.
     * @param q La stringa è utilizzata per specificare il tipo di filtraggio (titolo, autore, ISBN, ecc.).
     * @post La FilteredList flLibreria è aggiornata in base al filtro.
     */
    public void filtraLibri(String q) {
        
    }
    
    /**
     * @brief Applica un filtro alla lista degli Utenti.
     * @param q La stringa è utilizzata per specificare il tipo di filtraggio (nome, cognome, matricola, ecc.).
     * @post La FilteredList flClienti è aggiornata in base al filtro.
     */
    public void filtraUtenti(String q) {
        
    }
    
    /**
     * @brief Aggiunge un nuovo libro alla Libreria.
     *
     * Effettua i controlli di validità sui campi e affida l'aggiunta del libro alla classe Libreria.
     * Aggiorna la lista osservabile in caso di successo.
     *
     * @param titolo Il titolo del libro.
     * @param autori La lista degli autori del libro.
     * @param anno L'anno di pubblicazione.
     * @param ISBN Il codice ISBN .
     * @param copieTot Il numero di copie totali.
     * @param copieDisp Il numero di copie disponibili.
     * @throws Exception Se i dati non sono validi o il libro esiste già.
     * @post Il libro è aggiunto alla Libreria e alla obLibreria.
     */
    public void aggiungiLibro(String titolo, List<Autore> autori, int anno, String ISBN, int copieTot, int copieDisp) throws Exception {
       
    }
    
    /**
     * @brief Elimina un libro dalla Libreria.
     *
     * @param l Il Libro da eliminare.
     * @throws Exception Se il libro ha ancora copie in prestito.
     * @post Il libro è rimosso da Libreria e da obLibreria.
     */
    public void eliminaLibro(Libro l) throws Exception {
        
    }
    
    /**
     * @brief Controlla la validità minima dei campi per l'aggiunta di un Libro.
     * @param titolo Il titolo del libro.
     * @param autori La lista degli autori del libro.
     * @param anno L'anno di pubblicazione.
     * @param ISBN Il codice ISBN .
     * @param copieTot Il numero di copie totali.
     * @param copieDisp Il numero di copie disponibili.
     * @return true se i campi sono validi, false altrimenti.
     */
    private boolean checkValiditaCampiLibro(String titolo,List<Autore> autori, int anno,String ISBN, int copieTot, int copieDisp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Controlla la validità minima dei campi per l'aggiunta di un Utente.
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param matricola La matricola univoca.
     * @param email L'email istituzionale.
     * @param numPrestitiAttivi Il numero di prestiti attivi dell'utente.
     * @return true se i campi sono validi, false altrimenti.
     */
    
    private boolean checkValiditaCampiUtente(String nome, String cognome, String matricola, String email, int numPrestitiAttivi){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Aggiunge un nuovo utente ai Clienti.
     *
     * Effettua i controlli di validità e delega l'aggiunta alla classe Clienti.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param matricola La matricola dell'utente.
     * @param email L'indirizzo email.
     * @param numPrestitiAttivi Il numero di Prestiti Attivi dell'utente
     * @throws Exception Se i dati non sono validi o la matricola esiste già.
     * @post L'utente è aggiunto a Clienti e a obClienti.
     */
    public void aggiungiUtente(String nome, String cognome, String matricola, String email, int numPrestitiAttivi) throws Exception {
       
    }
    
    /**
     * @brief Elimina un utente dal sistema.
     *
     * @param u L'Utente da eliminare.
     * @throws Exception Se l'utente ha ancora prestiti attivi.
     * @post L'utente è rimosso da Clienti e da obClienti.
     */
    public void eliminaUtente(Utente u) throws Exception {
        
    }
    
    /**
     * @brief Registra un nuovo prestito nel sistema.
     *
     * Controlla la disponibilità del libro e la possibilità dell'utente di prendere in prestito.
     *
     * @param u L'Utente che richiede il prestito.
     * @param l Il Libro richiesto.
     * @param data La data di inizio del prestito.
     * @throws Exception Se il libro non è disponibile o l'utente ha troppi prestiti.
     * @post Viene creato un nuovo Prestito, il contatore dei prestiti attivi è aggiornato
     * e il numero di copie disponibili del libro è decrementato.
     */
    public void aggiungiPrestito(Utente u, Libro l, LocalDate data) throws Exception {
    }
    
    /**
     * @brief Gestisce la restituzione di un Prestito.
     *
     * Aggiorna lo stato del Prestito e delega la rimozione.
     *
     * @param p Il Prestito da restituire.
     * @throws Exception Se il prestito non è più attivo o non esiste.
     * @post Il Prestito rimosso, il contatore dei prestiti attivi dell'utente 
     * viene decrementato e il numero di copie del libro incrementato.
     */
    public void restituisciPrestito(Prestito p) throws Exception {
        
    }

    /**
     * @brief Salva lo stato corrente della Biblioteca su un file esterno.
     *
     * Serializza l'oggetto Biblioteca (e quindi i gestori Clienti, Libreria, Prestiti)
     * nel file specificato da 'filename'.
     *
     * @throws Exception Se si verificano errori di I/O durante la serializzazione.
     * @post I dati sono salvati sul file.
     */
    public void salvaSuFile() throws Exception {
        
    }

    /**
     * @brief Carica lo stato della Biblioteca da un file esterno.
     *
     * Deserializza l'oggetto Biblioteca dal file e ripristina lo stato interno.
     * Richiama inizializzaListe() dopo il caricamento.
     *
     * @throws Exception Se si verificano errori di I/O o di classe durante la deserializzazione.
     * @return Una nuova istanza di Biblioteca con i dati caricati.
     */
    public Biblioteca caricaDaFile() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
