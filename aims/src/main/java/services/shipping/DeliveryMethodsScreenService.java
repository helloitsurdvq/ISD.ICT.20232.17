package services.shipping;


import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import model.Invoice;
import model.order.Order;
import model.Shipment;
import utils.Configs;
import services.BaseScreenService;
import services.invoice.InvoiceScreenService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DeliveryMethodsScreenService extends BaseScreenService {
    private Order order;

    @FXML
    private RadioButton placeRushOrderValue;

    @FXML
    private RadioButton placeOrderValue;

    @FXML
    private TextField deliveryInstruction;

    @FXML
    private TextField shipmentDetail;

    @FXML
    private DatePicker deliveryTime;

    @FXML
    private Label errorProvince;

    @FXML
    private Button updateDeliveryMethodInfoButton;

    public DeliveryMethodsScreenService(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }

    @FXML
    private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
        String deliveryInstructionString = new String(deliveryInstruction.getText());
        String shipmentDetailString = new String(shipmentDetail.getText());
        String deliveryDateString = new String();
        if (deliveryTime.getValue() != null) {
            deliveryDateString = new String(deliveryTime.getValue().toString());
        }
        int typeDelivery;
        if (placeRushOrderValue.isSelected()) {
            typeDelivery = utils.Configs.PLACE_RUSH_ORDER;
        } else {
            typeDelivery = utils.Configs.PLACE_ORDER;
        }
        var shipment = new Shipment(typeDelivery);
        shipment.setShipmentDetail(shipmentDetailString);
        shipment.setDeliveryTime(deliveryDateString);
        shipment.setDeliveryInstruction(deliveryInstructionString);

        PlaceRushOrderController.validatePlaceRushOrderData(shipment);
        order.setShipment(shipment);

        // // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenService InvoiceScreenHandler = new InvoiceScreenService(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenService);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }

    @FXML
    private void handleBack(MouseEvent event) throws IOException {
        // Back to previous screen
        BaseScreenService ShippingScreenHandler = new ShippingScreenService(this.stage, Configs.SHIPPING_SCREEN_PATH,
                this.order);
        ShippingScreenHandler.setPreviousScreen(this);
        ShippingScreenHandler.setHomeScreenHandler(homeScreenService);
        ShippingScreenHandler.setScreenTitle("Shipping Screen");
        ShippingScreenHandler.setBController(getBController());
        ShippingScreenHandler.show();
    }

    @FXML
    private void handleDeliveryType(ActionEvent event) {
        if (placeOrderValue.isSelected()) {
            deliveryInstruction.setDisable(true);
            shipmentDetail.setDisable(true);
            deliveryTime.setDisable(true);
        } else if (placeRushOrderValue.isSelected()) {
            deliveryInstruction.setDisable(false);
            shipmentDetail.setDisable(false);
            deliveryTime.setDisable(false);
        }
        handleProvinceError(event);
    }

    @FXML
    private void handleProvinceError(ActionEvent event) {
        String province = new String(order.getProvince());

        errorProvince.setVisible(false);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(false);

        if (!province.equals("Hà Nội")) {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(true);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                updateDeliveryMethodInfoButton.setDisable(true);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
            }
        } else {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(false);
                deliveryInstruction.setDisable(false);
                shipmentDetail.setDisable(false);
                deliveryTime.setDisable(false);
                updateDeliveryMethodInfoButton.setDisable(false);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                errorProvince.setVisible(false);
            }
        }
    }

    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
}
