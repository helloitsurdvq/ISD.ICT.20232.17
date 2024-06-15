import com.example.aims.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatePhoneNumberTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "0123456789, true",
            "9876543210, false",
            "12345, false",
            "abc1234567, false",
            " , false",
    })
    public void testValidatePhoneNumber(String phoneNumber, boolean expected) {
        boolean isValid = placeOrderController.validatePhoneNumber(phoneNumber);
        assertEquals(expected, isValid);
    }
}
