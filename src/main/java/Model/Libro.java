/**
 * @file Libro.java
 * @brief Il file contiene l'implementazione della classe Libro.
 * 
 * La classe Libro incapsula varie informazioni di un singolo libro della biblioteca.
 */

package Model;

import java.util.ArrayList;
import java.util.List;

public class Libro {

    private String titolo;          ///< Titolo del libro.    
    private List<Autore> autori;    ///< Lista degli autori.
    private int anno;               ///< Anno di pubblicazione.
    private int ISBN;               ///< Codice ISBN a 13 cifre.
    private int numCopie;           ///< Numero di copie disponibili.

    /// Costruttore della classe.
    public Libro(String titolo, ArrayList<Autore> autori, int anno, int ISBN, int numCopie) {
        this.titolo = titolo;
        this.autori = new ArrayList<Autore>();
        this.anno = anno;
        this.ISBN = ISBN;
        this.numCopie = numCopie;
    }

    /// Getter del titolo.
    public String getTitolo() { return titolo; }

    /// Getter degli autori.
    public List<Autore> getAutori() { return autori; }

    /// Getter dell'anno di pubblicazione.
    public int getAnno() { return anno; }

    /// Getter del codice ISBN.
    public int getISBN() { return ISBN; }

    /// Getter del numero di copie.
    public int getNumCopie() { return numCopie; }

    /// Setter del titolo.
    public void setTitolo(String titolo) { this.titolo = titolo; }

    /// Setter della lista degli autori.
    public void setAutori(List<Autore> autori) {
        // foreach
    }

    /// Setter dell'anno di pubblicazione.
    public void setAnno(int anno) { this.anno = anno; }

    /// Setter del codice ISBN.
    public void setISBN(int ISBN) { this.ISBN = ISBN; }

    /// Setter del numero di copie.
    public void setNumCopie(int numCopie) { this.numCopie = numCopie; }
    
    /**
     * @brief Controlla se l'istanza corrente di Libro è attualmente in prestito o no.
     * 
     * @return Restituisce `true` se il libro è in prestito ad almeno un utente. Altrimenti, restituisce `false`
     */
    public boolean inPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Aggiunge un autore alla lista degli autori.
     * 
     * @post Alla lista è stato aggiunto l'autore specificato in input.
     * 
     * @param a La variabile di tipo Autore che specifica il nome e il cognome dell'autore da aggiungere alla lista.
     */
    public void aggiungiAutore(Autore a) {
    }

    /**
     * @brief Rimuove un autore dalla lista degli autori.
     * 
     * @pre 'a' deve trovarsi nella lista.
     * @post Dalla lista è stato rimosso l'autore specificato in input.
     * 
     * @param a La variabile di tipo Autore che specifica il nome e il cognome dell'autore da rimuovere.
     */
    public void rimuoviAutore(Autore a) {
    }

    /**
     * @brief Controlla che `numCopie > 0`.
     * 
     * @return Restituisce `true` se `numCopie > 0`, altrimenti restituisce `false`.
     */
    public boolean isDisponibile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
