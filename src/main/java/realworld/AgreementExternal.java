package realworld;

import java.time.LocalDate;
import java.util.List;

public class AgreementExternal {
    private AgreementType type;
    private AgreementRecurrence recurrence;
    private List<LocalDate> dueDates;
    private LocalDate endDate;
    private AgreementStatus status;
    private Long agreementId;
    private List<ExemptionExternal> exemptions;
    private List<OperationExternal> operations;
    private Key key;

    public AgreementExternal(long id, long version) {
        this.key = new Key(id, version);
    }

    public void setType(AgreementType type) {
        this.type = type;
    }

    public AgreementType getType() {
        return type;
    }

    public void setRecurrence(AgreementRecurrence recurrence) {
        this.recurrence = recurrence;
    }

    public AgreementRecurrence getRecurrence() {
        return recurrence;
    }

    public void setDueDates(List<java.time.LocalDate> dueDates) {
        this.dueDates = dueDates;
    }

    public List<LocalDate> getDueDates() {
        return dueDates;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public List<ExemptionExternal> getExemptions() {
        return exemptions;
    }

    public void setExemptions(List<ExemptionExternal> exemptions) {
        this.exemptions = exemptions;
    }


    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setOperations(List<OperationExternal> operations) {
        this.operations = operations;
    }

    public List<OperationExternal> getOperations() {
        return operations;
    }


    class Key {
        private final long version;
        private final long id;

        public Key(long id, long version) {
            this.id = id;
            this.version = version;
        }

        public long getVersion() {
            return version;
        }

        public long getId() {
            return id;
        }
    }
}
