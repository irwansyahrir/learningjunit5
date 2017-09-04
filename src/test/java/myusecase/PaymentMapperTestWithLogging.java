package myusecase;

import myusecase.domain.DomesticPaymentDTO;
import myusecase.domain.DtoWithId;
import myusecase.domain.ForeignPaymentDTO;
import myusecase.external.Agreement;
import myusecase.external.Payment;
import myusecase.external.PaymentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("original")
@DisplayName("Test mapping with logging to use @BeforeAll")
public class PaymentMapperTestWithLogging {

    static Logger LOG = Logger.getLogger(PaymentMapperTestWithLogging.class.getName());

    @BeforeAll
    static void initTheWholeTestClass() {
        LOG.info("---------------------------------------------");
        LOG.info("Gentlemen, start your test engine!");
        LOG.info("---------------------------------------------");
    }

    @AfterAll()
    static void endTheWholeTestClass() {
        LOG.info("---------------------------------------------");
        LOG.info("Time flies, the tests are sadly over :(");
        LOG.info("---------------------------------------------");
    }

    PaymentMapper mapper = new PaymentMapper();
    private Payment payment;

    @BeforeEach
    void setUp() {
        LOG.info("Instantiate the payment with id : 123L");
        payment = new Payment();
        payment.setId(123L);
    }

    @Test
    void testMapForeignPayment() {
        LOG.info("Set payment type as Foreign and map it");
        payment.setPaymentType(PaymentType.FOREIGN);
        DtoWithId dto = mapper.map(payment);
        assertEquals("123", ((ForeignPaymentDTO) dto).foreignPaymentId);
    }

    @Test
    void testMapDomesticPaymentWithAgreement() {
        LOG.info("Set payment type as Domestic, set an agreement, and then map it");

        payment.setPaymentType(PaymentType.DOMESTIC);
        Agreement agreement = new Agreement();
        agreement.setAgreementId(111L);
        agreement.setPeriode(Periode.DAILY);
        agreement.setStartDate(LocalDate.of(2017, 9, 1));
        agreement.setEndDate(LocalDate.of(2017, 10, 1));

        payment.setAgreement(agreement);

        DtoWithId dto = mapper.map(payment);
        assertEquals("123", ((DomesticPaymentDTO)dto).domesticPaymentId);
        assertEquals("111", ((DomesticPaymentDTO)dto).agreement.id);

    }
}
