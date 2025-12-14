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
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Optional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
// AGGIUNTA IMPORT NECESSARIA PER I BINDING
import javafx.scene.layout.BorderPane; 

public class MainController {
    
    // --- Riferimento al Model ---
    private Biblioteca biblioteca;
    
    // --- ELEMENTI GRAFICI (Iniettati da FXML) ---

    // Pannelli principali
    @FXML private AnchorPane paneLibri;
    @FXML private AnchorPane paneUtenti;
    @FXML private AnchorPane panePrestiti;

    // Bottoni Sidebar (Devono essere aggiunti se non ci sono)
    @FXML private Button btnLibri;
    @FXML private Button btnUtenti;
    @FXML private Button btnPrestiti;

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
    
    // Riferimento al contenitore principale (BorderPane)
    // Non è necessario un fx:id se lo usiamo per i binding del pane centrale.

    
    /**
     * @brief Configura i binding per l'adattabilità (responsiveness) dell'interfaccia.
     */
    public void setupController() {

        // --- 1. CONFIGURAZIONE TABELLE (Politica di ridimensionamento) ---
        // IMPEDISCE ALL'UTENTE DI RIDIMENSIONARE LE COLONNE E FORZA L'ADATTAMENTO ALLA LARGHEZZA
        if (tableLibri != null) {
            tableLibri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        if (tableUtenti != null) {
            tableUtenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        if (tablePrestiti != null) {
            tablePrestiti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        // --- 2. BINDING PER L'ADATTAMENTO DEI PANNELLI CENTRALI ---
        // Se il tuo file FXML ha il BorderPane come root e gli AnchorPane dei dati 
        // sono contenuti in un unico StackPane (che è la configurazione standard),
        // devi vincolare gli AnchorPane a questo StackPane.
        
        // Poiché gli AnchorPane dei dati (paneLibri, paneUtenti, panePrestiti) sono
        // anch'essi i contenitori RADICE per la loro vista specifica, devono essere
        // vincolati al loro genitore (lo StackPane) per occupare tutto lo spazio.
        
        // BINDING PANELLI DATI (Larghezza e Altezza)
        // Ipotizzando che il genitore diretto di questi AnchorPane sia uno StackPane che
        // si adatta al centro del BorderPane, questo garantisce l'espansione.
        
        if (paneLibri != null && paneLibri.getParent() != null) {
            Pane parent = (Pane) paneLibri.getParent();
            
            // Larghezza: Vincola la larghezza dei pannelli alla larghezza del loro genitore
            paneLibri.prefWidthProperty().bind(parent.widthProperty());
            paneUtenti.prefWidthProperty().bind(parent.widthProperty());
            panePrestiti.prefWidthProperty().bind(parent.widthProperty());

            // Altezza: Vincola l'altezza dei pannelli all'altezza del loro genitore
            paneLibri.prefHeightProperty().bind(parent.heightProperty());
            paneUtenti.prefHeightProperty().bind(parent.heightProperty());
            panePrestiti.prefHeightProperty().bind(parent.heightProperty());
        }


        // --- 3. BINDING COMPONENTI INTERNI (Ricerca e Tabelle) ---
        // La TableView e il campo di ricerca devono espandersi all'interno dei loro AnchorPane contenitori.
        
        // A. TableView: Vincolata agli angoli dell'AnchorPane
        // Nel tuo FXML, le TableView sono già ancorate (bottom, left, right, top).
        // Se si utilizza un layout VBox/AnchorPane come genitore della TableView, 
        // possiamo assicurarci che si adattino alla larghezza del pannello. 
        // Visto che l'FXML usa AnchorPane come contenitore della TableView con 
        // AnchorPane.setBottom, ecc., non servono binding espliciti qui, ma li 
        // aggiungiamo per sicurezza sulla larghezza.
        
        if (tableLibri != null) {
            tableLibri.prefWidthProperty().bind(paneLibri.widthProperty());
        }
        if (tableUtenti != null) {
            tableUtenti.prefWidthProperty().bind(paneUtenti.widthProperty());
        }
        if (tablePrestiti != null) {
            tablePrestiti.prefWidthProperty().bind(panePrestiti.widthProperty());
        }
        
        // B. Campo di Ricerca Libri (per espandere il TextField)
        // Questo binding espanderà il TextField di ricerca in base alla larghezza del pannello Libri.
        if (searchLibri != null && paneLibri != null) {
             // Sottrai la larghezza fissa degli altri elementi (Label, Button, Margini, ecc.)
             // Questo è un calcolo approssimativo e può richiedere fine-tuning con l'FXML
             // Larghezza Totale PaneLibri - (Larghezza Label + Larghezza Button + Spazi Fissi)
             // Nel tuo FXML (HBox): Cerca(54) + 2 Regioni(25+48) + Img(39) + Button(143) + Margini(15*4) = ~ 370px
             searchLibri.prefWidthProperty().bind(paneLibri.widthProperty().subtract(370));
        }
        
         // C. Campo di Ricerca Utenti (per espandere il TextField)
         if (searchUtenti != null && paneUtenti != null) {
             // Larghezza Totale PaneUtenti - (Larghezza Label + Button + Spazi Fissi)
             // Nel tuo FXML (HBox): Cerca(83) + HBox Img(53) + 2 Regioni(81) + Button(158) + Margini(15*4) = ~ 435px
             searchUtenti.prefWidthProperty().bind(paneUtenti.widthProperty().subtract(435));
        }

    }
    
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

        // Applica i binding e la logica di ridimensionamento (CHIAMATA AGGIUNTA/RIPOSIZIONATA)
        setupController();

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
        
        mostraLibri();
    }

    /**
     * @brief Configura la TableView e le colonne per la visualizzazione dei Libri.
     *
     * Collega la lista filtrata dei Libri (flLibreria) alla TableView.
     */
    public void configuraLibri() {
        if (colLibTitolo != null) colLibTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitolo()));
        if (colLibAutori != null) colLibAutori.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().autoriToString())); 
        if (colLibAnno != null) colLibAnno.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getAnno()));
        if (colLibIsbn != null) colLibIsbn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getISBN()));
        // Mostra copie disponibili / totali
        if (colLibCopie != null) colLibCopie.setCellValueFactory(d -> new SimpleStringProperty(
            d.getValue().getNumCopieDisponibili() + "/" + d.getValue().getNumCopieTotali()));

        // Colonna Azioni
        if (colLibAzioni != null) colLibAzioni.setCellFactory(param -> new TableCell<Libro, Void>() {
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
        if (colUtNome != null) colUtNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        if (colUtCognome != null) colUtCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCognome()));
        if (colUtMatr != null) colUtMatr.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricola()));
        if (colUtPrestiti != null) colUtPrestiti.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getNumPrestitiAttivi()));

        if (colUtAzioni != null) colUtAzioni.setCellFactory(param -> new TableCell<Utente, Void>() {
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
        if (colPresUtente != null) colPresUtente.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUtente().getCognome()));
        if (colPresLibro != null) colPresLibro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getLibro().getTitolo()));
        // Colonna scadenza
        if (colPresData != null) colPresData.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDataRestituzione().toString()));

        // Configurazione stato (Verde = OK, Rosso = Ritardo)
        if (colPresStato != null) colPresStato.setCellFactory(c -> new TableCell<Prestito, String>() {
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
        if (colPresAzioni != null) colPresAzioni.setCellFactory(p -> new TableCell<Prestito, Void>() {
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
    // Questi metodi ora sono chiamati dai bottoni "+ Nuovo" nell'FXML

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
        g.addRow(1, new Label("Autori (separati da ,):"), tAutori);
        g.addRow(2, new Label("Anno:"), tAnno);
        g.addRow(3, new Label("ISBN:"), tIsbn);
        g.addRow(4, new Label("Numero Copie:"), tCopie);
        
        d.getDialogPane().setContent(g);
        
        Optional<ButtonType> result = d.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Logica per creare e aggiungere il libro
            try {
                // Esempio: gestisci l'input dell'anno e delle copie come numeri
                int anno = Integer.parseInt(tAnno.getText());
                int copie = Integer.parseInt(tCopie.getText());
                //List<String> autori = Arrays.asList(tAutori.getText().split(","));
                
                //Libro nuovoLibro = new Libro(tTitolo.getText(), (List<Autore>) autori, anno, tIsbn.getText(), copie);
                // Ho modificato l'uso di List.of per Autori, assumendo che tu abbia un costruttore o metodo 
                // in Biblioteca che gestisca la conversione da List<String> a List<Autore> se necessario.
                // Usando il tuo codice fornito:
                List<Autore> newautori = parseAutori(tAutori.getText());
                biblioteca.aggiungiLibro(
                    tTitolo.getText(), 
                    newautori, 
                    anno, 
                    tIsbn.getText(), 
                    copie, 
                    copie
                );
                
                tableLibri.refresh();
            } catch (NumberFormatException e) {
                alertErrore("Anno e Copie devono essere numeri validi.");
            } catch (Exception e) {
                 alertErrore("Errore durante l'aggiunta del libro: " + e.getMessage());
            }
        }
    }

    /**
     * @brief Avvia il flusso per l'aggiunta di un nuovo Utente.
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
                     tableUtenti.refresh();
                 } catch (Exception e) {
                     alertErrore("Errore inserimento: " + e.getMessage());
                 }
             }
         });
    }

    /**
     * @brief Avvia il flusso per la creazione di un nuovo Prestito.
     */
    @FXML
    public void nuovoPrestito() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Nuovo Prestito");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        
        // Assicurati che i metodi getObClienti() e getObLibreria() restituiscano ObservableList<T>
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
                    tablePrestiti.refresh();
                    tableLibri.refresh(); // Aggiorna il conteggio copie
                    tableUtenti.refresh(); // Aggiorna il conteggio prestiti
                } catch (Exception e) {
                    alertErrore(e.getMessage());
                }
            }
        });
    }

    // --- Metodi Helper (per le azioni delle tabelle) ---

    private void modificaLibro(Libro l) {
         // Implementazione di modificaLibro
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
    
    private void modificaUtente(Utente u) {
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
         g.addRow(2, new Label("Matricola:"), tMatricola);
         g.addRow(3, new Label("Email:"), tEmail);
         
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
                    nome = s.substring(0, ultimoSpazio).trim();
                    cognome = s.substring(ultimoSpazio + 1).trim();
                }
                listaAutori.add(new Autore(nome, cognome));
            }
        }
        return listaAutori;
    }

    private void alertErrore(String messaggio) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Errore di Sistema");
        a.setHeaderText(null);
        a.setContentText(messaggio);
        a.showAndWait();
    }
    
     private void alertInfo(String messaggio) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informazione");
        a.setHeaderText(null);
        a.setContentText(messaggio);
        a.showAndWait();
    }
}