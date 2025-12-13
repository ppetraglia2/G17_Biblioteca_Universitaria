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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    private final String filename = "output.bin";
    
    ///Costruttore della classe Biblioteca
    public Biblioteca(){
        if(!caricaDaFile()){
            this.libreria = new Libreria(new ArrayList<>()); 
            this.clienti = new Clienti(new ArrayList<>()); 
            this.prestiti = new Prestiti(new ArrayList<>());
        }
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
    private void inizializzaListe() {
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
     * @param search La stringa è utilizzata per specificare il tipo di filtraggio (titolo, autore, ISBN, ecc.).
     * @post La FilteredList flLibreria è aggiornata in base al filtro.
     */
    public void filtraLibri(String search) {
        flLibreria.setPredicate(libro->{
            if(search == null || search.isEmpty()) return true;
            
            String lowSearch = search.toLowerCase();
            
            return libro.getTitolo().toLowerCase().contains(lowSearch) || libro.getAutori().toString().toLowerCase().contains(lowSearch) || libro.getISBN().toLowerCase().contains(lowSearch);
        });
    }
    
    /**
     * @brief Applica un filtro alla lista degli Utenti.
     * @param search La stringa è utilizzata per specificare il tipo di filtraggio (nome, cognome, matricola, ecc.).
     * @post La FilteredList flClienti è aggiornata in base al filtro.
     */
    public void filtraUtenti(String search) {
        flClienti.setPredicate(utente->{
            if(search == null || search.isEmpty()) return true;
            
            String lowSearch = search.toLowerCase();
            
            return utente.getCognome().toLowerCase().contains(lowSearch) || utente.getMatricola().toLowerCase().contains(lowSearch);
        });
    }
    
    /**
     * @brief Controlla la validità minima dei campi per l'aggiunta di un Libro.
     * @param titolo Il titolo del libro.
     * @param autori La lista degli autori del libro.
     * @param anno L'anno di publicazione del libro.
     * @param ISBN Il codice ISBN .
     * @param copieTot Il numero di copie totali.
     * @param copieDisp Il numero di copie disponibili.
     * @return true se i campi sono validi, false altrimenti.
     */
    public boolean checkValiditaCampiLibro(String titolo, List<Autore> autori, int anno, String ISBN, int copieTot, int copieDisp) {
        
        if(!ISBN.matches("^\\d{13}$") || copieTot < copieDisp || anno <= 0 || !titolo.trim().matches("^[\\p{L}\\p{N}'\":\\-.,?! ]+$"))
            return false;
        
        for(Autore a : autori){
            if(!a.getNome().trim().matches("^[\\p{L}' ]+$") || !a.getCognome().trim().matches("^[\\p{L}' ]+$"))
                return false;
        }
        
        return true;
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
    
    public boolean checkValiditaCampiUtente(String nome, String cognome, String matricola, String email, int numPrestitiAttivi){
        
        return nome.matches("^[\\p{L}' ]+$") 
                && cognome.matches("^[\\p{L}' ]+$") 
                && matricola.matches("^\\d{10}$") 
                && email.matches("^[A-Za-z]\\.[\\p{L}']+\\d+@studenti\\.unisa\\.it$") 
                && numPrestitiAttivi >= 0;
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
       if(!checkValiditaCampiLibro(titolo, autori, anno, ISBN, copieTot, copieDisp)) throw new Exception("Campi non validi!");
       
       Libro l = new Libro(titolo, new ArrayList<>(autori), anno, ISBN, copieTot, copieDisp);
       
       if(libreria.isInLibreria(l)) throw new Exception("Libro già presente");
       
       libreria.aggiungiLibro(l);
       obLibreria.add(l);
       salvaSuFile();
    }
    
    /**
     * @brief Elimina un libro dalla Libreria.
     *
     * Controlla che il libro non sia in prestito ed affida la rimozione del libro alla classe Libreria.
     * Aggiorna la lista osservabile.
     * 
     * @param l Il Libro da eliminare.
     * @throws Exception Se il libro ha ancora copie in prestito.
     * @post Il libro è rimosso da Libreria e da obLibreria.
     */
    public void eliminaLibro(Libro l) throws Exception {
        if(l.isLibroInPrestito()) throw new Exception("Impossibile eliminare: Libro in prestito");
        
        libreria.eliminaLibro(l);
        obLibreria.remove(l);
        salvaSuFile();
    }
    
    public void modificaLibro(Libro l, String titolo, List<Autore> autori, int anno, String isbn, int copie) throws Exception{
        if(!checkValiditaCampiLibro(titolo, autori, anno, isbn, copie, copie)) throw new Exception("Campi non validi!");
        
        libreria.modificaLibro(l, titolo, autori, anno, isbn, copie);
        obLibreria.set(obLibreria.indexOf(l), l);
        salvaSuFile();
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
     * @throws Exception Se i dati non sono validi o la matricola esiste già.
     * @post L'utente è aggiunto a Clienti e a obClienti.
     */
    public void aggiungiUtente(String nome, String cognome, String matricola, String email) throws Exception {
       if(!checkValiditaCampiUtente(nome, cognome, matricola, email, 0)) throw new Exception("Campi non validi!");
       
       Utente u = new Utente(nome, cognome, matricola, email, 0);
       
       if(clienti.esisteUtente(u)) throw new Exception("Utente già registrato");
       
       clienti.aggiungiUtente(u);
       obClienti.add(u);
       salvaSuFile();
    }
    
    /**
     * @brief Elimina un utente dal sistema.
     *
     * @param u L'Utente da eliminare.
     * @throws Exception Se l'utente ha ancora prestiti attivi.
     * @post L'utente è rimosso da Clienti e da obClienti.
     */
    public void eliminaUtente(Utente u) throws Exception {
        if(u.inPrestito()) throw new Exception("Impossibile eliminare: Utente possiede prestiti attivi");
        
        clienti.eliminaUtente(u);
        obClienti.remove(u);
        salvaSuFile();
    }
    
    
    public void modificaUtente(Utente u, String nome, String cognome, String matricola, String email) throws Exception{
        if(!checkValiditaCampiUtente(nome, cognome, matricola, email, 0)) throw new Exception("Campi non validi!");
        
        clienti.modificaUtente(u, nome, cognome, matricola, email);
        obClienti.set(obClienti.indexOf(u), u);
        salvaSuFile();
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
        if(u == null || l == null || data == null) throw new Exception("Dati non validi");
        if(l.getNumCopieDisponibili() <= 0) throw new Exception("Copie del libro non disponibili");
        if(u.getNumPrestitiAttivi() >= 3) throw new Exception("L'utente selezionato è già a carico di 3 prestiti");
        
        Prestito p = new Prestito(u, l, data);
        prestiti.aggiungiPrestito(p);
        obPrestiti.add(p);
        
        l.diminuisciCopie();
        u.incrementaPrestitiAttivi();
        obClienti.set(obClienti.indexOf(u), u);
        obLibreria.set(obLibreria.indexOf(l), l);
        
        salvaSuFile();
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
        if (!obPrestiti.contains(p)) {
            throw new Exception("Prestito non trovato per la rimozione.");
        }
        prestiti.rimuoviPrestito(p);
        obPrestiti.remove(p);
        
        Libro l = p.getLibro();
        Utente u = p.getUtente();
        
        l.aumentaCopie();
        u.decrementaPrestitiAttivi();
        obClienti.set(obClienti.indexOf(u), u);
        obLibreria.set(obLibreria.indexOf(l), l);
        
        salvaSuFile();
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
    private void salvaSuFile() throws Exception {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            
            out.writeObject(libreria);
            out.writeObject(clienti);
            out.writeObject(prestiti);
            
        }catch(IOException e){ throw new Exception("Errore nel salvataggio dei dati"); }
    }

    /**
     * @brief Carica lo stato della Biblioteca da un file esterno.
     *
     * Deserializza l'oggetto Biblioteca dal file e ripristina lo stato interno.
     * Richiama inizializzaListe() dopo il caricamento.
     *
     * @return true se il caricamento ha avuto successo, false altrimenti.
     */
    private boolean caricaDaFile(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            
            libreria = (Libreria) in.readObject();
            clienti = (Clienti) in.readObject();
            prestiti = (Prestiti) in.readObject();
            
            return true;
        }catch(Exception e){ return false; }
    }
}
