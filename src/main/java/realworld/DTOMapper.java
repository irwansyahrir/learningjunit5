package realworld;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DTOMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public AgreementDTO map(AgreementExternal agreementExternal) {
        AgreementDTO dto = new AgreementDTO();
        dto.id = agreementExternal.getKey().getId();
        dto.version = agreementExternal.getKey().getVersion();
        dto.agreementType = agreementExternal.getType();
        dto.recurrenceType = agreementExternal.getRecurrence().toString();
        dto.firstDueDate = agreementExternal.getDueDates().get(0).format(formatter);
        dto.endDate = agreementExternal.getEndDate().format(formatter);
        dto.exemptions = mapExemptions(agreementExternal.getExemptions());
        dto.operations = mapOperation(agreementExternal.getOperations());
        return dto;
    }

    private List<Operation> mapOperation(List<OperationExternal> operationExternals) {
        List<Operation> operations = new ArrayList<>();

        for (OperationExternal ox : operationExternals) {
            switch (ox) {
                case DELETE_EXTERNAL:
                    operations.add(Operation.DELETE);
                    break;
                case UPDATE_EXTERNAL:
                    operations.add(Operation.UPDATE);
                    break;
                case VIEW_EXTERNAL:
                    operations.add(Operation.VIEW);
                    break;
            }
        }
        return operations;
    }

    private List<AgreementExemption> mapExemptions(List<ExemptionExternal> exemptions) {
        DateTimeFormatter exemptionDateFormmater = DateTimeFormatter.ofPattern("dd.MM");
        List<AgreementExemption> agreementExemptions = new ArrayList<>();
        for (ExemptionExternal ex : exemptions) {
            AgreementExemption exemption = new AgreementExemption();
            exemption.startDate = ex.startDate.format(exemptionDateFormmater);
            exemption.endDate = ex.endDate.format(exemptionDateFormmater);
            agreementExemptions.add(exemption);
        }
        return agreementExemptions;
    }

    public AgreementExternal mapPaymentToAgreement(ForeignPaymentDTO dto) {
        return null;
    }

    public AgreementDTOReference mapReference(Payment payment) {
        return null;
    }
}
