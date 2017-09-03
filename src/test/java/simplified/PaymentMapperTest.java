package simplified;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import simplified.domain.DomesticPaymentDTO;
import simplified.domain.DtoWithId;
import simplified.domain.ForeignPaymentDTO;
import simplified.domain.TransferDTO;
import simplified.external.Payment;
import simplified.external.PaymentType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

@Tag("original")
@DisplayName("Payment Without Agreement")
public class PaymentMapperTest {
    private static final long PAYMENT_ID_LONG = 123L;
    private static final String DTO_ID_STRING = "123";

    PaymentMapper mapper = new PaymentMapper();

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(PAYMENT_ID_LONG);
    }

    @Test
    void testMapForeignPayment() {
        payment.setPaymentType(PaymentType.FOREIGN);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((ForeignPaymentDTO) dto).foreignPaymentId);
    }

    @Test
    void testMapDomesticPayment() {
        payment.setPaymentType(PaymentType.DOMESTIC);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((DomesticPaymentDTO)dto).domesticPaymentId);
    }

    @Test
    void testMapTransfer() {
        payment.setPaymentType(PaymentType.TRANSFER);
        DtoWithId dto = mapper.map(payment);
        assertEquals(DTO_ID_STRING, ((TransferDTO) dto).transferId);
    }


    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void testPayments(PaymentType paymentType) {
        payment.setPaymentType(paymentType);

        assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals(DTO_ID_STRING, ((ForeignPaymentDTO)dto).foreignPaymentId);
        });

        assumingThat(PaymentType.DOMESTIC.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals(DTO_ID_STRING, ((DomesticPaymentDTO)dto).domesticPaymentId);
        });

        assumingThat(PaymentType.TRANSFER.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals(DTO_ID_STRING, ((TransferDTO)dto).transferId);
        });

        assumingThat(PaymentType.ILLEGAL.equals(payment.getPaymentType()),
                () -> assertThrows(IllegalArgumentException.class, () -> mapper.map(payment)));
    }

    @ParameterizedTest
    @EnumSource(value = PaymentType.class, mode = EXCLUDE, names = {"ILLEGAL"})
    void testLegalPaymentsOnly(PaymentType paymentType) {
        payment.setPaymentType(paymentType);

        DtoWithId dto = mapper.map(payment);

        assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()),
                () -> assertEquals(DTO_ID_STRING, ((ForeignPaymentDTO)dto).foreignPaymentId));

        assumingThat(PaymentType.DOMESTIC.equals(payment.getPaymentType()),
                () -> assertEquals(DTO_ID_STRING, ((DomesticPaymentDTO)dto).domesticPaymentId));

        assumingThat(PaymentType.TRANSFER.equals(payment.getPaymentType()),
                () -> assertEquals(DTO_ID_STRING, ((TransferDTO)dto).transferId));
    }

    @Test
    void testMappingIllegalPaymentType() {
        payment.setPaymentType(PaymentType.ILLEGAL);
        assertThrows(IllegalArgumentException.class, () -> mapper.map(payment));
    }

    @TestFactory
    Stream<DynamicTest> testDynamicMapping() {
        return Stream.of(PaymentType.values())
                .map(type -> DynamicTest.dynamicTest("The type is " + type.toString(),
                        () -> {
                            payment.setPaymentType(type);

                            assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()), () -> {
                                DtoWithId dto = mapper.map(payment);
                                assertEquals(DTO_ID_STRING, ((ForeignPaymentDTO) dto).foreignPaymentId);
                            });

                            assumingThat(PaymentType.TRANSFER.equals(payment.getPaymentType()), () -> {
                                DtoWithId dto = mapper.map(payment);
                                assertEquals(DTO_ID_STRING, ((TransferDTO) dto).transferId);
                            });

                            assumingThat(PaymentType.DOMESTIC.equals(payment.getPaymentType()), () -> {
                                DtoWithId dto = mapper.map(payment);
                                assertEquals(DTO_ID_STRING, ((DomesticPaymentDTO) dto).domesticPaymentId);
                            });

                            assumingThat(PaymentType.ILLEGAL.equals(payment.getPaymentType()), () ->
                                    assertThrows(IllegalArgumentException.class, () -> mapper.map(payment)));

                        })
                );
    }
}
