package realworld;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static realworld.Operation.DELETE;
import static realworld.Operation.UPDATE;
import static realworld.Operation.VIEW;

public class MapperTest
{
    private DTOMapper mapper = new DTOMapper();

    /*TODO: user group assertion for this test*/

    @Test
    public void testMap() throws Exception
    {
        AgreementDTO output = mapper.map(createAgreementExternal());

        assertThat(output.id, is(123L));
        assertThat(output.version, is(456L));
        assertThat(output.agreementType, is(AgreementType.PERIODIC));
        assertThat(output.recurrenceType, is(AgreementRecurrence.MONTHLY.toString()));

        assertThat(output.firstDueDate, is("15.04.2016"));
        assertThat(output.endDate, is("15.06.2017"));
        assertThat(output.exemptions.size(), is(1));
        assertThat(output.exemptions.get(0).startDate, is("05.05"));
        assertThat(output.exemptions.get(0).endDate, is("07.05"));

        assertTrue(output.operations.contains(VIEW));
        assertTrue(output.operations.contains(UPDATE));
        assertTrue(output.operations.contains(DELETE));
    }

    @Test
    public void testMapBetalingToStandingOrder() throws Exception
    {
        ForeignPaymentDTO dto = new ForeignPaymentDTO();
        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.id = 123L;
        agreementDTO.agreementType = AgreementType.PERIODIC;
        agreementDTO.recurrenceType = AgreementRecurrence.DAILY.toString();
        agreementDTO.firstDueDate = "01.02.2016";
        agreementDTO.status = AgreementStatus.ACTIVE;

        AgreementExemption exemption = new AgreementExemption();
        exemption.startDate = "01.07";
        exemption.endDate = "01.08";
        agreementDTO.exemptions.add(exemption);
        dto.setStandingOrder(agreementDTO);

        AgreementExternal agreementExternal = mapper.mapPaymentToAgreement(dto);

        assertThat(agreementExternal.getAgreementId(), is(123L));
        assertThat(agreementExternal.getType(), is(AgreementType.PERIODIC));
        assertThat(agreementExternal.getStatus(), is(AgreementStatus.ACTIVE));
        assertThat(agreementExternal.getRecurrence(), is(AgreementRecurrence.DAILY));
        assertThat(agreementExternal.getDueDates().get(0), is(LocalDate.of(2016, 2, 1)));
        assertThat(agreementExternal.getExemptions().get(0).startDate.getMonthValue(), is(7));
        assertThat(agreementExternal.getExemptions().get(0).startDate.getDayOfMonth(), is(1));
        assertThat(agreementExternal.getExemptions().get(0).endDate.getMonthValue(), is(8));
        assertThat(agreementExternal.getExemptions().get(0).endDate.getDayOfMonth(), is(1));

    }

    @Test
    public void testMapKey() throws Exception
    {
        PaymentSO paymentSO = new PaymentSO();
        paymentSO.setAgreementExternal(createAgreementExternal());
        paymentSO.setAgreementExternalKey(createAgreementExternal().getKey());
        AgreementDTOReference dtoReference = mapper.mapReference(paymentSO);

        assertThat(dtoReference.standingOrderId, is(123L));
        assertThat(dtoReference.version, is(456L));
        assertThat(dtoReference.status, is(AgreementStatus.ACTIVE));
    }

    protected AgreementExternal createAgreementExternal()
    {
        AgreementExternal agreementExternal = new AgreementExternal(123L, 456L);
        agreementExternal.setType(AgreementType.PERIODIC);
        agreementExternal.setRecurrence(AgreementRecurrence.MONTHLY);
        agreementExternal.setDueDates(Collections.singletonList(LocalDate.of(2016, 4, 15)));
        agreementExternal.setEndDate(LocalDate.of(2017, 6, 15));
        agreementExternal.setExemptions(createExemptions());
        agreementExternal.setStatus(AgreementStatus.ACTIVE);
        List<OperationExternal> operations = new ArrayList<>();
        operations.add(OperationExternal.VIEW_EXTERNAL);
        operations.add(OperationExternal.UPDATE_EXTERNAL);
        operations.add(OperationExternal.DELETE_EXTERNAL);
        agreementExternal.setOperations(operations);
        return agreementExternal;
    }

    private List<ExemptionExternal> createExemptions() {
        ExemptionExternal exemption = new ExemptionExternal();
        exemption.startDate = LocalDate.of(2017, 5, 5);
        exemption.endDate = LocalDate.of(2017, 5, 7);
        return Collections.singletonList(exemption);
    }
}
