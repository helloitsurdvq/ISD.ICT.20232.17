package app;

import utils.Configs;
import services.home.HomeScreenService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        try {
            StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(Configs.SPLASH_SCREEN_PATH));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            final String appName = "AIMS";
            stage.setTitle(appName);
            Image icon = new Image(getClass().getResourceAsStream("/assets/aims_cover_image.png"));
            stage.getIcons().add(icon);
            stage.show();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    HomeScreenService homeHandler = new HomeScreenService(stage, Configs.HOME_PATH);
                    homeHandler.setScreenTitle("Home Screen");
                    homeHandler.setImage();
                    homeHandler.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}