/**
 * @file Prestito.java
 * @brief Il file contiene l'implementazione della classe Prestito.
 * 
 * La classe Prestito modella un prestito di un libro come una coppia Utente-Libro, specificandone anche una data prevista di restituzione.
 */

package Model;

import Model.Libro;
import java.time.LocalDate;

public class Prestito {

    private Utente utente;              ///< Uente che richiede il prestito.
    private Libro libro;                ///< Libro preso in prestito. 
    private LocalDate dataRestituzione; ///< Dta di restituzione prevista.

    /// Costruttore della classe.
    public Prestito(Utente utente, Libro libro, LocalDate dataRestituzione) {
        this.utente = utente;
        this.libro = libro;
        this.dataRestituzione = dataRestituzione;
    }

    /// Getter dell'utente.
    public Utente getUtente() { return utente; }

    /// Getter del libro.
    public Libro getLibro() { return libro; }

    /// Getter della data di restituzione.
    public LocalDate getDataRestituzione() { return dataRestituzione; }

    /// Setter dell'utente.
    public void setUtente(Utente utente) { this.utente = utente; }

    /// Setter del libro.
    public void setLibro(Libro libro) { this.libro = libro; }

    /// Setter della data di restituzione.
    public void setDataRestituzione(LocalDate dataRestituzione) { this.dataRestituzione = dataRestituzione; }

     /**
     * @brief Controlla se il prestito è in ritardo rispetto alla data odierna.
     * @return true se la data odierna è successiva alla data prevista di restituzione.
     */
    public boolean controllaRitardo() {
        return LocalDate.now().isAfter(this.dataRestituzione);
    }
    
    /**
     * @brief Ritorna una rappresentazione in stringa dell'oggetto Prestito.
     * @return Una stringa contenente Prestito, Titolo del libro, Nome e Cognome dell'Utente, Data Restituzione.
     */
    @Override
    public String toString() {
        return String.format("Prestito: %s a %s. --- Scadenza: %s (Ritardo: %s)", 
            libro.getTitolo(), 
            utente.getNome() + " " + utente.getCognome(), 
            dataRestituzione.toString(),
            controllaRitardo()? "SI" : "NO");
    }

    /**
     * @brief Definisce l'uguaglianza logica per identificare in modo univoco
     * un prestito attivo. Si considera univoco per la combinazione Utente-Libro-DataPrestito.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestito prestito = (Prestito) o;
        
        // Un prestito attivo è definito dall'Utente, dal Libro (ISBN) e dalla Data di Inizio.
        return utente.equals(prestito.utente) && 
                libro.equals(prestito.libro) && 
                dataRestituzione.equals(prestito.dataRestituzione);
    }

    /**
     * @brief Ritorna il codice hash, coerente con equals().
     */
    @Override
    public int hashCode() {
        int hash =7;
        return 31*hash + utente.hashCode() + libro.hashCode() + dataRestituzione.hashCode();
    }
}
