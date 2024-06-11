package services.manage;

import controller.ManageOrderController;
import controller.MediaController;
import utils.Configs;
import services.BaseScreenService;
import services.home.HomeScreenService;
import services.manage.media.MediaManageScreenService;
import services.manage.order.OrderManageScreenService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageScreenService extends BaseScreenService implements Initializable {
    @FXML
    protected Button mediaManage;

    @FXML
    protected Button orderManage;

    @FXML
    protected Button backHome;

    protected HomeScreenService home;

    public ManageScreenService(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaManage.setOnAction(e -> {
            openMediaManage();
        });

        orderManage.setOnAction(e -> {
            openOrderManage();
        });

        backHome.setOnAction(e -> {
            backToHome();
        });
    }

    protected void openMediaManage() {
        try {
            MediaManageScreenService mediaManageScreen = new MediaManageScreenService(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
            mediaManageScreen.setHomeScreenHandler(this.home);
            mediaManageScreen.setBController(new MediaController());
            mediaManageScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void openOrderManage() {
        try {
            OrderManageScreenService orderManageScreen = new OrderManageScreenService(this.stage, Configs.ORDER_MANAGE_SCREEN_PATH);
            orderManageScreen.setHomeScreenHandler(this.home);
            orderManageScreen.setBController(new ManageOrderController());
            orderManageScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void backToHome() {
        try {
            HomeScreenService homeHandler = new HomeScreenService(stage, Configs.HOME_PATH);
            homeHandler.setScreenTitle("Home Screen");
            homeHandler.setImage();
            homeHandler.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}