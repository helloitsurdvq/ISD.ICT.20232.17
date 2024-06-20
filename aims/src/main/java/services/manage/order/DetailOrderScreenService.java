package services.manage.order;

import controller.ManageOrderController;
import model.media.Media;
import model.order.Order;
import model.order.OrderMedia;
import utils.Format;
import services.home.HomeService;
import services.manage.ManageScreenService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DetailOrderScreenService extends ManageScreenService implements Initializable {

    private Order order;

    private int orderId;

    @FXML
    private Label customerName;

    @FXML
    private Label customerPhone;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerProvince;

    @FXML
    private Label deliveryShipType;

    @FXML
    private Label deliveryDate;

    @FXML
    private Label deliveryInstruction;

    @FXML
    private Label totalPrice;

    @FXML
    private Label shippingFee;

    @FXML
    private Label totalMoney;

    @FXML
    private Label orderStatus;

    @FXML
    private Label paymentStatus;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<OrderMedia> orderMediaTable;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaId;

    @FXML
    private TableColumn<OrderMedia, String> orderMediaTitle;

    @FXML
    private TableColumn<OrderMedia, String> orderMediaType;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaPrice;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaQuantity;

    @FXML
    private TableColumn<OrderMedia, String> orderMediaImage;

    public DetailOrderScreenService(Stage stage, String screenPath, ManageOrderController manageOrderController, int orderId) throws IOException {
        super(stage, screenPath);
        super.setBController(manageOrderController);
        this.orderId = orderId;
    }

    public ManageOrderController getBController() { return (ManageOrderController) super.getBController(); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        cancelButton.setOnAction(e -> {
            openOrderManage();
        });
    }

    public int checkPaid(int orderId) throws SQLException {
        return getBController().checkPaid(orderId);
    }

    public void showDetailOrder() throws SQLException {
        Order order = getBController().getOrderById(orderId);
        // Customer information
        customerName.setText(order.getName());
        customerPhone.setText(order.getPhone());
        customerAddress.setText(order.getAddress());
        customerProvince.setText(order.getProvince());

        // Delivery information
        if(order.getShipment().getShipType() == 1) deliveryShipType.setText("Rush Order");
        else deliveryShipType.setText("Standard Order");
        deliveryDate.setText(order.getShipment().getDeliveryTime() != null ? order.getShipment().getDeliveryTime() : "N/A");
        deliveryInstruction.setText(order.getShipment().getDeliveryInstruction() != null ? order.getShipment().getDeliveryInstruction() : "N/A");

        // OrderMedia Information
        List<OrderMedia> listOrderMedia = order.getlstOrderMedia();
        orderMediaPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderMediaPrice.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer price, boolean empty) {
                super.updateItem(price, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(Format.getCurrencyFormat(price));
                    setAlignment(Pos.CENTER);
                }
            }
        });

        orderMediaQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orderMediaId.setCellValueFactory(cellData -> {
            Media media = cellData.getValue().getMedia();
            return new ReadOnlyObjectWrapper<>(media.getId());
        });

        orderMediaTitle.setCellValueFactory(cellData -> {
            Media media = cellData.getValue().getMedia();
            return new SimpleStringProperty(media != null ? media.getTitle() : "");
        });

        orderMediaType.setCellValueFactory(cellData -> {
            Media media = cellData.getValue().getMedia();
            return new SimpleStringProperty(media != null ? media.getType() : "");
        });

        orderMediaImage.setCellValueFactory(cellData -> {
            Media media = cellData.getValue().getMedia();
            return new SimpleStringProperty(media != null ? media.getImageURL() : "");
        });

        orderMediaImage.setCellFactory(col -> {
            TableCell<OrderMedia, String> cell = new TableCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        File file = new File(imageUrl);
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);

                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);

                        setAlignment(Pos.CENTER);
                        setGraphic(imageView);
                    }
                }
            };

            return cell;
        });
        orderMediaTable.getItems().setAll(listOrderMedia);
        totalPrice.setText(String.valueOf(Format.getCurrencyFormat(order.getAmount())));
        shippingFee.setText(String.valueOf(Format.getCurrencyFormat(order.getShippingFees())));
        totalMoney.setText(String.valueOf(Format.getCurrencyFormat(order.getAmount() + order.getShippingFees())));
        orderStatus.setText(order.getStatus());

        if ("approve".equalsIgnoreCase(order.getStatus())) {
            orderStatus.setTextFill(Color.GREEN);
        } else if ("refuse".equalsIgnoreCase(order.getStatus())) {
            orderStatus.setTextFill(Color.RED);
        } else if ("pending".equalsIgnoreCase(order.getStatus())) {
            orderStatus.setTextFill(Color.ORANGE);
        }

        if(checkPaid(orderId) == 1) {
            paymentStatus.setText("Paid");
            paymentStatus.setTextFill(Color.GREEN);
        } else {
            paymentStatus.setText("Not paid");
        }
    }
}