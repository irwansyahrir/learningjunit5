package simulated;


import simulated.domain.DomesticPaymentDTO;
import simulated.domain.DtoWithId;
import simulated.domain.ForeignPaymentDTO;
import simulated.domain.TransferDTO;
import simulated.external.Payment;
import simulated.external.PaymentType;

public class PaymentMapper {

    DtoWithId map(Payment payment){

        if (payment.getPaymentType() == PaymentType.FOREIGN_WHATEVER_ONE) {
            ForeignPaymentDTO foreignPaymentDTO = new ForeignPaymentDTO();
            foreignPaymentDTO.foreignPaymentId = Long.toString(payment.getId());
            return foreignPaymentDTO;
        }

        if (payment.getPaymentType() == PaymentType.DOMESTIC_WHATERVER_TWO) {
            DomesticPaymentDTO domesticPaymentDTO = new DomesticPaymentDTO();
            domesticPaymentDTO.domesticPaymentId = Long.toString(payment.getId());
            return domesticPaymentDTO;
        }

        if (payment.getPaymentType() == PaymentType.TRANSFER_123_OUT_CLEARING) {
            TransferDTO transferDTO = new TransferDTO();
            transferDTO.transferId = Long.toString(payment.getId());
            return transferDTO;
        }

        return null;

    }
}
