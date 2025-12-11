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
    
    private ArrayList<Autore> listaAutori;
    //DATI PER TEST
    private static final String TITOLO_INIZIALE = "Lo Squalificato";
    private static final Autore AUTORE1 = new Autore("Osamu", "Dazai");
    private static final int ANNO_INIZIALE = 1948;
    private static final String ISBN_INIZIALE = "9788807050268";
    private static final int NUM_COPIE_TOTALI = 3;
    private static final int NUM_COPIE_DISPONIBILI_INIZIALE = 3;
    
    private static final String TITOLO_NUOVO = "50 animali in via d'estinzione";
    private static final Autore AUTORE2 = new Autore("Willy", "Guasti");
    private static final int ANNO_NUOVO = 2025;
    private static final String ISBN_NUOVO = "9791221092752";
    private static final int NUM_COPIE_DISPONIBILI_NUOVO = 2;
    
    @BeforeEach
    void setUp() {
        // Inizializza la lista con il primo autore
        listaAutori = new ArrayList<>();
        listaAutori.add(AUTORE1);
        
        // Inizializza l'oggetto base prima di ogni test
        this.libro = new Libro(TITOLO_INIZIALE, listaAutori, ANNO_INIZIALE, ISBN_INIZIALE, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);
    }
    
    // --- Test del Costruttore e dei Getter ---
    @Test
    public void testGetter() {
    
        // 2. Assert
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo deve corrispondere al valore passato nel costruttore.");
        assertEquals(listaAutori, libro.getAutori(), "L'autore deve corrispondere al valore passato nel costruttore.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "L'ISBN deve corrispondere al valore passato nel costruttore.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno deve corrispondere al valore passato nel costruttore.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali deve corrispondere al valore passato nel costruttore");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili deve corrispondere al valore passato nel costruttore");
    }
    
     
    // --- Test dei Setter ---
    @Test
    public void testSetTitolo(){
        libro.setTitolo(TITOLO_NUOVO);
        
        // Assert
        assertEquals(TITOLO_NUOVO, libro.getTitolo(), "Il titolo deve essere aggiornato dal setter del titolo.");
        assertEquals(listaAutori, libro.getAutori(), "La lista autori non deve essere modificata dal setter del titolo.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "Il codice ISBN non deve essere modificato dal setter del titolo.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno non deve essere modificato dal setter del titolo.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali non deve essere modificato dal setter del titolo.");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili non deve essere modificato dal setter del titolo.");
        
    }
    
    @Test
    public void testSetAutori(){
        libro.setAutori(listaAutori);
        
        // Assert
        assertEquals(listaAutori, libro.getAutori(), "La lista autori deve essere aggiornato dal setter della lista autori.");
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo non deve essere modificato dal setter della lista autori.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "Il codice ISBN non deve essere modificato dal setter della lista autori.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno non deve essere modificato dal setter della lista autori.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali non deve essere modificato dal setter della lista autori.");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili non deve essere modificato dal setter della lista autori.");
        
    }
    
    @Test
    public void testSetAnno(){
        libro.setAnno(ANNO_NUOVO);
        
        // Assert
        assertEquals(ANNO_NUOVO, libro.getAnno(), "L'anno deve essere aggiornato dal setter dell'anno");
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo non deve essere modificato dal setter dell'anno");
        assertEquals(listaAutori, libro.getAutori(), "La lista autori non deve essere modificata dal setter dell'anno.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "L'ISBN non deve essere modificato dal setter dell'anno.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali non deve essere modificato dal setter dell'anno.");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili non deve essere modificato dal setter dell'anno.");
    }
    
    @Test
    public void testSetISBN(){
        libro.setISBN(ISBN_NUOVO);
        
        // Assert
        assertEquals(ISBN_NUOVO, libro.getISBN(), "L'ISBN deve essere aggiornato dal setter dell'ISBN.");
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo non deve essere modificato dal setter dell'ISBN.");
        assertEquals(listaAutori, libro.getAutori(), "La lista autori non deve essere modificata dal setter dell'ISBN.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno non deve essere modificato dal setter dell'ISBN.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali non deve essere modificato dal setter dell'ISBN.");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili non deve essere modificato dal setter dell'ISBN.");
        
    }
    
    @Test
    public void testSetNumCopieTotali(){
        libro.setNumCopieTotali(4);
        
        // Assert
        assertEquals(4, libro.getNumCopieTotali(), "Il numero di copie totali deve essere modificato dal setter del numero di copie totali.");
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo non deve essere modificato dal setter del numero di copie totali.");
        assertEquals(listaAutori, libro.getAutori(), "La lista autori non deve essere modificata dal setter del numero di copie totali.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno non deve essere modificato dal setter del numero di copie totali.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "L'ISBN non deve essere modificato dal setter del numero di copie totali.");
        assertEquals(NUM_COPIE_DISPONIBILI_INIZIALE, libro.getNumCopieDisponibili(),"Il numero di copie disponibili non deve essere modificato dal setter del numero di copie totali.");
        
    }
    
    
    @Test
    public void testSetNumCopieDisponibili(){
        libro.setNumCopieDisponibili(1);
        
        // Assert
        assertEquals(1, libro.getNumCopieDisponibili(),"Il numero di copie disponibili deve essere modificato dal setter del numero di copie disponibili.");
        assertEquals(TITOLO_INIZIALE, libro.getTitolo(), "Il titolo non deve essere modificato dal setter del numero di copie disponibili.");
        assertEquals(listaAutori, libro.getAutori(), "La lista autori non deve essere modificata dal setter del numero di copie disponibili.");
        assertEquals(ANNO_INIZIALE, libro.getAnno(), "L'anno non deve essere modificato dal setter del numero di copie disponibili.");
        assertEquals(ISBN_INIZIALE, libro.getISBN(), "L'ISBN non deve essere modificato dal setter del numero di copie disponibili.");
        assertEquals(NUM_COPIE_TOTALI, libro.getNumCopieTotali(),"Il numero di copie totali non deve essere modificato dal setter del numero di copie disponibili.");
        
    }
}
