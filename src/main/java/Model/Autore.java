/**
 * @file Autore.java
 * @brief Questo file contiene la gestione degli autori.
 * 
 * La classe Autore definirà i componenti di una lista che farà parte degli attributi della classe Libro.
 */

package Model;

import java.util.Objects;

public class Autore {

    /// Nome dell'autore.
    private String nome;
    
    /// Cognome dell'autore.
    private String cognome;
    
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
    
    /**
     * @brief Ritorna una rappresentazione in stringa dell'oggetto Autore.
     * @return Una stringa contenente Nome e Cognome.
     */
    @Override
    public String toString(){
        return nome + " " + cognome;
    }
    
    /**
     * @brief Definisce l'uguaglianza logica tra due oggetti Autore. 
     * Due autori sono considerati uguali se hanno lo stesso Nome E lo stesso Cognome.
     * @param o L'oggetto da confrontare.
     * @return true se gli oggetti sono logicamente uguali.
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Autore a = (Autore) o;
        return Objects.equals(this.nome,a.nome) && Objects.equals(this.cognome, a.cognome);
    }
    
    /**
     * @brief Ritorna il codice hash dell'oggetto, coerente con il metodo equals().
     * 
     * @return Il codice hash dell'oggetto.
     */
    @Override
    public int hashCode(){
        int hash = 7;
        return 31*hash + nome.hashCode() + cognome.hashCode();
    }
}
