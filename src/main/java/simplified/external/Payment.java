package simplified.external;

public class Payment {
    private long id;
    private PaymentType paymentType;
    private Agreement agreement;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public Agreement getAgreement() {
        return agreement;
    }
}
