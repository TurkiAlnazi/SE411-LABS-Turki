package sa.informatix.tools;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Trading Tool");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}