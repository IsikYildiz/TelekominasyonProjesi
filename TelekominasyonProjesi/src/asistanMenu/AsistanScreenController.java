package asistanMenu;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class AsistanScreenController { //Asistan menüsünü kontrol eder.
	  @FXML
	    private Button bonusesButton;

	    @FXML
	    private Button callsButton;

	    @FXML
	    private Button exitButton;

	    @FXML
	    private Button protestsButton;

	    @FXML
	    void listBonuses(ActionEvent event) {//Asistana ait primleri listeler.
	    	try {
                Parent root = FXMLLoader.load(getClass().getResource("/bonusesScreen/bonusesscreen.fxml"));
                bonusesButton.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Sayfa Yüklenemedi!");
            }
	    }

	    @FXML
	    void listCalls(ActionEvent event) throws SQLException, ClassNotFoundException {//Asistanın çağrılarını listeler.
	    	try {
                Parent root = FXMLLoader.load(getClass().getResource("/callsScreen/callsscreen.fxml"));
                callsButton.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Sayfa Yüklenemedi!");
            }
	    }

	    @FXML
	    void listProtests(ActionEvent event) {//Asistanın itirazlarını listeler.
	    	try {
                Parent root = FXMLLoader.load(getClass().getResource("/asistanProtestsScreen/asistanprotestsscreen.fxml"));
                protestsButton.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println("Sayfa Yüklenemedi!");
            }
	    }
	    
	    @FXML
	    void exitApp(ActionEvent event) {//Uygulamadan çıkar.
	    	System.exit(1);
	    }
}
