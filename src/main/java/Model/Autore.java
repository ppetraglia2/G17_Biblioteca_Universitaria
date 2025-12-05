/**
 * @file Autore.java
 * @brief Questo file contiene la gestione degli autori.
 * 
 * La classe Autore definirà i componenti di una lista che farà parte degli attributi della classe Libro.
 */

package Model;

public class Autore {

    private String nome;    ///< Nome dell'autore
    private String cognome; ///< Cognome dell'autore
    
    ///Costruttore della classe Autore
    public Autore(String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }

    ///Getter del Nome
    public String getNome() { return nome; }

    ///Getter del cognome
    public String getCognome() { return cognome; }

    ///Setter del Nome
    public void setNome(String nome) { this.nome = nome; }

    ///Setter del Cognome
    public void setCognome(String cognome) { this.cognome = cognome; }   
}