/**
 * @file MainController.java
 * @brief Controller principale per l'interfaccia utente.
 *
 * Questa classe gestisce gli eventi dell'utente, l'aggiornamento degli elementi grafici
 * (TableView, campi di testo, ecc.) e invia le richieste di sistema alla classe Biblioteca.
 * È associata al file FXML principale.
 */
package Controller;

import Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane; // Modificato per compatibilità con FXML
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    
    // --- Riferimento al Model ---
    private Biblioteca biblioteca;
    
    // --- ELEMENTI GRAFICI (Iniettati da FXML) ---
    
    // Pannelli principali
    // NOTA: Cambiati da VBox a AnchorPane per corrispondere al main.fxml
    @FXML private AnchorPane paneLibri;
    @FXML private AnchorPane paneUtenti;
    @FXML private AnchorPane panePrestiti;

    // Campi di Ricerca
    @FXML private TextField searchLibri;
    @FXML private TextField searchUtenti;

    // Tabella Libri
    @FXML private TableView<Libro> tableLibri;
    @FXML private TableColumn<Libro, String> colLibTitolo;
    @FXML private TableColumn<Libro, String> colLibAutori;
    @FXML private TableColumn<Libro, String> colLibIsbn;
    @FXML private TableColumn<Libro, String> colLibCopie;
    @FXML private TableColumn<Libro, Integer> colLibAnno;
    @FXML private TableColumn<Libro, Void> colLibAzioni;

    // Tabella Utenti
    @FXML private TableView<Utente> tableUtenti;
    @FXML private TableColumn<Utente, String> colUtNome;
    @FXML private TableColumn<Utente, String> colUtCognome;
    @FXML private TableColumn<Utente, String> colUtMatr;
    @FXML private TableColumn<Utente, Integer> colUtPrestiti;
    @FXML private TableColumn<Utente, Void> colUtAzioni;

    // Tabella Prestiti
    @FXML private TableView<Prestito> tablePrestiti;
    @FXML private TableColumn<Prestito, String> colPresUtente;
    @FXML private TableColumn<Prestito, String> colPresLibro;
    @FXML private TableColumn<Prestito, String> colPresData;
    @FXML private TableColumn<Prestito, String> colPresStato;
    @FXML private TableColumn<Prestito, Void> colPresAzioni;
    
    
    // --- Metodi di Inizializzazione ---
    
    /**
     * @brief Metodo chiamato automaticamente dopo il caricamento del file FXML.
     *
     * Inizializza il Controller: carica i dati (se esistono), inizializza la Biblioteca
     * e configura gli elementi grafici (colonne delle tabelle, filtri, ecc.).
     */
    @FXML
    public void initialize() {
        // Inizializza il modello
        biblioteca = new Biblioteca();
        
        // Configura le colonne delle tabelle
        configuraLibri();
        configuraUtenti();
        configuraPrestiti();

        // Collega i dati del modello alle tabelle
        if (biblioteca.getFlLibreria() != null)
            tableLibri.setItems(biblioteca.getFlLibreria());
        
        if (biblioteca.getFlClienti() != null)
            tableUtenti.setItems(biblioteca.getFlClienti());
            
        if (biblioteca.getObPrestiti() != null)
            tablePrestiti.setItems(biblioteca.getObPrestiti());

        // Listener per la ricerca in tempo reale
        if (searchLibri != null) {
            searchLibri.textProperty().addListener((obs, oldVal, newVal) -> biblioteca.filtraLibri(newVal));
        }
        if (searchUtenti != null) {
            searchUtenti.textProperty().addListener((obs, oldVal, newVal) -> biblioteca.filtraUtenti(newVal));
        }
    }

    /**
     * @brief Configura la TableView e le colonne per la visualizzazione dei Libri.
     *
     * Collega la lista filtrata dei Libri (flLibreria) alla TableView.
     */
    public void configuraLibri() {
        colLibTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitolo()));
        colLibAutori.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().autoriToString())); 
        colLibAnno.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getAnno()));
        colLibIsbn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getISBN()));
        // Mostra copie disponibili / totali
        colLibCopie.setCellValueFactory(d -> new SimpleStringProperty(
            d.getValue().getNumCopieDisponibili() + "/" + d.getValue().getNumCopieTotali()));

        // Colonna Azioni
        colLibAzioni.setCellFactory(param -> new TableCell<Libro, Void>() {
            private final Button btnEdit = new Button("Modif.");
            private final Button btnDel = new Button("Elim.");
            private final HBox pane = new HBox(5, btnEdit, btnDel);

            {
                btnDel.setStyle("-fx-text-fill: red;");
                btnEdit.setOnAction(event -> {
                    Libro l = getTableView().getItems().get(getIndex());
                    modificaLibro(l);
                });
                btnDel.setOnAction(event -> {
                    Libro l = getTableView().getItems().get(getIndex());
                    try {
                        biblioteca.eliminaLibro(l);
                    } catch (Exception ex) {
                        alertErrore(ex.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    /**
     * @brief Configura la TableView e le colonne per la visualizzazione degli Utenti.
     *
     * Collega la lista filtrata degli Utenti (flClienti) alla TableView.
     */
    public void configuraUtenti() {
        colUtNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colUtCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCognome()));
        colUtMatr.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricola()));
        colUtPrestiti.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getNumPrestitiAttivi()));

        colUtAzioni.setCellFactory(param -> new TableCell<Utente, Void>() {
            private final Button btnEdit = new Button("Modif.");
            private final Button btnDel = new Button("Elim.");
            private final HBox pane = new HBox(5, btnEdit, btnDel);

            {
                btnDel.setStyle("-fx-text-fill: red;");
                btnEdit.setOnAction(event -> {
                    Utente u = getTableView().getItems().get(getIndex());
                    modificaUtente(u);
                });
                btnDel.setOnAction(event -> {
                    Utente u = getTableView().getItems().get(getIndex());
                    try {
                        biblioteca.eliminaUtente(u);
                    } catch (Exception ex) {
                        alertErrore(ex.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    /**
     * @brief Configura la TableView e le colonne per la visualizzazione dei Prestiti.
     *
     * Collega la lista completa dei Prestiti (obPrestiti) alla TableView.
     */
    private void configuraPrestiti() {
        colPresUtente.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUtente().getCognome()));
        colPresLibro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getLibro().getTitolo()));
        colPresData.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDataRestituzione().toString()));

        // Configurazione stato (Verde = OK, Rosso = Ritardo)
        colPresStato.setCellFactory(c -> new TableCell<Prestito, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Prestito p = (Prestito) getTableRow().getItem();
                    boolean ritardo = p.controllaRitardo();
                    setText(ritardo ? "RITARDO" : "Attivo");
                    setTextFill(ritardo ? Color.RED : Color.GREEN);
                }
            }
        });

        // Tasto restituzione
        colPresAzioni.setCellFactory(p -> new TableCell<Prestito, Void>() {
            private final Button btnRet = new Button("Rientro");
            {
                btnRet.setOnAction(e -> {
                    Prestito prestito = getTableView().getItems().get(getIndex());
                    try {
                        biblioteca.restituisciPrestito(prestito);
                        // Refresh necessario per aggiornare contatori e disponibilità
                        tablePrestiti.refresh();
                        tableLibri.refresh();
                        tableUtenti.refresh();
                    } catch (Exception ex) {
                        alertErrore(ex.getMessage());
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRet);
            }
        });
    }
    
    // --- Metodi di Visualizzazione/Navigazione ---

    /**
     * @brief Mostra la vista relativa alla gestione dei Libri.
     */
    @FXML
    public void mostraLibri() {
        cambiaScena(paneLibri);
    }

    /**
     * @brief Mostra la vista relativa alla gestione degli Utenti.
     */
    @FXML
    public void mostraUtenti() {
        cambiaScena(paneUtenti);
    }

    /**
     * @brief Mostra la vista relativa alla gestione dei Prestiti.
     */
    @FXML
    public void mostraPrestiti() {
        cambiaScena(panePrestiti);
    }

    /**
     * @brief Gestisce la visibilità dei pannelli principali.
     *
     * Nasconde tutti i pannelli e rende visibile solo quello specificato.
     *
     * @param paneToShow Il pannello (AnchorPane/Pane) da rendere visibile.
     */
    private void cambiaScena(Pane paneToShow) {
        if(paneLibri != null) paneLibri.setVisible(false);
        if(paneUtenti != null) paneUtenti.setVisible(false);
        if(panePrestiti != null) panePrestiti.setVisible(false);
        
        if(paneToShow != null) paneToShow.setVisible(true);
    }
    
    // --- Metodi per gestire le Azioni (Event Handlers) ---

    /**
     * @brief Avvia il flusso per l'aggiunta di un nuovo Libro.
     *
     * Apre una finestra per l'inserimento dei dati.
     */
    @FXML
    public void nuovoLibro() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Nuovo Libro");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        TextField tTitolo = new TextField();
        TextField tAutori = new TextField();
        TextField tAnno = new TextField();
        TextField tIsbn = new TextField();
        TextField tCopie = new TextField();
        
        g.addRow(0, new Label("Titolo:"), tTitolo);
        g.addRow(1, new Label("Autori (virgola):"), tAutori);
        g.addRow(2, new Label("Anno:"), tAnno);
        g.addRow(3, new Label("ISBN:"), tIsbn);
        g.addRow(4, new Label("Copie:"), tCopie);
        
        d.getDialogPane().setContent(g);
        
        d.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    List<Autore> newautori = parseAutori(tAutori.getText());
                    // Gestione base per la conversione intero
                    int newanno = Integer.parseInt(tAnno.getText().trim());
                    int newcopie = Integer.parseInt(tCopie.getText().trim());
                    
                    biblioteca.aggiungiLibro(
                        tTitolo.getText(), 
                        newautori, 
                        newanno,
                        tIsbn.getText(), 
                        newcopie,
                        newcopie
                    );
                } catch (NumberFormatException ne) {
                    alertErrore("Errore: Anno e Copie devono essere numeri interi.");
                } catch (Exception e) {
                    alertErrore("Errore inserimento: " + e.getMessage());
                }
            }
        });
    }

    /**
     * @brief Avvia il flusso per l'aggiunta di un nuovo Utente.
     *
     * Apre una finestra per l'inserimento dei dati.
     */
    @FXML
    public void nuovoUtente() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Nuovo Utente");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        TextField tNome = new TextField();
        TextField tCognome = new TextField();
        TextField tMatricola = new TextField();
        TextField tEmail = new TextField();
        
        g.addRow(0, new Label("Nome:"), tNome);
        g.addRow(1, new Label("Cognome:"), tCognome);
        g.addRow(2, new Label("Matricola:"), tMatricola);
        g.addRow(3, new Label("Email:"), tEmail);
        
        d.getDialogPane().setContent(g);
        
        d.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    biblioteca.aggiungiUtente(
                        tNome.getText(), 
                        tCognome.getText(), 
                        tMatricola.getText(), 
                        tEmail.getText(),
                        0
                    );
                } catch (Exception e) {
                    alertErrore("Errore inserimento: " + e.getMessage());
                }
            }
        });
    }

    /**
     * @brief Avvia il flusso per la registrazione di un nuovo Prestito.
     *
     * Apre una finestra per l'inserimento dei dati.
     */
    @FXML
    public void nuovoPrestito() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Nuovo Prestito");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        
        ComboBox<Utente> comboUtenti = new ComboBox<>(biblioteca.getObClienti());
        ComboBox<Libro> comboLibri = new ComboBox<>(biblioteca.getObLibreria());
        DatePicker datePicker = new DatePicker(LocalDate.now().plusDays(30)); 
        
        g.addRow(0, new Label("Utente:"), comboUtenti);
        g.addRow(1, new Label("Libro:"), comboLibri);
        g.addRow(2, new Label("Scadenza:"), datePicker);
        
        d.getDialogPane().setContent(g);
        
        d.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (comboUtenti.getValue() == null || comboLibri.getValue() == null) {
                        throw new IllegalArgumentException("Selezionare utente e libro.");
                    }
                    biblioteca.aggiungiPrestito(
                        comboUtenti.getValue(), 
                        comboLibri.getValue(), 
                        datePicker.getValue()
                    );
                } catch (Exception e) {
                    alertErrore(e.getMessage());
                }
            }
        });
    }

    /**
     * @brief Avvia il flusso per la modifica di un Libro esistente.
     *
     * @param l L'oggetto Libro da modificare (selezionato dalla tabella).
     */
    @FXML
    public void modificaLibro(Libro l) {
        if (l == null) return;

        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Modifica Libro");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        
        TextField tTitolo = new TextField(l.getTitolo());
        TextField tAutori = new TextField(l.autoriToString());
        TextField tAnno = new TextField(String.valueOf(l.getAnno()));
        TextField tIsbn = new TextField(l.getISBN());
        TextField tCopie = new TextField(String.valueOf(l.getNumCopieTotali()));
        
        g.addRow(0, new Label("Titolo:"), tTitolo);
        g.addRow(1, new Label("Autori:"), tAutori);
        g.addRow(2, new Label("Anno:"), tAnno);
        g.addRow(3, new Label("ISBN:"), tIsbn);
        g.addRow(4, new Label("Copie Tot:"), tCopie);
        
        d.getDialogPane().setContent(g);
        
        d.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int newanno = Integer.parseInt(tAnno.getText().trim());
                    int newcopie = Integer.parseInt(tCopie.getText().trim());
                    List<Autore> newautori = parseAutori(tAutori.getText());
                    
                    biblioteca.modificaLibro(l, tTitolo.getText(), newautori, newanno, tIsbn.getText(), newcopie);
                    tableLibri.refresh();
                } catch (Exception e) {
                    alertErrore(e.getMessage());
                }
            }
        });
    }

    /**
     * @brief Avvia il flusso per la modifica di un Utente esistente.
     *
     * @param u L'oggetto Utente da modificare (selezionato dalla tabella).
     */
    @FXML
    public void modificaUtente(Utente u) {
        if (u == null) return;

        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Modifica Utente");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        
        TextField tNome = new TextField(u.getNome());
        TextField tCognome = new TextField(u.getCognome());
        TextField tMatricola = new TextField(u.getMatricola());
        TextField tEmail = new TextField(u.getEmail());
        
        g.addRow(0, new Label("Nome:"), tNome);
        g.addRow(1, new Label("Cognome:"), tCognome);
        g.addRow(2, new Label("Email:"), tEmail);
        
        d.getDialogPane().setContent(g);
        
        d.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    biblioteca.modificaUtente(u, tNome.getText(), tCognome.getText(), tMatricola.getText(), tEmail.getText());
                    tableUtenti.refresh();
                } catch (Exception e) {
                    alertErrore(e.getMessage());
                }
            }
        });
    }
    
    /**
     * @brief Helper per convertire la stringa autori in Lista di oggetti Autore.
     * Separatore virgola. Ultima parola considerata come cognome.
     *
     * @param str La stringa di input (es. "Nome1 Cognome1, Nome2 Cognome2").
     * @return ArrayList<Autore> parsata.
     */
    private ArrayList<Autore> parseAutori(String str) {
        ArrayList<Autore> listaAutori = new ArrayList<>();
        
        if (str == null || str.trim().isEmpty()) {
            return listaAutori;
        }

        String[] stringheAutori = str.split(",");

        for (String s : stringheAutori) {
            s = s.trim();
            if (!s.isEmpty()) {
                int ultimoSpazio = s.lastIndexOf(" ");
                String nome, cognome;

                if (ultimoSpazio == -1) {
                    nome = s;
                    cognome = ""; 
                } else {
                    nome = s.substring(0, ultimoSpazio);
                    cognome = s.substring(ultimoSpazio + 1);
                }
                listaAutori.add(new Autore(nome, cognome));
            }
        }
        return listaAutori;
    }

    /**
     * @brief Visualizza un Alert di errore all'utente.
     *
     * @param msg Il messaggio di errore da mostrare.
     */
    public void alertErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Operazione fallita");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}