package takimLideriProtestsScreen;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TakimLideriProtestsScreenController {

    @FXML
    private TableColumn<TakimLiderProtests, String> asistan;

    @FXML
    private TableColumn<TakimLiderProtests, String> asistanNo;

    @FXML
    private TableColumn<TakimLiderProtests, String> protestAnswer;

    @FXML
    private TableColumn<TakimLiderProtests, Date> protestDate;

    @FXML
    private TableColumn<TakimLiderProtests, String> protestSituation;
    
    @FXML
    private TableColumn<TakimLiderProtests, String> protestExplanation;
    
    @FXML
    private TableView<TakimLiderProtests> protestTable;
    
    @FXML
    private Label text;
    
    @FXML
    private Button acceptButton;
    
    @FXML
    private Button declineButton;
    
    @FXML
    private Button backButton;

	
    @SuppressWarnings("unchecked")
	@FXML
	void initialize() throws ClassNotFoundException, SQLException{//Takım liderine gelen itirazları tabloya girer.
    	text.setText("Cevabınızı girdikten sonra, o satırı seçip onayla veya reddete basınız.");
    	text.setVisible(true);
    	Connection con=Data.connect();
    	protestDate.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,Date>("protestDate"));
    	asistanNo.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,String>("asistanNo"));
    	asistan.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,String>("asistan"));
    	protestExplanation.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,String>("protestExplanation"));
    	protestSituation.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,String>("protestSituation"));
    	protestAnswer.setCellFactory(TextFieldTableCell.<TakimLiderProtests>forTableColumn());
    	protestAnswer.setCellValueFactory(new PropertyValueFactory<TakimLiderProtests,String>("protestAnswer"));
		ObservableList<TakimLiderProtests> protestsList = FXCollections.observableArrayList(Data.getTakimLideriProtestsList(con));
		protestTable.setItems(protestsList);
		con.close();
    }
 
    @FXML
    void accept(ActionEvent event) {//Seçilen satırdaki itirazı günceller.
    	Connection con = null;
		try {
			con = Data.connect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int row=protestTable.getSelectionModel().getFocusedIndex();
		if(!protestAnswer.getCellObservableValue(row).equals("")) {
			try {
				int ıd = Integer.parseInt(asistanNo.getCellObservableValue(row).getValue());
				PreparedStatement updateItiraz=con.prepareStatement("update Itiraz set itirazCevabi='"+protestAnswer.getCellObservableValue(row).getValue()+"', itirazDurum='onaylandı' where sicilNo="+ıd);
				updateItiraz.execute();
				try {
		            Parent root = FXMLLoader.load(getClass().getResource("/takimLiderMenu/takimliderscreen.fxml"));
		            acceptButton.getScene().setRoot(root);
		        } catch (Exception e) {
		            System.out.println("Sayfa Yüklenemedi!");
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }

    @FXML
    void decline(ActionEvent event) {//Seçilen satırdaki itirazı günceller.
    	Connection con = null;
		try {
			con = Data.connect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	int row=protestTable.getSelectionModel().getSelectedIndex();
		if(!protestAnswer.getCellObservableValue(row).equals("")) {
			try {
				int ıd = Integer.parseInt(asistanNo.getCellObservableValue(row).getValue());
				PreparedStatement updateItiraz=con.prepareStatement("update Itiraz set itirazCevabi='"+protestAnswer.getCellObservableValue(row).getValue()+"', itirazDurum='reddedildi' where sicilNo="+ıd);
				updateItiraz.executeUpdate();
				try {
		            Parent root = FXMLLoader.load(getClass().getResource("/takimLiderMenu/takimliderscreen.fxml"));
		            declineButton.getScene().setRoot(root);
		        } catch (Exception e) {
		            System.out.println("Sayfa Yüklenemedi!");
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
    
    @FXML
    void edit(TableColumn.CellEditEvent<TakimLiderProtests, String> a) {//Tablodaki itiraz cevabının düzenlenmesini sağlar.
    	TakimLiderProtests answer= protestTable.getSelectionModel().getSelectedItem();
    	answer.setProtestAnswer(a.getNewValue());
    }
    
    @FXML
    void back(ActionEvent event) { //Takım lideri menüsüne geri döner.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/takimLiderMenu/takimliderscreen.fxml"));
            backButton.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Başarısız");
        }
    }
}
