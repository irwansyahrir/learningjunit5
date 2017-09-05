package myusecase;

import myusecase.domain.DomesticPaymentDTO;
import myusecase.domain.DtoWithId;
import myusecase.domain.ForeignPaymentDTO;
import myusecase.domain.TransferDTO;
import myusecase.external.Payment;
import myusecase.external.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMapperTestUsingReporter {

    private static final long PAYMENT_ID_LONG = 123L;
    private static final String DTO_ID_STRING = "123";

    PaymentMapper mapper = new PaymentMapper();

    private Payment payment;

    @BeforeEach
    void setUp(TestReporter testReporter) {
        payment = new Payment();
        payment.setId(PAYMENT_ID_LONG);
        testReporter.publishEntry("payment Id", Long.toString(payment.getId()));
    }

    @Test
    void testMapForeignPayment(TestReporter testReporter) {
        payment.setPaymentType(PaymentType.FOREIGN);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((ForeignPaymentDTO) dto).foreignPaymentId);
        testReporter.publishEntry("Payment type", payment.getPaymentType().toString());
    }

    @Test
    void testMapDomesticPayment(TestReporter testReporter) {
        payment.setPaymentType(PaymentType.DOMESTIC);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((DomesticPaymentDTO)dto).domesticPaymentId);
        testReporter.publishEntry("Payment type", payment.getPaymentType().toString());
    }

    @Test
    void testMapTransfer(TestReporter testReporter) {
        payment.setPaymentType(PaymentType.TRANSFER);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((TransferDTO) dto).transferId);
        System.out.println("PaymentType = " + payment.getPaymentType().toString());
        testReporter.publishEntry("What a key", "What a value!");
        testReporter.publishEntry("Payment type", payment.getPaymentType().toString());
    }
}
