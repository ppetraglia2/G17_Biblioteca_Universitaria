/**
 * @file AccessoController.java
 * @brief Controller che gestisce la schermata di accesso al software.
 *
 * Questa classe gestisce la transizione dalla schermata di accesso alla schermata principale dell'applicazione.
 * L'utente può cliccare sul Button per accedere alla HomePage, evento che viene gestito da questo controller 
 * È associata al file FXML accesso.fxml.
 */
package Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class AccessoController {

    @FXML
    private Button btnEntra;

    /**
     * @brief Metodo per l'inizializzazione del Controller che sostituisce initialize().
     * 
     */
    public void setupController() {
        // Nessuna azione specifica necessaria per questa vista semplice.
    }

    /**
     * @brief Gestisce l'evento di click sul pulsante "ENTRA NEL SISTEMA".
     * Avvia la schermata principale dell'applicazione.
     * 
     * @param event L'evento di azione (click del mouse).
     */
    @FXML
    private void handleEntraNelSistema(ActionEvent event) {
        try {
            String mainViewPath = "/View/MainView.fxml"; 
            
            // 1. Carica il file FXML della vista principale
            Parent mainView = FXMLLoader.load(getClass().getResource(mainViewPath));
            
            // 2. Ottieni lo Stage (finestra) corrente dal pulsante cliccato
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 3. Imposta la nuova Scene sullo Stage
            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.setTitle("Sistema di Gestione Biblioteca Universitaria");
            stage.show();
            
        } catch (IOException e) {
            // Gestione di errori nel caricamento dell'FXML
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di Caricamento");
            alert.setHeaderText("Impossibile caricare la schermata principale.");
            alert.setContentText("Verifica che il file MainView.fxml esista nel percorso: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
             e.printStackTrace();
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText("Errore Generico");
             alert.setContentText(e.getMessage());
             alert.showAndWait();
        }
    }
}