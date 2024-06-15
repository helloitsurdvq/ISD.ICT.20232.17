import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateInvoiceTest {
    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice("I001", 15.0f, 150.0f);
    }

    @Test
    public void testConstructor() {
        assertEquals("I001", invoice.getInvoiceId());
        assertEquals(15.0f, invoice.getShippingFee());
        assertEquals(150.0f, invoice.getTotalFee());
    }

    @Test
    public void testSettersAndGetters() {
        invoice.setInvoiceId("I002");
        assertEquals("I002", invoice.getInvoiceId());

        invoice.setShippingFee(20.0f);
        assertEquals(20.0f, invoice.getShippingFee());

        invoice.setTotalFee(200.0f);
        assertEquals(200.0f, invoice.getTotalFee());
    }

    @Test
    public void testTotalFeeCalculation() {
        float total = invoice.getShippingFee() + invoice.getTotalFee();
        assertEquals(165.0f, total);
    }
}