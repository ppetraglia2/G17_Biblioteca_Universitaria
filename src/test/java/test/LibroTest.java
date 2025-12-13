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
        
        ArrayList<Autore> nuovaLista = new ArrayList<>();
        nuovaLista.add(AUTORE2);
        
        libro.setAutori(nuovaLista);
        
        // Assert
        assertEquals(nuovaLista, libro.getAutori(), "La lista autori deve essere aggiornato dal setter della lista autori.");
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
    
    @Test
    public void testAggiungiAutore() throws Exception{
        libro.aggiungiAutore(AUTORE2);
        
        assertEquals(2, libro.getAutori().size(), "La grandezza della lista deve essere 2 dopo l'aggiunta di 2 autori");
        assertTrue(libro.getAutori().contains(AUTORE2), "La lista deve contenere il nuovo autore");
        assertTrue(libro.getAutori().contains(AUTORE1), "La lista deve contenere il vecchio autore");
    }
    
    @Test
    public void testAggiungiAutore_Presente(){
        
        // Verifico che si scateni l'eccezione, senza far crashare il programma di test.
       assertThrows(Exception.class, () -> {
            libro.aggiungiAutore(AUTORE1);
        }, "Deve lanciare un'eccezione se si prova ad aggiungere un autore già aggiunto.");
        
        assertEquals(1, libro.getAutori().size(), "La lista non deve cambiare dimensione se viene lanciata un'eccezione.");
    }
    
    @Test
    public void testRimuoviAutore() throws Exception{
        libro.rimuoviAutore(AUTORE1);
        
        assertEquals(0, libro.getAutori().size(), "La grandezza della lista deve essere 0 dopo la rimozione dell'unico autore.");
        assertFalse(libro.getAutori().contains(AUTORE1), "La lista non deve contenere l'autore dopo la sua rimozione.");
    }
    
    @Test
    public void testRimuoviAutore_UnoSuDue() throws Exception{
        libro.aggiungiAutore(AUTORE2);
        
        libro.rimuoviAutore(AUTORE1);
        
        assertEquals(1, libro.getAutori().size(), "La grandezza della lista deve essere 1 dopo la rimozione di un autore da una lista di due autori.");
        assertFalse(libro.getAutori().contains(AUTORE1), "La lista non deve contenere l'autore dopo la sua rimozione.");
        assertTrue(libro.getAutori().contains(AUTORE2), "La lista deve contenere il secondo autore anche dopo la rimozione del primo.");
    }
    
    @Test
    public void testRimuoviAutore_NonPresente(){
        assertThrows(Exception.class, () -> {
            libro.rimuoviAutore(AUTORE2);
        }, "Deve lanciare un'eccezione se si prova a rimuovere un autore non aggiunto.");
        
        assertEquals(1, libro.getAutori().size(), "La lista non deve cambiare dimensione se viene lanciata un'eccezione.");
    }
    
    @Test
    public void testIsLibroInPrestito(){
        
        libro.setNumCopieDisponibili(NUM_COPIE_DISPONIBILI_NUOVO);
        // Totali = 3, Disponibili = 2.
        
        assertTrue(libro.isLibroInPrestito(), "Deve restituire true quando il numero di copie totali supera quello di copie disponibili.");
    }
    
    @Test
    public void testIsLibroInPrestito_NoPrestito(){
        // Totali = 3, Disponibili = 3.
        
        assertFalse(libro.isLibroInPrestito(), "Deve restituire false quando il numero di copie totali non supera quello di copie disponibili.");
    }
    
    @Test
    public void testIsDisponibile() {
        // Disponibili = 3.
        
        assertTrue(libro.isDisponibile(), "Deve restituire true quando il numero di copie disponibili è maggiore di zero.");
    }
    
    @Test
    public void testIsDisponibile_NonDisponibile(){
        libro.setNumCopieDisponibili(0);
        
        assertFalse(libro.isDisponibile(), "Deve restituire false quando il numero di copie disponibili non è maggiore di zero.");
    }
    
    @Test
    public void testAumentaCopie(){
        libro.aumentaCopie();
        
        assertEquals(4, libro.getNumCopieDisponibili(), "Il numero di copie disponibili deve aumentare di uno.");
    }
    
    @Test
    public void testDiminuisciCopie_NonPresenti(){
        libro.setNumCopieDisponibili(0);
        assertThrows(Exception.class, () -> {
            libro.diminuisciCopie();
        }, "Deve lanciare un'eccezione se si prova a rimuovere una copia, ma le copie sono 0.");
    }
    
    @Test
    public void testDiminuisciCopie() throws Exception {
        libro.diminuisciCopie();
        
        assertEquals(2, libro.getNumCopieDisponibili(), "Il numero di copie disponibili deve essere 2 dopo aver rimosso la terza copia.");
    }
    
    // --- Test di toString() ---
    @Test
    public void testToString(){
        String expectedString = String.format("%s (Autori : %s) [ISBN: %s]\n",
                TITOLO_INIZIALE, libro.autoriToString(), ISBN_INIZIALE);
        //Assert
        assertEquals(expectedString, libro.toString(), "toString() deve restituire la stringa formattata.");
    }
    
    // --- Test di autoriToString() ---
    @Test
    void autoriToString_AutoreSingolo_FormatoCorrettoConVirgolaFinale() {
        Autore a1 = new Autore("Nome","Cognome");
        ArrayList<Autore> singoloAutore = new ArrayList<>();
        singoloAutore.add(a1);
        
        Libro libro = new Libro("Titolo", singoloAutore, 2000, "1111111111111", 1, 1);
        
        String result = libro.autoriToString();
        
        // Risultato atteso, inclusa la virgola e lo spazio finali non rimossi dal metodo
        String expected = "Nome Cognome, "; 
        
        assertEquals(expected, result, "autoriToString non formatta correttamente autori multipli.");
    }
    
    @Test
    void autoriToString_ListaVuota_RitornaStringaVuota() {
        Libro libroSenzaAutori = new Libro("Anonimo", new ArrayList<>(), 2000, "2222222222222", 1, 1);
        
        String result = libroSenzaAutori.autoriToString();
        
        String expected = "";
        
        assertEquals(expected, result, "autoriToString con lista vuota deve ritornare una stringa vuota.");
    }
    
    @Test
    void autoriToString_AutoriMultipli_FormatoCorrettoConVirgolaFinale() {
        Autore a1 = new Autore("Nome","Cognome");
        Autore a2 = new Autore("Nome2", "Cognome2");
        ArrayList<Autore> piuAutori = new ArrayList<>();
        piuAutori.add(a1);
        piuAutori.add(a2);
        
        Libro libro = new Libro("Titolo", piuAutori, 2000, "1111111111111", 1, 1);
        
        String result = libro.autoriToString();
        
        // Risultato atteso, inclusa la virgola e lo spazio finali non rimossi dal metodo
        String expected = "Nome Cognome, Nome2 Cognome2, "; 
        
        assertEquals(expected, result, "autoriToString non formatta correttamente autori multipli.");
    }
    
    
    // --- Test di equals() ---
    @Test
    public void testEquals_stessaIstanza(){
        assertTrue(libro.equals(libro),"L'oggetto deve essere uguale a sé stesso.");
    }
    
    @Test
    public void testEquals_OggettiUguali(){
        //Istanzio due libri con gli stessi dati
        Libro libro2 = new Libro(TITOLO_INIZIALE, listaAutori, ANNO_INIZIALE, ISBN_INIZIALE, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);

        // Assert
        assertTrue(libro.equals(libro2), "Due oggetti con gli stessi dati devono essere uguali.");
    }
    
    @Test
    public void testEquals_ISBNUguale(){
        //Istanzio due libri con stesso ISBN
        Libro libro2 = new Libro(TITOLO_NUOVO, listaAutori, ANNO_NUOVO, ISBN_INIZIALE, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);

        // Assert
        assertTrue(libro.equals(libro2), "Due libri con lo stesso ISBN devono essere uguali, anche se il resto cambia.");  
    }
    
    @Test
    public void testEquals_ISBNDiverso(){
        //Istanzio due libri con ISBN diverso
        Libro libro2 = new Libro(TITOLO_INIZIALE, listaAutori, ANNO_INIZIALE, ISBN_NUOVO, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);
        
        //Assert
        assertFalse(libro.equals(libro2),"Due oggetti con ISBN diverso non devono essere uguali, anche se il resto è uguale."); 
    }
    
    @Test
    public void testEquals_NULL(){
        //Assert
        assertFalse(libro.equals(null), "L'oggetto non deve essere uguale a NULL.");
    }
    
    @Test
    public void testEquals_AltraClasse(){
        //Istanzio oggetto appartenente ad un'altra classe
        Object altraClasse = new Object(); 
        
        //Assert
        assertFalse(libro.equals(altraClasse), "L'oggetto non deve essere uguale ad un oggetto di un'altra classe.");
    }
    
    // --- Test HashCode ---
    
    @Test
    void testHashCode_OggettiUguali() {
        //Istanzio due oggetti con gli stessi dati
        Libro libro2 = new Libro(TITOLO_INIZIALE, listaAutori, ANNO_INIZIALE, ISBN_INIZIALE, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);
        //Assert
        assertEquals(libro.hashCode(),libro2.hashCode(),"L'hashcode deve essere uguale per due oggetti uguali."); 
    }
    
    @Test
    void testHashCode_OggettiDiversi() {
        //Istanzio due oggetti con dati diversi
        Libro libro2 = new Libro(TITOLO_INIZIALE, listaAutori, ANNO_INIZIALE, ISBN_NUOVO, NUM_COPIE_TOTALI, NUM_COPIE_DISPONIBILI_INIZIALE);
        //Assert
        assertNotEquals(libro.hashCode(),libro2.hashCode(),"L'hashcode deve essere diverso per due oggetti diversi."); 
    }
}
