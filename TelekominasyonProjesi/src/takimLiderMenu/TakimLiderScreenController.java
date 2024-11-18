package takimLiderMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class TakimLiderScreenController {

    @FXML
    private Button openProtests;
    
    @FXML
    private Button exitButton;


    @FXML
    void openProtestsScreen(ActionEvent event) {//Takım liderine gelen itirazları gösterir.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/takimLideriProtestsScreen/takimlideriprotestsscreen.fxml"));
            openProtests.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Sayfa Yüklenemedi!");
        }
    }
    
    @FXML
    void exitApp(ActionEvent event) {
    	System.exit(1);
    }
}
