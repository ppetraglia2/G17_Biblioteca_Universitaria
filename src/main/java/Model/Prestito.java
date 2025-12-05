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
     * @brief Controlla che la data di restituzione non sia inferiore alla data odierna.
     * @return Restituisce `true` se la data di restituzione è inferiore alla data odierna, quindi la restituzione è in ritardo. Altrimenti, restituisce `false`.
     * 
     * @param dataOdierna La data odierna di tipo LocalDate passata in input.
     */
    public boolean controllaRitardo(LocalDate dataOdierna) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
