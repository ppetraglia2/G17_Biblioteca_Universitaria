/**
 * @file Main.java
 * @brief Classe di avvio dell'applicazione.
 *
 * La classe Main estende l'Application di JavaFX e si occupa di inizializzare
 * l'ambiente grafico e caricare il layout principale (View).
 */
package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.*;
import Controller.*;

public class Main extends Application {

    private Stage primaryStage; // Manteniamo un riferimento allo Stage principale

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        // 1. Avvia la schermata di Accesso
        mostraSchermataAccesso();
    }

    /**
     * Carica e visualizza la schermata di login.
     */
    public void mostraSchermataAccesso() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("accesso.fxml")
            );
            Parent root = loader.load();

            AccessoController controller = loader.getController();
            if (controller != null) {
                controller.setupController(); 
            }

            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Gestione dell'errore di caricamento FXML
        }
    }

    /**
     * Carica e visualizza la homepage, sostituendo la schermata di login.
     */
    public void mostraHomepage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("main.fxml")
            );
            Parent root = loader.load();

            // Opzionale: Ottieni il controller Main se devi passargli dati
            // MainController controller = loader.getController();
            // controller.setDatiUtente(utenteLoggato);

            // Cambia la Scena corrente dello Stage
            primaryStage.setTitle("Homepage Applicazione");
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            
        } catch (IOException e) {
            e.printStackTrace();
            // Gestione dell'errore di caricamento FXML
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}