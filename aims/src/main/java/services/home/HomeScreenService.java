package services.home;

import exception.ViewCartException;
import controller.AuthController;
import controller.HomeController;
import controller.MediaController;
import controller.ViewCartController;
import model.cart.Cart;
import model.media.Media;
import utils.Configs;
import utils.Format;
import services.BaseScreenService;
import services.cart.CartScreenService;
import services.manage.media.MediaManageScreenService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HomeScreenService extends BaseScreenService implements Initializable {
    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private Button loginBtn;

    @FXML
    private VBox vboxMedia1;

    @FXML
    private VBox vboxMedia2;

    @FXML
    private VBox vboxMedia3;

    @FXML
    private HBox hboxMedia;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private TextField SearchField;

    @FXML
    private Button BtnSort;

    private List homeItems;
    private List searchItems;

    public HomeScreenService(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public Label getNumMediaCartLabel() {
        return this.numMediaInCart;
    }

    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController());
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media) object;
                HomeService m1 = new HomeService(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        aimsImage.setOnMouseClicked(e -> {
            SearchField.setText("");
            this.searchItems = null;
            addMediaHome(this.homeItems);
        });

        cartImage.setOnMouseClicked(e -> {
            CartScreenService cartScreen;
            try {
                cartScreen = new CartScreenService(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });

        loginBtn.setOnMouseClicked(e -> {
            LoginScreenService loginScreen;
            try {
                loginScreen = new LoginScreenService(this.stage, Configs.LOGIN_SCREEN_PATH);
                loginScreen.setHomeScreenHandler(this);
                loginScreen.setBController(new AuthController());
                loginScreen.show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);

        // Search engine
        splitMenuBtnSearch.setOnMouseClicked(event -> {
            String target_item_name = SearchField.getText().strip().toLowerCase();
            setBController(new HomeController());
            if (target_item_name.isEmpty()){
                setBController(new HomeController());
                try {
                    List medium = getBController().getAllMedia();
                    this.searchItems = new ArrayList<>();
                    for (Object object : medium) {
                        Media media = (Media) object;
                        HomeService m1 = new HomeService(Configs.HOME_MEDIA_PATH, media, this);
                        this.searchItems.add(m1);
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    List all_media = getBController().getAllMedia();
                    this.searchItems = new ArrayList<>();
                    for (Object object : all_media) {
                        Media media = (Media) object;
                        if (media.getTitle().toLowerCase().equals(target_item_name)){
                            HomeService m1 = new HomeService(Configs.HOME_MEDIA_PATH, media, this);
                            this.searchItems.add(m1);
                        }
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            addMediaHome(this.searchItems);
        });

        // Sort product price
        BtnSort.setOnMouseClicked(event -> {
            List sortItems;
            if (this.searchItems == null){
                sortItems = new ArrayList<>(this.homeItems);
            }
            else {
                sortItems = new ArrayList<>(this.searchItems);
            }
            sortItems.sort(new SortProductComparator());
            addMediaHome(sortItems);
        });
    }

    public void setImage() {
        setImageToImageViewFromClasspath("/assets/Logo.png", aimsImage);
        setImageToImageViewFromClasspath("/assets/cart.png", cartImage);
    }

    private void setImageToImageViewFromClasspath(String imagePath, ImageView imageView) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            imageView.setImage(image);
        } else {
            System.out.println("Image file not found in classpath: \" + imagePath");
        }
    }

    public void addMediaHome(List items) {
        ArrayList mediaItems = (ArrayList) ((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        while (!mediaItems.isEmpty()) {
            hboxMedia.getChildren().forEach(node -> {
                int vid = hboxMedia.getChildren().indexOf(node);
                VBox vBox = (VBox) node;
                while (vBox.getChildren().size() < 3 && !mediaItems.isEmpty()) {
                    HomeService media = (HomeService) mediaItems.get(0);
                    vBox.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            });
            return;
        }
    }

    private void addMenuItem(int position, String text, MenuButton menuButton) {
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });

            List filteredItems = new ArrayList<>();
            homeItems.forEach(me -> {
                HomeService media = (HomeService) me;
                if (media.getMedia().getTitle().toLowerCase().startsWith(text.toLowerCase())) {
                    filteredItems.add(media);
                }
            });
            addMediaHome(filteredItems);
        });
        menuButton.getItems().add(position, menuItem);
    }
}
