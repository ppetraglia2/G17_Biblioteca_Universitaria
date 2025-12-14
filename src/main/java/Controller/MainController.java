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
import javafx.scene.layout.BorderPane; 

public class MainController {
    
    // --- Riferimento al Model ---
    private Biblioteca biblioteca;
    
    // --- ELEMENTI GRAFICI ---
    // Pannelli principali
    @FXML private AnchorPane paneLibri;
    @FXML private AnchorPane paneUtenti;
    @FXML private AnchorPane panePrestiti;

    // Bottoni Sidebar 
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

    
    /**
     * @brief Configura i binding per l'adattabilità (responsiveness) dell'interfaccia.
     */
    public void setupController() {

        if (tableLibri != null) {
            tableLibri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        if (tableUtenti != null) {
            tableUtenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        if (tablePrestiti != null) {
            tablePrestiti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        
        if (paneLibri != null && paneLibri.getParent() != null) {
            Pane parent = (Pane) paneLibri.getParent();
            
            paneLibri.prefWidthProperty().bind(parent.widthProperty());
            paneUtenti.prefWidthProperty().bind(parent.widthProperty());
            panePrestiti.prefWidthProperty().bind(parent.widthProperty());

            paneLibri.prefHeightProperty().bind(parent.heightProperty());
            paneUtenti.prefHeightProperty().bind(parent.heightProperty());
            panePrestiti.prefHeightProperty().bind(parent.heightProperty());
        }

        if (tableLibri != null) {
            tableLibri.prefWidthProperty().bind(paneLibri.widthProperty());
        }
        if (tableUtenti != null) {
            tableUtenti.prefWidthProperty().bind(paneUtenti.widthProperty());
        }
        if (tablePrestiti != null) {
            tablePrestiti.prefWidthProperty().bind(panePrestiti.widthProperty());
        }
        


        if (searchLibri != null && paneLibri != null) {
             // Sottrazione della larghezza fissa degli altri elementi (Label, Button, Margini, ecc.)
             // Larghezza Totale PaneLibri - (Larghezza Label + Larghezza Button + Spazi Fissi)
             //(HBox): Cerca(54) + 2 Regioni(25+48) + Img(39) + Button(143) + Margini(15*4) = 370px
             searchLibri.prefWidthProperty().bind(paneLibri.widthProperty().subtract(370));
        }
        
        if (searchUtenti != null && paneUtenti != null) {
             // Larghezza Totale PaneUtenti - (Larghezza Label + Button + Spazi Fissi)
             //(HBox): Cerca(83) + HBox Img(53) + 2 Regioni(81) + Button(158) + Margini(15*4) = 435px
             searchUtenti.prefWidthProperty().bind(paneUtenti.widthProperty().subtract(435));
        }
        
        if (tableLibri != null && paneLibri != null) {
        
            javafx.beans.property.ReadOnlyDoubleProperty containerWidth = paneLibri.widthProperty();
            javafx.beans.binding.DoubleBinding dynamicFontSize = containerWidth.divide(75);
            
            tableLibri.styleProperty().bind(
                dynamicFontSize.asString("-fx-font-size: 18px;")
            );  
        }
        
        if (tableUtenti != null && paneUtenti != null) {
        
            javafx.beans.property.ReadOnlyDoubleProperty containerWidth = paneUtenti.widthProperty();
            javafx.beans.binding.DoubleBinding dynamicFontSize = containerWidth.divide(75);
            
            tableUtenti.styleProperty().bind(
                dynamicFontSize.asString("-fx-font-size: 18px;")
            );  
        }
        
        if (tablePrestiti != null && panePrestiti != null) {
        
            javafx.beans.property.ReadOnlyDoubleProperty containerWidth = panePrestiti.widthProperty();
            javafx.beans.binding.DoubleBinding dynamicFontSize = containerWidth.divide(75);
            
            tablePrestiti.styleProperty().bind(
                dynamicFontSize.asString("-fx-font-size: 18px;")
            );  
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

        biblioteca = new Biblioteca();
        
        configuraLibri();
        configuraUtenti();
        configuraPrestiti();

        setupController();

        if (biblioteca.getFlLibreria() != null)
            tableLibri.setItems(biblioteca.getFlLibreria());
        
        if (biblioteca.getFlClienti() != null)
            tableUtenti.setItems(biblioteca.getFlClienti());
            
        if (biblioteca.getObPrestiti() != null)
            tablePrestiti.setItems(biblioteca.getObPrestiti());


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

        if (colLibCopie != null) colLibCopie.setCellValueFactory(d -> new SimpleStringProperty(
            d.getValue().getNumCopieDisponibili() + "/" + d.getValue().getNumCopieTotali()));


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
                    
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Conferma Eliminazione");
                    a.setHeaderText("Eliminazione Libro");
                    a.setContentText("Sei sicuro di voler eliminare il libro: " + l.getTitolo() + "?");
                    
                    Optional<ButtonType> result = a.showAndWait();
                   
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            biblioteca.eliminaLibro(l);
                        } catch (Exception ex) {
                            alertErrore(ex.getMessage());
                        }
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
                    
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Conferma Eliminazione");
                    a.setHeaderText("Eliminazione Utente");
                    a.setContentText("Sei sicuro di voler eliminare l'utente: " + u.getNome() +" "+ u.getCognome()+ "?");
                    
                    Optional<ButtonType> result = a.showAndWait();
                    
                    if(result.isPresent() && result.get() == ButtonType.OK){
                        try {
                            biblioteca.eliminaUtente(u);
                        } catch (Exception ex) {
                            alertErrore(ex.getMessage());
                        }
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

        if (tablePrestiti != null) {
            tablePrestiti.setRowFactory(tv -> new TableRow<Prestito>() {
                @Override
                protected void updateItem(Prestito prestito, boolean empty) {
                    super.updateItem(prestito, empty);

                    if (prestito == null || empty)
                        setStyle("");
                    else {
                        if (prestito.controllaRitardo())
                            setStyle("-fx-background-color: #ffcccc;"); 
                        else
                            setStyle(""); 
                    }
                }
            });
        }
        
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
                    setTextFill(ritardo ? Color.RED: Color.GREEN);
                }
            }
        });

        if (colPresAzioni != null) colPresAzioni.setCellFactory(p -> new TableCell<Prestito, Void>() {
            private final Button btnRet = new Button("Rientro");
            {
                btnRet.setOnAction(e -> {
                    Prestito prestito = getTableView().getItems().get(getIndex());
                    
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Conferma Restituzione");
                    a.setHeaderText("Restituzione Libro");
                    a.setContentText("Sei sicuro di voler registrare la restituzione del libro : " + prestito.getLibro().getTitolo() + "?");
                    
                    Optional<ButtonType> result = a.showAndWait();
                    
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            biblioteca.restituisciPrestito(prestito);

                            tablePrestiti.refresh();
                            tableLibri.refresh();
                            tableUtenti.refresh();
                        } catch (Exception ex) {
                            alertErrore(ex.getMessage());
                        }
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
        d.getDialogPane().setStyle("-fx-font-size: 15px;");
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        g.setStyle("-fx-alignment: center;");
        
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
            try {
                int anno = Integer.parseInt(tAnno.getText());
                int copie = Integer.parseInt(tCopie.getText());
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
         d.getDialogPane().setStyle("-fx-font-size: 15px;");
         
         GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
         g.setStyle("-fx-alignment: center;");
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
        d.getDialogPane().setStyle("-fx-font-size: 15px;");
        
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        g.setStyle("-fx-alignment: center;");
        
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
                    tableLibri.refresh();
                    tableUtenti.refresh();
                } catch (Exception e) {
                    alertErrore(e.getMessage());
                }
            }
        });
    }

    // --- Metodi Helper (per le azioni delle tabelle) ---

    private void modificaLibro(Libro l) {
         if (l == null) return;

         Dialog<ButtonType> d = new Dialog<>();
         d.setTitle("Modifica Libro");
         d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
         
         GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
         g.setStyle("-fx-alignment: center;");
         
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
                     int newcopieTot = Integer.parseInt(tCopie.getText().trim());
                     int newcopieDisp = l.getNumCopieDisponibili() - (l.getNumCopieTotali() - newcopieTot);
                     List<Autore> newautori = parseAutori(tAutori.getText());
                     
                     biblioteca.modificaLibro(l, tTitolo.getText(), newautori, newanno, tIsbn.getText(), newcopieTot, newcopieDisp);
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
         g.setStyle("-fx-alignment: center;");
         
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
