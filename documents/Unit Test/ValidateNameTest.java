import com.example.aims.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateNameTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    public void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Dinh Viet Quang, true",
            "Nam, true",
            "123, false",
            " , false",
            " , false",
            "V-Q, false",
    })
    public void testValidateName(String name, boolean expected) {
        boolean isValid = placeOrderController.validateName(name);
        assertEquals(expected, isValid);
    }
}
