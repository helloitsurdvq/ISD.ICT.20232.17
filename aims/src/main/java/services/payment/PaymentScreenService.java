package services.payment;

import controller.PaymentController;
import model.Invoice;
import subsystem.VNPay.Config;
import utils.Configs;
import services.BaseScreenService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PaymentScreenService extends BaseScreenService {
    private Invoice invoice;
    @FXML
    private Label pageTitle;
    @FXML
    private VBox vBox;

    public PaymentScreenService(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;

        displayWebView();
    }

    private void displayWebView(){
        var paymentController = new PaymentController();
        var paymentUrl = paymentController.getUrlPay(invoice.getAmount(), "AIMS purchasing");
        WebView paymentView = new WebView();
        WebEngine webEngine = paymentView.getEngine();
        webEngine.load(paymentUrl);
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            handleUrlChanged(newValue);
        });
        vBox.getChildren().clear();
        vBox.getChildren().add(paymentView);
    }

    private static Map<String, String> parseQueryString(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    private void handleUrlChanged(String newValue) {
        if (newValue.contains(Config.vnp_ReturnUrl)) {
            try {
                URI uri = new URI(newValue);
                String query = uri.getQuery();
                Map<String, String> params = parseQueryString(query);
                payOrder(params);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void payOrder(Map<String, String> res) throws IOException {
        var ctrl = (PaymentController) super.getBController();
        Map<String, String> response = ctrl.makePayment(res, this.invoice.getOrder().getId());
        BaseScreenService resultScreen = new ResultScreenService(this.stage, Configs.RESULT_SCREEN_PATH,
                response.get("RESULT"), response.get("MESSAGE"));
        ctrl.emptyCart();
        resultScreen.setPreviousScreen(this);
        resultScreen.setHomeScreenHandler(homeScreenService);
        resultScreen.setScreenTitle("Result Screen");
        resultScreen.show();
    }
}