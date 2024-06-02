package services.payment;

import services.BaseScreenService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class ResultScreenService extends BaseScreenService {
    private String result;
    private String message;
    @FXML
    private Label pageTitle;
    @FXML
    private Label resultLabel;
    @FXML
    private Button okButton;
    @FXML
    private Label messageLabel;
    public ResultScreenService(Stage stage, String screenPath, String result, String message) throws IOException {
        super(stage, screenPath);
        resultLabel.setText(result);
        messageLabel.setText(message);
    }
}
