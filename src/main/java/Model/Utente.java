/**
 * @file Utente.java
 * @brief Questo file contiene la gestione degli utenti.
 * 
 * La classe Utente racchiude tutte le informazioni relative agli utenti della biblioteca.
 */

package Model;

public class Utente {

    private String nome;            ///< Nome dell'utente
    private String cognome;         ///< Cognome dell'utente
    private String matricola;       ///< Numero di matricola
    private String email;           ///< Email istituzionale
    private int numPrestitiAttivi;  ///< Numero di Prestiti attivi dell'Utente

    ///Costruttore della classe Utente
    public Utente(String nome, String cognome, String matricola, String email, int numPrestitiAttivi) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.numPrestitiAttivi = numPrestitiAttivi;
    }

    ///@brief Getter del nome
    public String getNome() { return nome; }

    ///@brief Getter del cognome
    public String getCognome() { return cognome; }

    ///@brief Getter della matricola
    public String getMatricola() { return matricola; }

    ///Getter del nome
    public String getEmail() { return email; }
    
    //Getter del numero di prestiti attivi
    public int getNumPrestitiAttivi(){return numPrestitiAttivi;}

    ///Setter del nome
    public void setNome(String nome) { this.nome = nome; }

    ///Setter del cognome
    public void setCognome(String cognome) { this.cognome = cognome; }

    ///Setter della matricola
    public void setMatricola(String matricola) { this.matricola = matricola; }

    ///Setter del'email
    public void setEmail(String email) { this.email = email; }
    
    //Setter del numero di prestiti attivi
    public void setNumPrestitiAttivi(int npa) {this.numPrestitiAttivi = npa;}

    /**
     * @brief Controlla se il numero di prestiti in cui compare l'utente è stato superato
     * 
     * Il numero di prestiti per ogni utente dev'essere `<= 3`
     *
     * @return True se il limite è stato raggiunto
     */
    public boolean limitePrestiti() {
        return this.numPrestitiAttivi >= 3;
    }
    
    /**
     * @brief Controlla se l'istanza corrente di Utente ha libri in prestito.
     * @return Restituisce `true` se l'Utente ha dei libri in prestito.
     */
    public boolean inPrestito() {
        return this.numPrestitiAttivi > 0;
    }
    
    
    /**
     * @brief Incrementa il numero di prestiti attivi dell'utente
     */
    public void incrementaPrestitiAttivi(){
        this.numPrestitiAttivi++;
    }
    
    /**
     * @brief Decrementa il numero di prestiti attivi dell'utente
     */
    public void decrementaPrestitiAttivi(){
        this.numPrestitiAttivi--;
    }
    
    /**
     * @brief Ritorna una rappresentazione in stringa dell'oggetto Utente.
     * @return Una stringa contenente Nome, Cognome, Matricola, Email Istituzionale, Numero di Prestiti Attivi.
     */
    @Override
    public String toString() {
        return String.format("%s %s (Matr: %s, Email: %s) - Prestiti Attivi: %d", 
            nome, cognome, matricola, email, numPrestitiAttivi);
    }

    /**
     * @brief Definisce l'uguaglianza logica tra due oggetti Utente.
     * Due utenti sono considerati uguali se hanno la stessa Matricola.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass() || matricola == null) return false; 
        Utente utente = (Utente) o;
        return matricola.equals(utente.matricola);
    }

    /**
     * @brief Ritorna il codice hash dell'oggetto, basato sulla Matricola per coerenza con equals().
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return 31*hash + matricola.hashCode();
    }
    
    
}
