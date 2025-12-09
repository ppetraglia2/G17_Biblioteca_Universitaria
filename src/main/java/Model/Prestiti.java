/**
 * @file Prestiti.java
 * @brief Il file contiene l'implementazione della classe Prestiti.
 * 
 * La classe Prestiti gestisce la lista di prestiti attualmente presenti.
 */

package Model;
import java.io.Serializable;
import java.util.ArrayList;

public class Prestiti implements Serializable{

    private ArrayList<Prestito> prestiti; ///< Lista dei prestiti.

    /// Costruttore della classe.
    public Prestiti(ArrayList<Prestito> prestiti) {
        if (prestiti != null) {
            this.prestiti = new ArrayList<>(prestiti);
        } else {
            this.prestiti = new ArrayList<>();
        }
    }
    
    //Costruttore di Default
    public Prestiti(){
        this.prestiti = new ArrayList<>();
    }

    ///Getter di Prestiti
    public ArrayList<Prestito> getPrestiti(){return prestiti;}
    
    /**
     * @brief Aggiunge un prestito alla lista dei prestiti.
     * 
     * @pre Il prestito non deve essere già presente nella lista.
     * @post Alla lista è stato aggiunto il prestito specificato in input.
     * 
     * @param p La variabile di tipo Prestito che specifica il prestito da aggiungere alla lista.
     */
    public void aggiungiPrestito(Prestito p) throws Exception{
        if (this.prestiti.contains(p)) {
            throw new Exception("ERRORE DUPLICATO: Il prestito risulta già registrato nel sistema.");
        }
        this.prestiti.add(p);
    }

    /**
     * @brief Rimuove un prestito dalla lista dei prestiti.
     * 
     * @pre Il prestito `p` deve trovarsi nella lista.
     * @post Dalla lista è stato rimosso il prestito specificato in input.
     * 
     * @param p La variabile di tipo Prestito che specifica il prestito da rimuovere dalla lista.
     */
    public void rimuoviPrestito(Prestito p) throws Exception{
        if (!this.prestiti.contains(p)) {
            throw new Exception("ERRORE RIMOZIONE: Il prestito da rimuovere non è presente nella lista dei prestiti attivi.");
        }

        this.prestiti.remove(p);
    }
    
}
