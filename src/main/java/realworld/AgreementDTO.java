package realworld;

import java.util.ArrayList;
import java.util.List;

public class AgreementDTO {

    public AgreementDTO() {
        exemptions = new ArrayList<>();
        operations = new ArrayList<>();
    }

    public long id;
    public Long version;
    public AgreementType agreementType;
    public String recurrenceType;
    public String firstDueDate;
    public String endDate;
    public List<AgreementExemption> exemptions;
    public List<Operation> operations;
    public AgreementStatus status;

}
