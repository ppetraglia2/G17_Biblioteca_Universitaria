/**
 * @file Libreria.java
 * @brief Questo file contiene la lista dei libri presenti nella Biblioteca.
 * 
 * La classe Libreria include la struttura che contiene tutti i libri della biblioteca ed i relativi metodi utili per la maipolazioni di tale struttura.
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Libreria implements Serializable{

    private ArrayList<Libro> libreria;  ///< Lista dei libri

    ///Costruttore della classe
    public Libreria(ArrayList<Libro> libreria) {
        if (libreria != null) {
            this.libreria = new ArrayList<>(libreria);
        } else {
            this.libreria = new ArrayList<>();
        }
    }
    
    //Costruttore di default
    public Libreria(){
        this.libreria = new ArrayList<>();
    }
    
    ///Getter di Libreria
    public ArrayList<Libro> getLibreria(){return libreria;}

    /**
     * @brief Permette di aggiungere un libro alla lista
     * 
     * Il libro può essere aggiunto solamente se i suoi campi sono corretti
     * 
     * @post Il libro dato da input è stato aggiunto alla lista
     * 
     * @param l È il libro da aggiungere alla lista
     */
    public void aggiungiLibro(Libro l) throws Exception{
        if (this.isInLibreria(l)) {
            throw new Exception("ERRORE DUPLICATO: Libro" + l.toString() + " già presente.");
        }
        this.libreria.add(l);
    }

    /**
     * @brief Permette di eliminare un libro dalla lista
     * 
     * @pre Il libro dato in input dev'essere presente nella lista
     * @post Il libro non è più presente nella lista
     * 
     * @param l È il libro da rimuovere dalla lista
     */
    public void eliminaLibro(Libro l) throws Exception{
        if(!this.isInLibreria(l)){
            throw new Exception ("IMPOSSIBILE ELIMINARE LIBRO! Libro : " + l.toString() + " non presente nella lista!");
        }
        if(l.isLibroInPrestito()){
            throw new Exception ("IMPOSSIBILE ELIMINARE LIBRO! Libro : " + l.toString() + " in prestito!");
        }
        this.libreria.remove(l);
    }
    
    /**
     * @brief Permette di modificare i campi di un libro della lista
     * 
     * @pre Il libro dato in input dev'essere presente nella lista
     * @post I campi desiderati sono stati modificati
     * 
     * @param l È il libro i cui campi devono essere modificati
     */
    public void modificaLibro(Libro l) {
    }
    
    /**
     * @brief Permette di controllare che un libro sia presente nella lista
     * 
     * @param l È il libro che dev'essere ricercato nella lista
     * @return True se il libro è presente nella libreria
     */
    public boolean isInLibreria(Libro l) {
        return this.libreria.contains(l);
    }
    
}
