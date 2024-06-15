import com.example.aims.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateAddressTest {
    private PlaceOrderController placeOrderController;
    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }
    @ParameterizedTest
    @CsvSource({
            "My Dinh, true",
            " , false",
            ", false",
    })

    public void testValidateAddress(String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddress(address);
        assertEquals(expected, isValid);
    }
}