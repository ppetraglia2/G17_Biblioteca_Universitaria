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
    private String ISBN;               ///< Codice ISBN a 13 cifre.
    private int numCopieTotali;         ///< Numero di copie totali.
    private int numCopieDisponibili;    ///< Numero di copie disponibili.

    /// Costruttore della classe.
    public Libro(String titolo, ArrayList<Autore> autori, int anno, String ISBN, int numCopieTotali, int numCopieDisponibili) {
        this.titolo = titolo;
        this.autori = new ArrayList<Autore>();
        this.anno = anno;
        this.ISBN = ISBN;
        this.numCopieTotali = numCopieTotali;
        this.numCopieDisponibili = numCopieTotali;      //< All'inizio, le copie disponibili sono pari al totale
    }

    /// Getter del titolo.
    public String getTitolo() { return titolo; }

    /// Getter degli autori.
    public List<Autore> getAutori() { return autori; }

    /// Getter dell'anno di pubblicazione.
    public int getAnno() { return anno; }

    /// Getter del codice ISBN.
    public String getISBN() { return ISBN; }

    /// Getter del numero di copie totali.
    public int getNumCopieTotali() { return numCopieTotali; }
    
    //Getter del numero di copie disponibili
    public int getNumCopieDisponibili(){return numCopieDisponibili;}

    /// Setter del titolo.
    public void setTitolo(String titolo) { this.titolo = titolo; }

    /// Setter della lista degli autori.
    public void setAutori(List<Autore> autori) {
        this.autori = new ArrayList<>(autori);;
    }

    /// Setter dell'anno di pubblicazione.
    public void setAnno(int anno) { this.anno = anno; }

    /// Setter del codice ISBN.
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    // Setter del numero di copie TOTALI
    public void setNumCopieTotali(int numCopieTotali) { 
        this.numCopieTotali = numCopieTotali;
    }
    
    // Setter del numero di copie DISPONIBILI
    public void setNumCopieDisponibili(int numCopieDisponibili) { 
        this.numCopieDisponibili = numCopieDisponibili;
    }
     
    /**
     * @brief Aggiunge un autore alla lista degli autori.
     * 
     * @post Alla lista è stato aggiunto l'autore specificato in input.
     * 
     * @param a La variabile di tipo Autore che specifica il nome e il cognome dell'autore da aggiungere alla lista.
     */
    public void aggiungiAutore(Autore a) throws Exception{
        //Verifico che l'autore a non sia già presente nella lista
        //e in quel caso lo aggiungo
        if(!this.autori.contains(a)){
            this.autori.add(a);
        }
        else{
            throw new Exception("L'autore " + a.toString() + " è già presente in questo libro.");
        }
    }

    /**
     * @brief Rimuove un autore dalla lista degli autori.
     * 
     * @pre 'a' deve trovarsi nella lista.
     * @post Dalla lista è stato rimosso l'autore specificato in input.
     * 
     * @param a La variabile di tipo Autore che specifica il nome e il cognome dell'autore da rimuovere.
     */
    public void rimuoviAutore(Autore a) throws Exception{
        //AUTORE NON PRESENTE NELLA LISTA
        if(!this.autori.contains(a)){
            throw new Exception("L'autore " + a.toString() + " non è presente in questo libro.");
        }
        else{
            //AUTORE PRESENTE NELLA LISTA
            this.autori.remove(a);
        }
    }
   
    /**
     * @brief Permette di controllare che un libro sia preso in prestito
     * 
     * @return True se almeno una copia del libro è in prestito
     */
    public boolean isLibroInPrestito() {
        return this.numCopieTotali > this.numCopieDisponibili;
    }
    
    /**
     * @brief Controlla che `numCopie > 0`.
     * 
     * @return Restituisce `true` se `numCopie > 0`, altrimenti restituisce `false`.
     */
    public boolean isDisponibile() {
        return this.numCopieDisponibili > 0;
    }
    
    /**
     * @brief Aumenta il numero di copie disponibili dell'istanza corrente di Libro
     */
    public void aumentaCopie(){
        this.numCopieDisponibili++;
        
    }
    
    /**
     * @brief Diminuisce il numero di copie disponibili dell'istanza corrente di Libro
     * @pre Il numero di copie disponibili deve essere > 0.
     */
    public void diminuisciCopie()throws Exception{
        if(this.numCopieDisponibili > 0){
            this.numCopieDisponibili--;
        }
        else{
            throw new Exception("Non ci sono copie disponibili di questo libro per il prestito.");
        }
        
    }
    /**
     * @brief Ritorna una rappresentazione in stringa dell'oggetto Libro.
     * @return Una stringa contenente Titolo, Autori e il Codice ISBN .
     */
    @Override
    public String toString() {
        return String.format("%s (Autori : %s) [ISBN: %s]\n", titolo, autori.toString(), ISBN);
    }

    /**
     * @brief Definisce l'uguaglianza logica tra due oggetti Libro.
     * Due libri sono considerati uguali se hanno lo stesso Codice ISBN.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || ISBN == null) return false; 
        Libro libro = (Libro) o;
        return this.ISBN.equals(libro.ISBN);
    }

    /**
     * @brief Ritorna il codice hash dell'oggetto, basato sull'ISBN per coerenza con equals().
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return 31*hash + ISBN.hashCode();
    }
}
