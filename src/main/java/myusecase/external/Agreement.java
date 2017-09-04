package myusecase.external;

import myusecase.Periode;

import java.time.LocalDate;

public class Agreement {
    private long agreementId;
    private Periode periode;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setAgreementId(long agreementId) {
        this.agreementId = agreementId;
    }

    public long getAgreementId() {
        return agreementId;
    }
}
