package callsScreen;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CallsScreenController {

    @FXML
    private Button backButton;

    @FXML
    private Button callButton;

    @FXML
    private TableColumn<Calls, Date> callDate;

    @FXML
    private TableColumn<Calls, Time> callEndTime;

    @FXML
    private TableColumn<Calls, String> callSituation;

    @FXML
    private TableColumn<Calls, Time> callStartTime;

    @FXML
    private TableColumn<Calls, String> callSubject;

    @FXML
    private TableView<Calls> callTable;

    @FXML
    private TableColumn<Calls, String> customerName;
    
    @FXML
    private Button changeButton;
    
    @FXML
    private Label error;
    
    @SuppressWarnings("unchecked")
	@FXML 
    void initialize() throws ClassNotFoundException, SQLException {//Sayfa açıldığında tabloya asistanın çağrılarını girer.
    	Connection con=Data.connect();
    	callDate.setCellValueFactory(new PropertyValueFactory<Calls,Date>("callDate"));
    	customerName.setCellValueFactory(new PropertyValueFactory<Calls,String>("customerName"));
    	callSubject.setCellValueFactory(new PropertyValueFactory<Calls,String>("callSubject"));
    	callStartTime.setCellValueFactory(new PropertyValueFactory<Calls,Time>("callStartTime"));
    	callEndTime.setCellValueFactory(new PropertyValueFactory<Calls,Time>("callEndTime"));
    	callSituation.setCellFactory(TextFieldTableCell.forTableColumn());
    	callSituation.setCellValueFactory(new PropertyValueFactory<Calls,String>("callSituation"));
    	ObservableList<Calls> callsList = FXCollections.observableArrayList(Data.getCallList(con));
    	callTable.setItems(callsList);
    	con.close();
    }
    
    @FXML
    void changeStuation(ActionEvent event) throws SQLException {
    	Connection con=null;
		try {
			con = Data.connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int row=callTable.getSelectionModel().getSelectedIndex();
		if(!(row==-1)){
			String name=customerName.getCellObservableValue(row).getValue();
			String startTime=callStartTime.getCellObservableValue(row).getValue().toString();
			String situation=callSituation.getCellObservableValue(row).getValue().toString();
			Date date=callDate.getCellObservableValue(row).getValue();
			Data.changeCallStuation(con, date.toLocalDate(), startTime, name, situation);
			try {
	            Parent root = FXMLLoader.load(getClass().getResource("/asistanMenu/asistanscreen.fxml"));
	            backButton.getScene().setRoot(root);
	        } catch (Exception e) {
	            System.out.println("Sayfa Yüklenemedi!");
	        }
		}
		else {
			error.setText("Lütfen bir satır seçiniz");
			error.setVisible(true);
		}
		con.close();
    }
    
    @FXML
    void edit(TableColumn.CellEditEvent<Calls, String> a) {// Tablodaki çağrı durumunun değiştirilmesini sağlar.
    	Calls situation= callTable.getSelectionModel().getSelectedItem();
    	situation.setCallSituation(a.getNewValue());
    }
    
    @FXML
    void addCall(ActionEvent event) {//Asistanı çağrı ekleme ekranına yönlendirir.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/addCallScreen/addcallscreen.fxml"));
            callButton.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Başarısız");
        }
    }

    @FXML
    void goBack(ActionEvent event) {//Asistan menüye yönlendirilir.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/asistanMenu/asistanscreen.fxml"));
            backButton.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Başarısız");
        }
    }
    
}

