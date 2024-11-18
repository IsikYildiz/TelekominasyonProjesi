package loginScreen;

import data.*;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginScreenController { //Giriş yapma ekranındaki işlemleri kontrol eder.
	private static String sifre;
	@FXML
	private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private ToggleGroup loginType;

    @FXML
    private TextField password;

    @FXML
    private Label error;

    @FXML
    void login(ActionEvent event) throws SQLException, ClassNotFoundException { //Şifreyi kontrol edilir ve doğruysa, kullanıcı ilgili ekrana yönlendirilir.
    	Connection con=Data.connect();
    	try {
    		if(!password.getText().equals("")){
        		RadioMenuItem radioButton = (RadioMenuItem) loginType.getSelectedToggle();
        		if(Data.comparePassword(con, password.getText(), radioButton)==1) {
        			setSifre(password.getText());
        			try {
                        Parent root = FXMLLoader.load(getClass().getResource("/asistanMenu/asistanscreen.fxml"));
                        loginButton.getScene().setRoot(root);
                    } catch (Exception e) {
                        System.out.println("Sayfa Yüklenemedi!");
                    }
        			con.close();
        		}
        		else if(Data.comparePassword(con, password.getText(), radioButton)==2) {
        			setSifre(password.getText());
        			try {
                        Parent root = FXMLLoader.load(getClass().getResource("/takimLiderMenu/takimliderscreen.fxml"));
                        loginButton.getScene().setRoot(root);
                    } catch (Exception e) {
                        System.out.println("Sayfa Yüklenemedi!");
                    }
        			con.close();
        		}
        		else
        			error.setText("Girdiğiniz Şifre Hatalıdır.");
        			error.setVisible(true);
        	}
        	else {
        		error.setText("Lütfen Şifreyi Giriniz.");
        		error.setVisible(true);
        	}
    	} catch(NullPointerException e) {
    		error.setText("Lütfen giriş tipini seçiniz.");
    		error.setVisible(true);
    	}
    	con.close();
    }

    @FXML
    void exitApp(ActionEvent event) {//Sistemden çıkış yapar.
    	System.exit(0);
    }

	public static String getSifre() {//Uygulamayı o an kullanan kişinin şifresini verir.
		return sifre;
	}

	public static void setSifre(String sifre) {//Uygulamayı o an kullanan kişinin şifresini kaydeder.
		LoginScreenController.sifre = sifre;
	}
}
