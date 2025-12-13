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

public class Main extends Application{
    
    /**
     * @brief Metodo principale per l'avvio dell'applicazione JavaFX.
     *
     * Questo metodo viene chiamato dal framework JavaFX dopo l'inizializzazione.
     * Imposta la scena e mostra la finestra (Stage).
     *
     * @param primaryStage Lo stage (finestra) principale dell'applicazione.
     * @throws Exception Se si verifica un errore durante il caricamento dell'FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Accesso.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Gestione Biblioteca");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    /**
     * @brief Metodo statico main.
     *
     * Metodo standard per l'applicazione Java.
     *
     * @param args Array di argomenti passati dalla linea di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
