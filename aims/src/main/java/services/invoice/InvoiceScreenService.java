package services.invoice;

import exception.ProcessInvoiceException;
import controller.PaymentController;
import model.Invoice;
import model.order.OrderMedia;
import utils.Configs;
import utils.Format;
import services.BaseScreenService;
import services.payment.PaymentScreenService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class InvoiceScreenService extends BaseScreenService {
    private static Logger LOGGER = Format.getLogger(InvoiceScreenService.class.getName());
    @FXML
    private Label pageTitle;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label province;

    @FXML
    private Label address;

    @FXML
    private Label instructions;

    @FXML
    private Label subtotal;

    @FXML
    private Label shippingFees;

    @FXML
    private Label total;
    @FXML
    private VBox vboxItems;

    private Invoice invoice;

    public InvoiceScreenService(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        setInvoiceInfo();
    }

    private void setInvoiceInfo() {
        name.setText(invoice.getOrder().getName());
        province.setText(invoice.getOrder().getProvince());
        instructions.setText(invoice.getOrder().getInstruction());
        address.setText(invoice.getOrder().getAddress());
        subtotal.setText(Format.getCurrencyFormat(invoice.getOrder().getAmount()));
        shippingFees.setText(Format.getCurrencyFormat(invoice.getOrder().getShippingFees()));
        int amount = invoice.getOrder().getAmount() + invoice.getOrder().getShippingFees();
        total.setText(Format.getCurrencyFormat(amount));
        invoice.setAmount(amount);
        invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceScreenService mis = new MediaInvoiceScreenService(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                vboxItems.getChildren().add(mis.getContent());
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }
        });
    }

    @FXML
    void confirmInvoice(MouseEvent event) throws IOException {
        BaseScreenService paymentScreen = new PaymentScreenService(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
        paymentScreen.setBController(new PaymentController());
        paymentScreen.setPreviousScreen(this);
        paymentScreen.setHomeScreenHandler(homeScreenService);
        paymentScreen.setScreenTitle("Payment Screen");
        paymentScreen.show();
    }
}