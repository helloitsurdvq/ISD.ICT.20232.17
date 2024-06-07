package services.manage.media.form;

import controller.MediaController;
import model.media.Media;
import utils.Configs;
import services.BaseScreenService;
import services.manage.media.MediaManageScreenService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MediaFormScreenService extends BaseScreenService implements Initializable {
    @FXML
    protected Label formTitle;

    @FXML
    protected TextField titleField;

    @FXML
    protected TextField categoryField;

    @FXML
    protected TextField priceField;

    @FXML
    protected TextField quantityField;

    @FXML
    protected Button uploadButton;

    @FXML
    protected Button saveButton;

    @FXML
    protected Button cancelButton;

    protected int id;
    protected String uploadedFilePath = "";

    public void setId(int id) {
        this.id = id;
    }

    public MediaFormScreenService(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public MediaController getBController() {
        return (MediaController) super.getBController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnAction(e -> {
            try {
                backScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        saveButton.setOnAction(e -> {
            try {
                save();
                backScreen();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .addAll(
                            new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
                            new FileChooser.ExtensionFilter("png files (*.jpeg)", "*.jpeg"),
                            new FileChooser.ExtensionFilter("png files (*.png)", "*.png")
                    );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String assetsFolder = "aims/src/main/resources/assets/uploads";
                File assetsDir = new File(assetsFolder);
                if (!assetsDir.exists()) {
                    assetsDir.mkdirs();
                }
                try {
                    String uniqueFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                    Path destinationPath = Paths.get(assetsFolder, uniqueFileName);
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    uploadButton.setText(selectedFile.getName());
                    uploadedFilePath = assetsFolder + "/" + uniqueFileName;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    protected void save() throws SQLException {
        Media media = getMediaValues();
        getBController().saveMedia(media);
    }

    protected Media getMediaValues() {
        String title = titleField.getText();
        String category = categoryField.getText();
        int price = Integer.valueOf(priceField.getText());
        int quantity = Integer.valueOf(quantityField.getText());
        return new Media(id, title, category, price, quantity, uploadedFilePath, "");
    }

    protected void backScreen() throws IOException {
        MediaManageScreenService mediaScreen = new MediaManageScreenService(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
        mediaScreen.setBController(new MediaController());
        mediaScreen.show();
    }

    protected void setDefaultValues(String title, String category, int price, int value, int quantity, String imageURL) {
        titleField.setText(title);
        categoryField.setText(category);
        priceField.setText(String.valueOf(price));
        quantityField.setText(String.valueOf(quantity));
        uploadedFilePath = imageURL;
    }

    public void setFormTitle(String title) {
        this.formTitle.setText(title);
    }
}