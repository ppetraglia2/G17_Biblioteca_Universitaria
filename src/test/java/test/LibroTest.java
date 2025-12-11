/**
 * @file LibroTest.java
 * @brief Test unitario per la classe Libro.
 */
package test;
import Model.Libro;
import Model.Autore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class LibroTest {
    private Libro libro;
    private Autore autore1;
    private Autore autore2;
    private ArrayList<Autore> listaAutoriIniziale;
    
    //DATI PER TEST
    private static final String TITOLO_INIZIALE = "I Promessi Sposi";
    private static final int ANNO_INIZIALE = 1827;
    private static final String ISBN_INIZIALE = "9788838439193";
    private static final int NUM_COPIE_TOTALI_INIZIALE = 5;
    
    private static final String TITOLO_NUOVO = "Il Sentiero dei Nidi di Ragno";
    private static final int ANNO_NUOVO = 1947;
    private static final String ISBN_NUOVO = "9788804637134";
    private static final int NUM_COPIE_TOTALI_NUOVO = 5;
    
    
    @BeforeEach
    void setUp() {
        //OGGETTI AUTORE
        autore1 = new Autore("Alessandro", "Manzoni");
        autore2 = new Autore("Italo", "Calvino");
        
        //LISTA AUTORI
        listaAutoriIniziale = new ArrayList<>();
        listaAutoriIniziale.add(autore1);
        listaAutoriIniziale.add(autore2);
        
        //LIBRO
        this.libro = new Libro(TITOLO_INIZIALE, listaAutoriIniziale,ANNO_INIZIALE,ISBN_INIZIALE, NUM_COPIE_TOTALI_INIZIALE,NUM_COPIE_TOTALI_INIZIALE);
        
    }
    
    
}
