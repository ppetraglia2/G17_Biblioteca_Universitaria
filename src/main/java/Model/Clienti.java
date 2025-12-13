/**
 * @file Clienti.java
 * @brief Questo file contiene la lista degli utenti della biblioteca.
 * 
 * La classe Clienti include la struttura che contiene tutti gli utenti della biblioteca ed i relativi metodi utili per la maipolazioni di tale struttura.
 */

package Model;
import java.io.Serializable;
import java.util.ArrayList;

public class Clienti implements Serializable{

    /// Lista degli utenti.
    private ArrayList<Utente> clienti;

    ///Costruttore della classe
    public Clienti(ArrayList<Utente> clienti) {
        if (clienti != null) {
            this.clienti = new ArrayList<>(clienti);
        } else {
            this.clienti = new ArrayList<>();
        }
    }
    //Costruttore di Default
    public Clienti(){
        this.clienti = new ArrayList<>();
    }
    
    //Getter di Clienti
    public ArrayList<Utente> getClienti(){return clienti;}
    
    /**
     * @brief Permette di aggiungere un'utente alla lista
     * 
     * L'utente può essere aggiunto solamente se i suoi campi sono corretti
     * 
     * @post L'utente è stato aggiunto alla lista
     * 
     * @param u È l'utente da aggiungere alla lista
     */
    public void aggiungiUtente(Utente u) throws Exception{
        if (this.esisteUtente(u)) {
            throw new Exception("ERRORE DUPLICATO: Utente " + u.toString() + " già presente.");
        }
        this.clienti.add(u);
    }

    /**
     * @brief Permette di eliminare un'utente dalla lista
     * 
     * @pre L'utente dev'essere presente in lista
     * @post L'utente non è più presente in lista
     * 
     * @param u È l'utente da eliminare dalla lista
     */
    public void eliminaUtente(Utente u) throws Exception{
        if(!this.esisteUtente(u)){
            throw new Exception ("IMPOSSIBILE ELIMINARE UTENTE! Utente : " + u.toString() + " non presente nella lista!");
        }
        
        if (u.inPrestito()) { 
             throw new Exception("IMPOSSIBILE ELIMINARE UTENTE! L'utente ha ancora " + u.getNumPrestitiAttivi() + " prestiti attivi.");
        }
        this.clienti.remove(u);
        
    }

    /**
     * @brief Permette di modificare i campi di un'utente
     * 
     * @post Campi dell'utente modificati
     * 
     * @param u È l'utente a cui bisogna modificare i campi
     */
    public void modificaUtente(Utente u, String nome, String cognome, String matricola, String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @bried Permette di verificare l'esistenza dell'Utente nella lista
     * @param Utente u è l'utente su cui effettuare la verifica
     * @return True se l'Utente passato come parametro è presente nella lista
     */
    public boolean esisteUtente(Utente u){
        return this.clienti.contains(u);
    }
}