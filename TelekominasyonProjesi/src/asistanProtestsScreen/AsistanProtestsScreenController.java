package asistanProtestsScreen;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AsistanProtestsScreenController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<AsistanProtests, String> protestAnswer;

    @FXML
    private TableColumn<AsistanProtests, Date> protestDate;

    @FXML
    private TableColumn<AsistanProtests, String> protestExplanation;

    @FXML
    private TableColumn<AsistanProtests, String> protestSituation;

    @FXML
    private TableView<AsistanProtests> protestTable;
    
    @SuppressWarnings("unchecked")
	@FXML
    void initialize() throws SQLException, ClassNotFoundException {//Ekran açılırken asistanın itirazlarını tabloya girer.
    	Connection con=Data.connect();
    	protestDate.setCellValueFactory(new PropertyValueFactory<AsistanProtests,Date>("protestDate"));
    	protestExplanation.setCellValueFactory(new PropertyValueFactory<AsistanProtests,String>("protestExplanation"));
    	protestSituation.setCellValueFactory(new PropertyValueFactory<AsistanProtests,String>("protestSituation"));
    	protestAnswer.setCellValueFactory(new PropertyValueFactory<AsistanProtests,String>("protestAnswer"));
		ObservableList<AsistanProtests> protestsList = FXCollections.observableArrayList(Data.getAsistanProtestsList(con));
    	protestTable.setItems(protestsList);
    	con.close();
    }

    @FXML
    void goBack(ActionEvent event) {//Asistan menüsüne geri döner.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/asistanMenu/asistanscreen.fxml"));
            backButton.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Başarısız");
        }
    }

}
