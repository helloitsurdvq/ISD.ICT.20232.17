import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateProductTest {
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("P001", "Book", "Education", 100.0f, 120.0f);
    }

    @Test
    public void testConstructor() {
        assertEquals("P001", product.getProductId());
        assertEquals("Book", product.getProductName());
        assertEquals("Education", product.getProductType());
        assertEquals(100.0f, product.getProductValue());
        assertEquals(120.0f, product.getProductPrice());
    }

    @Test
    public void testSettersAndGetters() {
        product.setProductId("P002");
        assertEquals("P002", product.getProductId());

        product.setProductName("CD");
        assertEquals("CD", product.getProductName());

        product.setProductType("Music");
        assertEquals("Music", product.getProductType());

        product.setProductValue(200.0f);
        assertEquals(200.0f, product.getProductValue());

        product.setProductPrice(250.0f);
        assertEquals(250.0f, product.getProductPrice());
    }

    @Test
    public void testPriceCalculation() {
        float priceWithVAT = product.getProductPrice() * 1.1f;
        assertEquals(132.0f, priceWithVAT);
    }
}
