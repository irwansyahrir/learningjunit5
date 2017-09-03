package simplified;


import simplified.domain.AgreementDTO;
import simplified.domain.DomesticPaymentDTO;
import simplified.domain.DtoWithId;
import simplified.domain.ForeignPaymentDTO;
import simplified.domain.TransferDTO;
import simplified.external.Agreement;
import simplified.external.Payment;

import java.time.format.DateTimeFormatter;

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

        if (payment.getAgreement() != null) {
            transferDTO.agreement = mapAgreement(payment.getAgreement());
        }

        return transferDTO;
    }

    private DtoWithId mapDomesticPayment(Payment payment) {
        DomesticPaymentDTO domesticPaymentDTO = new DomesticPaymentDTO();
        domesticPaymentDTO.domesticPaymentId = Long.toString(payment.getId());

        if (payment.getAgreement() != null) {
            domesticPaymentDTO.agreement = mapAgreement(payment.getAgreement());
        }
        return domesticPaymentDTO;
    }

    private DtoWithId mapForeignPayment(Payment payment) {
        ForeignPaymentDTO foreignPaymentDTO = new ForeignPaymentDTO();
        foreignPaymentDTO.foreignPaymentId = Long.toString(payment.getId());

        if (payment.getAgreement() != null) {
            foreignPaymentDTO.agreement = mapAgreement(payment.getAgreement());
        }
        return foreignPaymentDTO;
    }

    private AgreementDTO mapAgreement(Agreement agreement) {
        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.id = Long.toString(agreement.getAgreementId());
        agreementDTO.periode = agreement.getPeriode().toString();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        agreementDTO.startDate = agreement.getStartDate().format(dateFormatter);
        agreementDTO.endDate = agreement.getEndDate().format(dateFormatter);

        return agreementDTO;
    }

}
