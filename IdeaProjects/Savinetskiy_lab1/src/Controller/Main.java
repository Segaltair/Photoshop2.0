package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainWindow.fxml"));
        primaryStage.setTitle("Photoshop 2.0");
        primaryStage.setMinHeight(460);
        primaryStage.setMinWidth(620);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}