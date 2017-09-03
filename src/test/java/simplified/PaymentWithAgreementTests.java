package simplified;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simplified.domain.DtoWithId;
import simplified.domain.ForeignPaymentDTO;
import simplified.external.Agreement;
import simplified.external.Payment;
import simplified.external.PaymentType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class PaymentWithAgreementTests {
    private static final long PAYMENT_ID = 111L;

    PaymentMapper mapper = new PaymentMapper();
    private Payment foreignPayment;

    @BeforeEach
    void setUp() {
        foreignPayment = new Payment();
        foreignPayment.setId(PAYMENT_ID);
        foreignPayment.setPaymentType(PaymentType.FOREIGN);
    }

    @Test
    void mapPaymentWithAgreement() {
        Agreement agreement = new Agreement();
        agreement.setAgreementId(333L);
        agreement.setPeriode(Periode.DAILY);
        agreement.setStartDate(LocalDate.of(2017, 9, 1));
        agreement.setEndDate(LocalDate.of(2017, 10, 1));

        foreignPayment.setAgreement(agreement);

        DtoWithId dto = mapper.map(foreignPayment);

        ForeignPaymentDTO foreignPaymentDTO = (ForeignPaymentDTO) dto;
        assertEquals("111", foreignPaymentDTO.foreignPaymentId);

        assertAll("if agreement exist", () -> {
            assertNotNull(foreignPaymentDTO.agreement);
            assertAll("agreement",
                    () -> assertEquals("333", foreignPaymentDTO.agreement.id),
                    () -> assertEquals("DAILY", foreignPaymentDTO.agreement.periode),
                    () -> assertEquals("01.09.2017", foreignPaymentDTO.agreement.startDate),
                    () -> assertEquals("01.10.2017", foreignPaymentDTO.agreement.endDate)
            );
        });
    }

    @Test
    void mapPaymentWithoutAgreement() {
        DtoWithId dto = mapper.map(foreignPayment);

        ForeignPaymentDTO foreignPaymentDTO = (ForeignPaymentDTO) dto;
        assertEquals("111", foreignPaymentDTO.foreignPaymentId);

        assertAll("if agreement exist", () -> {
            assertNotNull(foreignPaymentDTO.agreement);

            //This will not be evaluated because the previous one is invalid.
            assertAll("agreement",
                    () -> assertEquals("333", foreignPaymentDTO.agreement.id),
                    () -> assertEquals("DAILY", foreignPaymentDTO.agreement.periode),
                    () -> assertEquals("01.09.2017", foreignPaymentDTO.agreement.startDate),
                    () -> assertEquals("01.10.2017", foreignPaymentDTO.agreement.endDate)
            );
        });
    }
}
