package simulated;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import simulated.domain.DomesticPaymentDTO;
import simulated.domain.DtoWithId;
import simulated.domain.ForeignPaymentDTO;
import simulated.domain.TransferDTO;
import simulated.external.Payment;
import simulated.external.PaymentType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

@Tag("simulated")
public class PaymentMapperTest {

    PaymentMapper mapper = new PaymentMapper();

    @Test
    void testMapForeignPayment() {
        Payment foreignPayment = new Payment();
        foreignPayment.setId(123L);
        foreignPayment.setPaymentType(PaymentType.FOREIGN);

        DtoWithId dto = mapper.map(foreignPayment);

        assertEquals("123", ((ForeignPaymentDTO) dto).foreignPaymentId);
    }


    @Test
    void testMapDomesticPayment() {
        Payment domesticPayment = new Payment();
        domesticPayment.setId(456L);
        domesticPayment.setPaymentType(PaymentType.DOMESTIC);

        DtoWithId dto = mapper.map(domesticPayment);

        assertEquals("456", ((DomesticPaymentDTO)dto).domesticPaymentId);
    }

    @Test
    void testMapTransfer() {
        Payment transfer = new Payment();
        transfer.setId(789L);
        transfer.setPaymentType(PaymentType.TRANSFER);

        DtoWithId dto = mapper.map(transfer);

        assertEquals("789", ((TransferDTO) dto).transferId);
    }


    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void testPayments(PaymentType paymentType) {
        Payment payment = new Payment();
        payment.setId(111L);
        payment.setPaymentType(paymentType);

        assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals("111", ((ForeignPaymentDTO)dto).foreignPaymentId);
        });

        assumingThat(PaymentType.DOMESTIC.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals("111", ((DomesticPaymentDTO)dto).domesticPaymentId);
        });

        assumingThat(PaymentType.TRANSFER.equals(payment.getPaymentType()),() -> {
            DtoWithId dto = mapper.map(payment);
            assertEquals("111", ((TransferDTO)dto).transferId);
        });

        assumingThat(PaymentType.ILLEGAL.equals(payment.getPaymentType()), () -> {
            assertThrows(IllegalArgumentException.class, () -> mapper.map(payment));
        });
    }

    @ParameterizedTest
    @EnumSource(value = PaymentType.class, mode = EXCLUDE, names = {"ILLEGAL"})
    void testLegalPaymentsOnly(PaymentType paymentType) {
        Payment payment = new Payment();
        payment.setId(222L);
        payment.setPaymentType(paymentType);

        DtoWithId dto = mapper.map(payment);

        assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()),
                () -> assertEquals("222", ((ForeignPaymentDTO)dto).foreignPaymentId));

        assumingThat(PaymentType.DOMESTIC.equals(payment.getPaymentType()),
                () -> assertEquals("222", ((DomesticPaymentDTO)dto).domesticPaymentId));

        assumingThat(PaymentType.TRANSFER.equals(payment.getPaymentType()),
                () -> assertEquals("222", ((TransferDTO)dto).transferId));
    }

    @Test
    void testMappingIllegalPaymentType() {
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.ILLEGAL);

        assertThrows(IllegalArgumentException.class, () -> mapper.map(payment));
    }

    @TestFactory
    Stream<DynamicTest> testDynamicMapping() {
        return Stream.of(PaymentType.values())
                .map(type -> DynamicTest.dynamicTest("The type is " + type.toString(),
                        () -> {
                            Payment payment = new Payment();
                            payment.setId(345L);
                            payment.setPaymentType(type);

                            assumingThat(PaymentType.FOREIGN.equals(payment.getPaymentType()),
                                    () -> assertEquals("345", ((ForeignPaymentDTO) mapper.map(payment)).foreignPaymentId));
                        })
                );
    }
}
