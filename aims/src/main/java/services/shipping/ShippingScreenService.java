package services.shipping;

import exception.InvalidDeliveryInfoException;
import controller.PlaceOrderController;
import model.order.Order;
import utils.Configs;
import services.BaseScreenService;
import services.PopUpScreenService;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShippingScreenService extends BaseScreenService implements Initializable {
    @FXML
    private Label screenTitle;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private TextField instructions;

    @FXML
    private ComboBox<String> province;

    private Order order;

    public ShippingScreenService(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus();
                firstTime.setValue(false);
            }
        });
        this.province.getItems().addAll(Configs.PROVINCES);
    }

    @FXML
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {
        HashMap messages = new HashMap<>();
        messages.put("name", name.getText());
        messages.put("phone", phone.getText());
        messages.put("address", address.getText());
        messages.put("instructions", instructions.getText());
        messages.put("province", province.getValue());
        var placeOrderCtrl = getBController();
        if (!placeOrderCtrl.validateContainLetterAndNoEmpty(name.getText())) {
            PopUpScreenService.error("Name is not valid!");
            return;
        }
        if (!placeOrderCtrl.validatePhoneNumber(phone.getText())) {
            PopUpScreenService.error("Phone is not valid!");
            return;

        }
        if (!placeOrderCtrl.validateContainLetterAndNoEmpty(address.getText())) {
            PopUpScreenService.error("Address is not valid!");
            return;
        }
        if (province.getValue() == null) {
            PopUpScreenService.error("Province is empty!");
            return;
        }
        try {
            // process and validate delivery info
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(order.getAmount());
        order.setShippingFees(shippingFees);
        order.setName(name.getText());
        order.setPhone(phone.getText());
        order.setProvince(province.getValue());
        order.setAddress(address.getText());
        order.setInstruction(instructions.getText());

        //create delivery method screen
        BaseScreenService DeliveryMethodsScreenHandler = new DeliveryMethodsScreenService(this.stage, Configs.DELIVERY_METHODS_PATH, this.order);
        DeliveryMethodsScreenHandler.setPreviousScreen(this);
        DeliveryMethodsScreenHandler.setHomeScreenHandler(homeScreenService);
        DeliveryMethodsScreenHandler.setScreenTitle("Delivery method screen");
        DeliveryMethodsScreenHandler.setBController(getBController());
        DeliveryMethodsScreenHandler.show();
    }

    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
}
