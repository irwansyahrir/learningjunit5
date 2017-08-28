package realworld;

public class ForeignPaymentDTO {
    private AgreementDTO standingOrder;

    public void setStandingOrder(AgreementDTO standingOrder) {
        this.standingOrder = standingOrder;
    }

    public AgreementDTO getStandingOrder() {
        return standingOrder;
    }
}
