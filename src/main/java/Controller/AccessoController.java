/**
 * @file AccessoController.java
 * @brief Controller che gestisce la schermata di accesso al software.
 *
 * Questa classe gestisce la transizione dalla schermata di accesso alla schermata principale dell'applicazione
 * e implementa il ridimensionamento (scaling) degli elementi UI tramite Binding.
 * È associata al file FXML accesso.fxml.
 */
package Controller;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class AccessoController {

    @FXML
    private Button btnEntra;

    // Elementi aggiunti per il ridimensionamento
    @FXML
    private ImageView logoImage;
    
    @FXML
    private Label labelBenvenuto;
    @FXML
    private Separator separatorBenvenuto;

    /**
     * @brief Metodo per l'inizializzazione del Controller.
     * * Qui eseguiamo i Binding per adattare le dimensioni di Logo e Testo alla finestra.
     */
    public void setupController() {
        
        logoImage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                logoImage.fitHeightProperty().bind(Bindings.min(newScene.widthProperty(), newScene.heightProperty()).divide(3.0));
                
                labelBenvenuto.fontProperty().bind(Bindings.createObjectBinding(() -> 
                    Font.font("Roboto", newScene.widthProperty().doubleValue() / 60.0),
                    newScene.widthProperty()));
                
                btnEntra.fontProperty().bind(Bindings.createObjectBinding(()->
                   javafx.scene.text.Font.font(
                        "System",
                        javafx.scene.text.FontWeight.BOLD, 
                        newScene.widthProperty().doubleValue() / 70.0 
                    ),
                    newScene.widthProperty()));
                
                labelBenvenuto.widthProperty().addListener((w_obs, oldWidth, newWidth) -> {
                    separatorBenvenuto.setMaxWidth(newWidth.doubleValue()); 
                });
            }
        });
    }

    /**
     * @brief Gestisce l'evento di click sul pulsante "ENTRA NEL SISTEMA".
     * Avvia la schermata principale dell'applicazione.
     * * @param event L'evento di azione (click del mouse).
     */
    @FXML
    private void handleEntraNelSistema(ActionEvent event) {
        try {
             String mainViewPath = "/View/main.fxml"; 
             Parent mainView = FXMLLoader.load(getClass().getResource(mainViewPath));
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(mainView, stage.getWidth(), stage.getHeight()); // Mantiene le dimensioni attuali

             stage.setScene(scene);
             stage.setTitle("Sistema di Gestione Biblioteca Universitaria");
             stage.show();
             
        } catch (IOException e) {
             e.printStackTrace();
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Errore di Caricamento");
             alert.setHeaderText("Impossibile caricare la schermata principale.");
             alert.setContentText("Dettagli: " + e.getMessage());
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