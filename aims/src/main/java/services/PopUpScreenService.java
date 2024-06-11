package services;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Configs;

import java.io.IOException;
import java.net.URL;

public class PopUpScreenService extends BaseScreenService {
    @FXML
    ImageView tickicon;

    @FXML
    Label message;

    public PopUpScreenService(Stage stage) throws IOException {
        super(stage, Configs.POPUP_PATH);
    }

    private static PopUpScreenService popup(String message, String imagepath, Boolean undecorated) throws IOException {
        PopUpScreenService popup = new PopUpScreenService(new Stage());
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        popup.message.setText(message);
        popup.setImage(imagepath);
        return popup;
    }

    public void setImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            Image img = new Image(imageUrl.toString());
            tickicon.setImage(img);
        } else {
            System.err.println("Image not found: " + path); // debug
        }
    }

    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    public void show(double time) {
        super.show();
        close(time);
    }

    public void close(double time) {
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished(event -> stage.close());
        delay.play();
    }

    public static void success(String message) throws IOException {
        popup(message,  "/assets/tickgreen.png", true).show(true);
    }

    public static void error(String message) throws IOException {
        popup(message, "/assets/tickerror.png", false).show(false);
    }

    public static PopUpScreenService loading(String message) throws IOException {
        return popup(message, "/assets/loading.gif", true);
    }
}
