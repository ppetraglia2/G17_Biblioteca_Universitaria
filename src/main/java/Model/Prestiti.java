/**
 * @file Prestiti.java
 * @brief Il file contiene l'implementazione della classe Prestiti.
 * 
 * La classe Prestiti gestisce la lista di prestiti attualmente presenti.
 */

package Model;

import java.util.ArrayList;

public class Prestiti {

    private ArrayList<Prestito> prestiti; ///< Lista dei prestiti.

    /// Costruttore della classe.
    public Prestiti(ArrayList<Prestito> prestiti) {
        this.prestiti = prestiti;
    }

    
    /**
     * @brief Aggiunge un prestito alla lista dei prestiti.
     * 
     * @post Alla lista è stato aggiunto il prestito specificato in input.
     * 
     * @param p La variabile di tipo Prestito che specifica il prestito da aggiungere alla lista.
     */
    public void aggiungiPrestito(Prestito p) {
    }

    /**
     * @brief Rimuove un prestito dalla lista dei prestiti.
     * 
     * @pre `p` deve trovarsi nella lista.
     * @post Dalla lista è stato rimosso il prestito specificato in input.
     * 
     * @param p La variabile di tipo Prestito che specifica il prestito da rimuovere dalla lista.
     */
    public void rimuoviPrestito(Prestito p) {
    }
}
