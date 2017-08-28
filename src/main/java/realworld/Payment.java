package realworld;

public class Payment {
    private AgreementExternal agreementExternal;
    private AgreementExternal.Key standingOrderKey;

    public void setAgreementExternal(AgreementExternal agreementExternal) {
        this.agreementExternal = agreementExternal;
    }

    public AgreementExternal getAgreementExternal() {
        return agreementExternal;
    }

    public void setAgreementExternalKey(AgreementExternal.Key standingOrderKey) {
        this.standingOrderKey = standingOrderKey;
    }

    public AgreementExternal.Key getStandingOrderKey() {
        return standingOrderKey;
    }
}
