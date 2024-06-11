package services.home;

import controller.AuthController;
import controller.MediaController;
import utils.Configs;
import utils.Format;
import services.BaseScreenService;
import services.manage.media.MediaManageScreenService;
import services.PopUpScreenService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoginScreenService extends BaseScreenService {
    public static Logger LOGGER = Format.getLogger(LoginScreenService.class.getName());

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    public LoginScreenService(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
    }

    public AuthController getBController() {
        return (AuthController) super.getBController();
    }

    @FXML
    void login() throws IOException, InterruptedException, SQLException {
        try {
            getBController().login(email.getText(), password.getText());
            PopUpScreenService.success("Login Successfully!");
            MediaManageScreenService mediaManageScreen = new MediaManageScreenService(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
            mediaManageScreen.setHomeScreenHandler(homeScreenService);
            mediaManageScreen.setBController(new MediaController());
            mediaManageScreen.show();
        } catch (Exception ex) {
            PopUpScreenService.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    void backToHomeScreen(MouseEvent event) throws IOException, InterruptedException, SQLException {
        this.homeScreenService.show();
    }
}