package com.example.aims.subsystem.vnpay;

import com.example.aims.entity.PaymentTransaction;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Response {
    // http://localhost:8080/vnpay_jsp/vnpay_return.jsp?vnp_Amount=18000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14198263
    // &vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3A06236597&vnp_PayDate=20231124213746&vnp_ResponseCode=00&vnp_TmnCode=5QT3R157
    // &vnp_TransactionNo=14198263&vnp_TransactionStatus=00&vnp_TxnRef=06236597
    // &vnp_SecureHash=2d2d33d975667b584a4a8ad635c43a3410c54e439f4e332031523a929d0d082113b3da217ba20741a9b70160369adc7360464f92e3226210b2414d7e2e012f81

    public static PaymentTransaction getPaymentTransaction(String response) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        try {
            URI uri = new URI(response);
            String query = uri.getQuery();

            Map<String, String> paramMap = parseQueryParameters(query);
            paymentTransaction.setMethod("VNPay");
            paymentTransaction.setAmount(Integer.valueOf(paramMap.get("vnp_Amount")));
            paymentTransaction.setBankName(paramMap.get("vnp_BankCode"));
            paymentTransaction.setContent(paramMap.get("vnp_OrderInfo"));

            String payDateString = paramMap.get("vnp_PayDate");
            LocalDateTime payDate = parsePayDate(payDateString);
            paymentTransaction.setTime(payDate);
            paymentTransaction.setId(paramMap.get("vnp_TransactionNo"));
            paymentTransaction.setStatus(Config.getStatus(paramMap.get("vnp_ResponseCode")));
        } catch (URISyntaxException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return paymentTransaction;
    }

    private static Map<String, String> parseQueryParameters(String query) {
        Map<String, String> paramMap = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    paramMap.put(key, value);
                }
            }
        }
        return paramMap;
    }

    private static LocalDateTime parsePayDate(String payDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(payDateString, formatter);
    }
}