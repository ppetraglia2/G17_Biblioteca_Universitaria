/**
 * @file Clienti.java
 * @brief Questo file contiene la lista degli utenti della biblioteca.
 * 
 * La classe Clienti include la struttura che contiene tutti gli utenti della biblioteca ed i relativi metodi utili per la maipolazioni di tale struttura.
 */

package Model;


import java.util.ArrayList;

public class Clienti {

    private ArrayList<Utente> clienti;  // Lista degli utenti

    ///Costruttore della classe
    public Clienti(ArrayList<Utente> clienti) {
        this.clienti = clienti;
    }

    /**
     * @brief Permette di aggiungere un'utente alla lista
     * 
     * L'utente può essere aggiunto solamente se i suoi campi sono corretti
     * 
     * @post L'utente è stato aggiunto alla lista
     * 
     * @param u È l'utente da aggiungere alla lista
     */
    public void aggiungiUtente(Utente u) {
    }

    /**
     * @brief Permette di eliminare un'utente dalla lista
     * 
     * @pre L'utente dev'essere presente in lista
     * @post L'utente non è più presente in lista
     * 
     * @param u È l'utente da eliminare dalla lsita
     */
    public void eliminaUtente(Utente u) {
    }

    /**
     * @brief Permette di modificare i campi di un'utente
     * 
     * @post Campi dell'utente modificati
     * 
     * @param u È l'utente a cui bisogna modificare i campi
     */
    public void modificaUtente(Utente u) {
    }

    /**DA COMPLETARE
     * @brief Permette di cercare l'utente che possiede quel numero di matricola
     * 
     * @pre Numero di matricola presente nel 
     * 
     * @param matricola Il numero di matricola utilizzato per la ricerca
     * @return La lista che contiene solamente il numero di matricola specificata in input
     */
    public ArrayList<Utente> cercaPerMatricola(int matricola) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Utente> cercaPerCognome(String cognome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
