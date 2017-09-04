package simplified;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import simplified.domain.DomesticPaymentDTO;
import simplified.domain.ForeignPaymentDTO;
import simplified.domain.TransferDTO;
import simplified.external.Agreement;
import simplified.external.Payment;
import simplified.external.PaymentType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("original")
@DisplayName("Mapping regular payments and payments with agreement")
public class PaymentMapperUsingNested {
    private static final long PAYMENT_ID_LONG = 123L;
    private static final String DTO_ID_STRING = "123";

    PaymentMapper mapper = new PaymentMapper();

    private Payment payment;

    @Nested
    class RegularPayment {
        @BeforeEach
        void setUp() {
            payment = new Payment();
            payment.setId(PAYMENT_ID_LONG);
        }

        @Test
        void testMapForeignPayment() {
            payment.setPaymentType(PaymentType.FOREIGN);
            ForeignPaymentDTO dto = (ForeignPaymentDTO) mapper.map(payment);
            assertEquals(DTO_ID_STRING, dto.foreignPaymentId);
            assertNull(dto.agreement);
        }

        @Test
        void testMapDomesticPayment() {
            payment.setPaymentType(PaymentType.DOMESTIC);
            DomesticPaymentDTO dto = (DomesticPaymentDTO) mapper.map(payment);
            assertEquals(DTO_ID_STRING, dto.domesticPaymentId);
        }

        @Test
        void testMapTransfer() {
            payment.setPaymentType(PaymentType.TRANSFER);
            TransferDTO dto = (TransferDTO) mapper.map(payment);
            assertEquals(DTO_ID_STRING, dto.transferId);
        }
    }

    @Nested
    class PaymentWithAgreement {
        Agreement agreement = null;

        @BeforeEach
        void setUp() {
            payment = new Payment();
            payment.setId(PAYMENT_ID_LONG);

            agreement = new Agreement();
            agreement.setAgreementId(333L);
            agreement.setPeriode(Periode.DAILY);
            agreement.setStartDate(LocalDate.of(2017, 9, 1));
            agreement.setEndDate(LocalDate.of(2017, 10, 1));

            payment.setAgreement(agreement);
        }

        @Test
        void testMapForeignPayment() {
            payment.setPaymentType(PaymentType.FOREIGN);
            ForeignPaymentDTO dto = (ForeignPaymentDTO) mapper.map(payment);
            assertEquals(DTO_ID_STRING, dto.foreignPaymentId);
            assertNotNull(dto.agreement);
            assertEquals("333", dto.agreement.id);
        }

        //Test map other types ...

    }
}
