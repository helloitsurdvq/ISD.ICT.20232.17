package services.manage.media.detail;

import controller.MediaController;
import model.media.Book;
import utils.Configs;
import services.BaseScreenService;
import services.manage.media.MediaManageScreenService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailScreenService extends BaseScreenService implements Initializable {
    protected int id;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    protected Button exitButton;

    @FXML
    protected Label authorText;

    @FXML
    protected Label coverTypeText;

    @FXML
    protected Label publisherText;

    @FXML
    protected Label pubDateText;

    @FXML
    protected Label numPageText;

    @FXML
    protected Label languageText;

    @FXML
    protected Label bookCatText;

    @FXML
    protected ImageView imageField;

    public DetailScreenService(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public MediaController getBController() {
        return (MediaController) super.getBController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e -> {
            try {
                backScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void showDetailBook(int id) throws SQLException {
        Book book = getBController().getBookMediaById(id);
        authorText.setText(book.getAuthor());
        coverTypeText.setText(book.getCoverType());
        publisherText.setText(book.getPublisher());
        pubDateText.setText(String.valueOf(book.getPublishDate()));
        numPageText.setText(String.valueOf(book.getNumOfPages()));
        languageText.setText(book.getLanguage());
        bookCatText.setText(book.getBookCategory());

        File file = new File(book.getImageURL());
        Image image = new Image(file.toURI().toString());
        imageField.setImage(image);
    }

    protected void backScreen() throws IOException {
        MediaManageScreenService mediaScreen = new MediaManageScreenService(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
        mediaScreen.setBController(new MediaController());
        mediaScreen.show();
    }
}