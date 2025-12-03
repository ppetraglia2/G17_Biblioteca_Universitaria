package Model;

import java.util.ArrayList;
import java.util.List;

public class Libro {

    private String titolo;
    private List<Autore> autori;
    private int anno;
    private int ISBN;
    private int numCopie;

    public Libro(String titolo, ArrayList<Autore> autori, int anno, int ISBN, int numCopie) {
        this.titolo = titolo;
        this.autori = new ArrayList<Autore>();
        this.anno = anno;
        this.ISBN = ISBN;
        this.numCopie = numCopie;
    }

    public void aggiungiAutore(Autore a) {
    }

    public void rimuoviAutore(Autore a) {
    }

    public boolean isDisponibile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
