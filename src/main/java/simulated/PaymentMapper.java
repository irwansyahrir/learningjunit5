package simulated;


import simulated.domain.DomesticPaymentDTO;
import simulated.domain.DtoWithId;
import simulated.domain.ForeignPaymentDTO;
import simulated.domain.TransferDTO;
import simulated.external.Payment;

public class PaymentMapper {

    DtoWithId map(Payment payment) {
        switch (payment.getPaymentType()) {
            case FOREIGN:
                return mapForeignPayment(payment);
            case DOMESTIC:
                return mapDomesticPayment(payment);
            case TRANSFER:
                return mapTransfer(payment);
            default:
                throw new IllegalArgumentException("Illegal payment type: " + payment.getPaymentType());
        }
    }

    private DtoWithId mapTransfer(Payment payment) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.transferId = Long.toString(payment.getId());
        return transferDTO;
    }

    private DtoWithId mapDomesticPayment(Payment payment) {
        DomesticPaymentDTO domesticPaymentDTO = new DomesticPaymentDTO();
        domesticPaymentDTO.domesticPaymentId = Long.toString(payment.getId());
        return domesticPaymentDTO;
    }

    private DtoWithId mapForeignPayment(Payment payment) {
        ForeignPaymentDTO foreignPaymentDTO = new ForeignPaymentDTO();
        foreignPaymentDTO.foreignPaymentId = Long.toString(payment.getId());
        return foreignPaymentDTO;
    }
}
