package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("UIResources", new Locale("pt", "BR")));
        fxmlLoader.setLocation(getClass().getResource("/fxml/ClientSelection.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Client Selection");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
