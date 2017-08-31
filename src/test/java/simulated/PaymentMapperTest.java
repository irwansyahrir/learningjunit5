package simulated;

import org.junit.jupiter.api.Test;
import simulated.domain.DomesticPaymentDTO;
import simulated.domain.DtoWithId;
import simulated.domain.ForeignPaymentDTO;
import simulated.domain.TransferDTO;
import simulated.external.Payment;
import simulated.external.PaymentType;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentMapperTest {

    PaymentMapper mapper = new PaymentMapper();

    @Test
    void testMapForeignPayment() {
        Payment foreignPayment = new Payment();
        foreignPayment.setId(123L);
        foreignPayment.setPaymentType(PaymentType.FOREIGN_WHATEVER_ONE);

        DtoWithId dto = mapper.map(foreignPayment);

        assertEquals("123", ((ForeignPaymentDTO) dto).foreignPaymentId);
    }


    @Test
    void testMapDomesticPayment() {
        Payment domesticPayment = new Payment();
        domesticPayment.setId(456L);
        domesticPayment.setPaymentType(PaymentType.DOMESTIC_WHATERVER_TWO);

        DtoWithId dto = mapper.map(domesticPayment);

        assertEquals("456", ((DomesticPaymentDTO)dto).domesticPaymentId);
    }

    @Test
    void testMapTransfer() {
        Payment transfer = new Payment();
        transfer.setId(789L);
        transfer.setPaymentType(PaymentType.TRANSFER_123_OUT_CLEARING);

        DtoWithId dto = mapper.map(transfer);

        assertEquals("789", ((TransferDTO) dto).transferId);
    }


    //TODO parameterized those tests. Parameter : test data
}
