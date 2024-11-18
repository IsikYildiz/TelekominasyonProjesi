package bonusesScreen;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BonusesScreenController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Bonuses, String> bonus;

    @FXML
    private TableColumn<Bonuses, Date> bonusDate;

    @FXML
    private TableColumn<Bonuses, String> bonusMax;

    @FXML
    private TableColumn<Bonuses, String> bonusMin;

    @FXML
    private TableColumn<Bonuses, String> bonusSituation;

    @FXML
    private TableView<Bonuses> bonuses;

    @FXML
    private Button protestButton;
    
    @FXML
    private TextField protestExplanation;
    
    @FXML
    private Label error;
    
    @SuppressWarnings("unchecked")
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {//Sayfa açıldığında Asistanın primlerini tabloda gösterir.
    	Connection con=Data.connect();
    	bonusDate.setCellValueFactory(new PropertyValueFactory<Bonuses,Date>("bonusDate"));
    	bonusMin.setCellValueFactory(new PropertyValueFactory<Bonuses,String>("bonusMin"));
    	bonusSituation.setCellValueFactory(new PropertyValueFactory<Bonuses,String>("bonusSituation"));
    	bonus.setCellValueFactory(new PropertyValueFactory<Bonuses,String>("bonus"));
    	bonusMax.setCellValueFactory(new PropertyValueFactory<Bonuses,String>("bonusMax"));
		ObservableList<Bonuses> bonusesList = FXCollections.observableArrayList(Data.getBonusList(con));
    	bonuses.setItems(bonusesList);
    	con.close();
    }
    
    @FXML
    void protest(ActionEvent event) throws ClassNotFoundException, SQLException {//Listede en son eklenen prime itiraz eder.
    	if(!protestExplanation.getText().equals("")){
    		Connection con=Data.connect();
    		if(Data.addAsistanProtest(con, protestExplanation, bonusDate.getCellObservableValue(0).getValue())) {
    			error.setText("Başarılı!");
    			error.setVisible(true);
    			con.close();
    		}
    		else {
    			error.setText("Bu prime daha önce itiraz ettiniz.");
    			error.setVisible(true);
    		}
    	}
    	else {
    		error.setVisible(true);
    		error.setText("Lütfen itiraz açıklamasını giriniz.");
    	}
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
