package services;

import controller.BaseController;
import services.home.HomeScreenService;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Hashtable;

public class BaseScreenService extends FXMLScreenService {
    protected final Stage stage;
    protected HomeScreenService homeScreenService;
    protected Hashtable<String, String> messages;
    private Scene scene;
    private BaseScreenService prev;
    private BaseController bController;

    private BaseScreenService(String screenPath) throws IOException {
        super(screenPath);
        this.stage = new Stage();
    }

    public BaseScreenService(Stage stage, String screenPath) throws IOException {
        super(screenPath);
        this.stage = stage;
    }

    public BaseScreenService getPreviousScreen() {
        return this.prev;
    }

    public void setPreviousScreen(BaseScreenService prev) {
        this.prev = prev;
    }

    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void setScreenTitle(String string) {
        this.stage.setTitle(string);
    }

    public BaseController getBController() {
        return this.bController;
    }

    public void setBController(BaseController bController) {
        this.bController = bController;
    }

    public void forward(Hashtable messages) {
        this.messages = messages;
    }

    public void setHomeScreenHandler(HomeScreenService HomeScreenService) {
        this.homeScreenService = HomeScreenService;
    }
}
