import com.example.aims.controller.PlaceOrderController;
import com.example.aims.controller.RushOrderController;
import com.example.aims.entity.Media;
import com.example.aims.entity.MediaShippingMethod;
import com.example.aims.entity.ShippingMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateMediaRushOrderTest {

    private RushOrderController rushOrderController;
    private ShippingMethod rushOrderMethod;
    private ShippingMethod normalMethod;

    private Media media1;
    private Media media2;

    private MediaShippingMethod mediaShippingMethod1;
    private MediaShippingMethod mediaShippingMethod2;

    @BeforeEach
    public void setUp() {
        rushOrderController = new RushOrderController();
        rushOrderMethod = new ShippingMethod(1, "Rush Order", "Description ...");
        normalMethod = new ShippingMethod(0, "Normal Method", "Description ...");

        media1 = new Media(1, "Taylor Swift", "Book", 10000, 10000, 10, "imageURl");
        media2 = new Media(2, "Yoasobi", "Book", 10000, 10000, 10, "imageURl");

        mediaShippingMethod1 = new MediaShippingMethod(media1, normalMethod);
        mediaShippingMethod2 = new MediaShippingMethod(media2, rushOrderMethod);

        media1.setMediaShippingMethod(mediaShippingMethod1);
        media2.setMediaShippingMethod(mediaShippingMethod2);
    }

    @Test
    public void test1() {
        boolean isSupport = rushOrderController.validateMediaRushOrder(media1);
        assertEquals(false, isSupport);
    }

    @Test
    public void test2() {
        boolean isSupport = rushOrderController.validateMediaRushOrder(media2);
        assertEquals(true, isSupport);
    }
}
