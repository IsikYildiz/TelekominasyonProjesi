package addCallScreen;

import java.sql.Connection;
import java.sql.SQLException;
import data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class addCallScreenController {//Asistan prim eklemek istediğinde açılan sayfayı kontrol eder.

	@FXML
	private Button addCallButton;

	@FXML
	private Button backButton;

	@FXML
	private TextField callDate;

	@FXML
	private TextField callEndTime;

	@FXML
	private ToggleGroup callSituation;

	@FXML
	private TextField callStartTime;
	 
	@FXML
	private ToggleGroup callSubject;

	@FXML
	private TextField customerName;

	@FXML
	private Label error;
	    
	@FXML
	private ToggleGroup customerType;
	
	@FXML
	void initialize(){
		callDate.setText(LocalDate.now().toString());
	}
	
    @FXML
    void addCall(ActionEvent event) throws ClassNotFoundException, SQLException { //Asistan gerekli bilgileri girdikten sonra, asistanın adına yeni bir çağrı eklenir.
    	Connection con=Data.connect();
    	try {
    		if(!customerName.getText().equals("")) {
        		try {
        			LocalTime startTime = LocalTime.parse(callStartTime.getText());
            		LocalTime endTime = LocalTime.parse(callEndTime.getText());
            		Data.addCall(con, LocalDate.now(), customerName, callSubject, startTime, endTime, callSituation,customerType);
            		try {
                        Parent root = FXMLLoader.load(getClass().getResource("/callsScreen/callsscreen.fxml"));
                        backButton.getScene().setRoot(root);
                    } catch (Exception e) {
                        System.out.println("Başarısız");
                    }
            		con.close();
        		}catch (DateTimeParseException e) {
        			error.setText("Girilen Tarih veya Zamanlar Yanlıştır.");
        			error.setVisible(true);
        		}
        	}
        	else {
        		error.setText("Müşter ismi yoktur.");
        		error.setVisible(true);
        	}
    	} catch (NullPointerException e) {
    		error.setText("Lütfen Görüşmenin Konusunu ve Durumunu Seçtiğinizden Emin Olunuz.");
    		error.setVisible(true);
    	}
    	con.close();
    }

    @FXML
    void goBack(ActionEvent event) {//Asistanın çağrılarım ekranına geri döner.
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/callsScreen/callsscreen.fxml"));
            backButton.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("Başarısız");
        }
    }

}
