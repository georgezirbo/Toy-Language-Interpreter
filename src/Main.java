import controller.Controller;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.stream.Collectors;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader MainLoader = new FXMLLoader();
        MainLoader.setLocation(getClass().getResource("gui/Start.fxml"));
        Parent StartWindow = MainLoader.load();
        primaryStage.setTitle("Start");
        primaryStage.setScene(new Scene(StartWindow, 715, 550));
        primaryStage.getIcons().add(new Image("gui/logo.png"));

        primaryStage.show();
    }

}

