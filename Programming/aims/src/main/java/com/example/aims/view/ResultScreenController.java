package com.example.aims.view;

import com.example.aims.entity.PaymentTransaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultScreenController implements Initializable {


    // http://localhost:8080/vnpay_jsp/vnpay_return.jsp?vnp_Amount=18000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14198263
    // &vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3A06236597&vnp_PayDate=20231124213746&vnp_ResponseCode=00&vnp_TmnCode=5QT3R157
    // &vnp_TransactionNo=14198263&vnp_TransactionStatus=00&vnp_TxnRef=06236597
    // &vnp_SecureHash=2d2d33d975667b584a4a8ad635c43a3410c54e439f4e332031523a929d0d082113b3da217ba20741a9b70160369adc7360464f92e3226210b2414d7e2e012f81

    @FXML
    Text transIDText;


    @FXML
    Text amountText;

    @FXML
    Text statusText;

    @FXML
    Text bankText;

    @FXML
    Text descText;

    @FXML
    Text timeText;


    @FXML
    Button submitBtn;

    private  PaymentTransaction paymentTransaction;

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initView() {
        transIDText.setText(paymentTransaction.getId());
         amountText.setText(String.valueOf(paymentTransaction.getAmount()));
         statusText.setText(paymentTransaction.getStatus());
         bankText.setText(paymentTransaction.getBankName());
         descText.setText(paymentTransaction.getContent());
         timeText.setText(paymentTransaction.getTime().toString());

    }

    @FXML
    protected void Submit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InvoiceScreen.fxml"));
        Parent root = loader.load();

        // Lấy scene hiện tại từ nút được kích hoạt sự kiện
        Scene currentScene = ((Node) event.getSource()).getScene();

        // Thay đổi root của scene hiện tại
        currentScene.setRoot(root);
    }
}