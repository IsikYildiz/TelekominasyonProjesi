package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {//Programı başlatır.

    @Override
    public void start(Stage stage) throws IOException { //Uygulama başlatıldığında ilk ekranı açar.
        FXMLLoader firstScreenLoader = new FXMLLoader(getClass().getResource("/loginScreen/loginscreen.fxml"));
        Scene scene = new Scene(firstScreenLoader.load());
        stage.setTitle("Telekominasyon Uygulaması");
        Image icon=new Image("/Ui/ikon.png");
        stage.setFullScreenExitHint("Tam Ekrandan Çıkmak İçin ESC Tuşuna Basınız");
        stage.setFullScreen(true);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }	
}