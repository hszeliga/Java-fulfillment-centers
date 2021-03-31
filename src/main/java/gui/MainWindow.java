package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.WriteToCSV;

import java.io.Serializable;
import java.util.Optional;

public class MainWindow extends Application implements Serializable {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("App");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Niezapisane dane");
                alert.setHeaderText("Czy zapisać wprowadzone dane do pliku CSV?");

                ButtonType save = new ButtonType("Tak");
                ButtonType dontsave = new ButtonType("Nie");
                ButtonType cancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(save, dontsave, cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == save){
                    WriteToCSV.writeProducts();
                    WriteToCSV.writeCart();
                    primaryStage.close();
                }
                else if (result.get() == dontsave) {
                    primaryStage.close();
                }
                else {
                    //wybrane cancel, nie rob nic
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}