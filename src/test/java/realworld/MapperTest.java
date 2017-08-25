package realworld;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static realworld.StandingOrderOperation.DELETE;
import static realworld.StandingOrderOperation.UPDATE;
import static realworld.StandingOrderOperation.VIEW;

public class MapperTest
{
    private StandingOrderDTOMapper mapper = new StandingOrderDTOMapper();

    /*TODO: user group assertion for this test*/

    @Test
    public void testMap() throws Exception
    {
        StandingOrderDTO output = mapper.map(createStandingOrderItem());

        assertThat(output.standingOrderId, is(123L));
        assertThat(output.version, is(456L));
        assertThat(output.standingOrderType, is(StandingOrderType.PERIODIC));
        assertThat(output.recurrenceType, is(StandingOrderRecurrence.MONTHLY.toString()));

        assertThat(output.firstDueDate, is("15.04.2016"));
        assertThat(output.endDate, is("15.04.2017"));
        assertThat(output.exemptions.size(), is(1));
        assertThat(output.exemptions.get(0).startDate, is("15.06"));
        assertThat(output.exemptions.get(0).endDate, is("15.07"));

        assertTrue(output.operations.contains(VIEW));
        assertTrue(output.operations.contains(UPDATE));
        assertTrue(output.operations.contains(DELETE));
    }

    @Test
    public void testMapBetalingToStandingOrder() throws Exception
    {
        ForeignPaymentDTO dto = new ForeignPaymentDTO();
        StandingOrderDTO standingOrder = new StandingOrderDTO();
        standingOrder.standingOrderId = 123L;
        standingOrder.standingOrderType = sb1.rest.bm.betaling.standingorder.StandingOrderType.PERIODIC;
        standingOrder.recurrenceType = StandingOrderRecurrence.DAILY.toString();
        standingOrder.firstDueDate = "01.02.2016";
        standingOrder.status = sb1.rest.bm.betaling.standingorder.StandingOrderStatus.ACTIVE;

        StandingOrderDTO.Exemption exemption = new StandingOrderDTO.Exemption();
        exemption.startDate = "01.07";
        exemption.endDate = "01.08";
        standingOrder.exemptions.add(exemption);
        dto.setStandingOrder(standingOrder);

        StandingOrderAgreement betaling = mapper.mapBetalingToStandingOrder(dto);

        assertThat(betaling.getAgreementId(), is(123L));
        assertThat(betaling.getType(), is(StandingOrderType.PERIODIC));
        assertThat(betaling.getStatus(), is(StandingOrderStatus.ACTIVE));
        assertThat(betaling.getRecurrence(), is(StandingOrderRecurrence.DAILY));
        assertThat(betaling.getDueDates().get(0), is(new LocalDate("2016-02-01")));
        assertThat(betaling.getExemptions().get(0).startDate.getMonthValue(), is(7));
        assertThat(betaling.getExemptions().get(0).startDate.getDayOfMonth(), is(1));
        assertThat(betaling.getExemptions().get(0).endDate.getMonthValue(), is(8));
        assertThat(betaling.getExemptions().get(0).endDate.getDayOfMonth(), is(1));

    }

    @Test
    public void testMapKey() throws Exception
    {
        Payment payment = new Payment();
        payment.setStandingOrder(createStandingOrderItem());
        payment.setStandingOrderKey(createStandingOrderItem().getKey());
        StandingOrderDTOReference dtoReference = mapper.mapReference(payment);

        assertThat(dtoReference.standingOrderId, is(123L));
        assertThat(dtoReference.version, is(456L));
        assertThat(dtoReference.status, is(StandingOrderStatus.ACTIVE));
    }

    protected StandingOrderAgreement createStandingOrderItem()
    {
        StandingOrderAgreement soItem = new StandingOrderAgreement(123L, 456L);
        soItem.setType(StandingOrderType.PERIODIC);
        soItem.setRecurrence(StandingOrderRecurrence.MONTHLY);
        soItem.setDueDates(Collections.singletonList(new org.joda.time.LocalDate("2016-04-15")));
        soItem.setEndDate(new org.joda.time.LocalDate("2017-04-15"));
        soItem.setExemptions(createExemptions());
        soItem.setStatus(StandingOrderStatus.ACTIVE);
        soItem.setOperations(new HashSet<>(Arrays.asList(VIEW, UPDATE, DELETE)));
        return soItem;
    }
}
