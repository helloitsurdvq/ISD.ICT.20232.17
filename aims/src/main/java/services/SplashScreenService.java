package services;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreenService implements Initializable {
    @FXML
    private ImageView logo;

    private static final Logger LOGGER = Logger.getLogger(SplashScreenService.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String imagePath = "/assets/Logo.png"; // Adjust the path as needed
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            logo.setImage(image);
        } else {
            LOGGER.log(Level.SEVERE, "Image file not found in classpath: " + imagePath);
        }
    }
}
