/**
 * @file Utente.java
 * @brief Questo file contiene la gestione degli utenti.
 * 
 * La classe Utente racchiude tutte le informazioni relative agli utenti della biblioteca.
 */

package Model;

public class Utente {

    private String nome;    ///< Nome dell'utente
    private String cognome; ///< Cognome dell'utente
    private int matricola;  ///< Numero di matricola
    private String email;   ///< Email istituzionale

    ///Costruttore della classe Utente
    public Utente(String nome, String cognome, int matricola, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
    }

    ///@brief Getter del nome
    public String getNome() { return nome; }

    ///@brief Getter del cognome
    public String getCognome() { return cognome; }

    ///@brief Getter della matricola
    public int getMatricola() { return matricola; }

    ///Getter del nome
    public String getEmail() { return email; }

    ///Setter del nome
    public void setNome(String nome) { this.nome = nome; }

    ///Setter del cognome
    public void setCognome(String cognome) { this.cognome = cognome; }

    ///Setter della matricola
    public void setMatricola(int matricola) { this.matricola = matricola; }

    ///Setter del'email
    public void setEmail(String email) { this.email = email; }

    /**
     * @brief Controlla se il numero di prestiti in cui compare l'utente è stato superato
     * 
     * Il numero di prestiti per ogni utente dev'essere `<= 3`
     *
     * @return True se il limite è stato raggiunto
     */
    public boolean limitePrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
