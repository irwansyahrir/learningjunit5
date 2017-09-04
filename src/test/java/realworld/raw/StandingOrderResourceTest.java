//TODO: find some test scenario here
//
//package realworld.raw;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static sb1.biz.payment.standingorder.StandingOrderStatus.ACTIVE;
//import static sb1.biz.payment.standingorder.StandingOrderStatus.CLOSED;
//import static sb1.biz.payment.standingorder.StandingOrderStatus.PENDING_APPROVAL;
//import static sb1.biz.payment.standingorder.StandingOrderStatus.VALIDATED;
//import static sb1.rest.bm.betaling.common.PaymentType.INTERNATIONAL_PAYMENT;
//import static sb1.rest.bm.betaling.common.PaymentType.TRANSFER;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.Response;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.MockitoJUnitRunner;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class StandingOrderResourceTest extends AbstractAuditHelperTestBase {
//    public static final int NO_CONTENT = Response.Status.NO_CONTENT.getStatusCode();
//    public static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();
//    public static final int OK = Response.Status.OK.getStatusCode();
//    public static final int PRECONDITION_FAILED = Response.Status.PRECONDITION_FAILED.getStatusCode();
//    public static final int INTERNAL_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
//
//    @InjectMocks
//    StandingOrderResource resource;
//
//    @Mock
//    StandingOrderService standingOrderService;
//
//    UserContext userContext = mock(UserContext.class);
//    OCRRegistryHelper ocrRegistryHelper = mock(OCRRegistryHelper.class);
//
//    @InjectMocks
//    @Spy
//    ForeignPaymentDTOMapper foreignPaymentDTOMapper;
//
//    @Spy
//    TransferDTOMapper transferDTOMapper;
//
//    @Spy
//    DomesticPaymentDTOMapper domesticPaymentDTOMapper;
//
//    @Spy
//    DomesticPaymentMapper domesticPaymentMapper = new DomesticPaymentMapper(ocrRegistryHelper);
//
//    @Mock
//    RegisteredPaymentsModel registeredPaymentsModel;
//
//    @Mock
//    AccountInfoDecorator accountInfoDecorator;
//
//    @Mock
//    StandingOrdersLinkDecorator standingOrdersLinkDecorator;
//
//    @Mock
//    RegulatoryCodeDecorator regulatoryCodeDecorator;
//
//    @Mock
//    CalculatedNOKAmountDecorator calculatedNOKAmountDecorator;
//
//    @Mock
//    BicInfoService bicInfoService;
//
//    @Mock
//    private StandingOrderAuditHelper auditHelper;
//
//    @Mock
//    private StandingOrderValidator standingOrderValidator;
//
//    @Mock
//    private SigningAuthDataMapper signingAuthDataMapper;
//
//    @Mock
//    private HttpServletRequest httpRequest;
//
//    @Mock
//    private ApproveSigningService approveSigningService;
//
//    @Mock
//    private SigningErrorResponder signingErrorResponder;
//
//    @Mock
//    private InitiatorNameDecorator paymentInfoDecorator;
//
//    public static final Long STANDING_ORDER_ID = 1L;
//    public static final Long VERSION = 1L;
//
//    @Test
//    public void testGetStandingOrders() throws Exception {
//        List<Payment> paymentList = new ArrayList<>();
//        paymentList.add(createPayment(PaymentType.INTERNATIONAL_PAYMENT));
//        paymentList.add(createPayment(PaymentType.TRANSFER));
//        paymentList.add(createPayment(PaymentType.DOMESTIC_APPROPRIATION));
//
//        Map<Long, List<Payment>> paymentMap = new HashMap<>();
//        paymentMap.put(STANDING_ORDER_ID, paymentList);
//        when(standingOrderService.searchStandingOrder()).thenReturn(paymentMap);
//
//        CollectionResponse response = resource.getStandingOrders(null, null);
//
//        List<StandingOrderMultiVersionDTO> standingOrders = (List<StandingOrderMultiVersionDTO>) response.data;
//        assertThat(standingOrders.size(), is(1));
//        assertThat(standingOrders.get(0).standingOrderId, is(STANDING_ORDER_ID));
//        assertThat(standingOrders.get(0).versions.size(), is(3));
//    }
//
//    @Test
//    public void testGetOneStandingOrder() throws Exception {
//        when(standingOrderService.getStandingOrder(any(StandingOrderKey.class)))
//                .thenReturn(createPayment(PaymentType.INTERNATIONAL_PAYMENT));
//
//        CollectionResponse response = resource.getStandingOrders(1L, 2L);
//
//        assertThat(response.data.size(), is(1));
//    }
//
//    @Test
//    public void testGetOneStandingOrderWithIncompleteParamsReturnsSearchAll() throws Exception {
//        List<Payment> paymentList = new ArrayList<>();
//        paymentList.add(createPayment(PaymentType.INTERNATIONAL_PAYMENT));
//        paymentList.add(createPayment(PaymentType.TRANSFER));
//
//        Map<Long, List<Payment>> paymentMap = new HashMap<>();
//        paymentMap.put(STANDING_ORDER_ID, paymentList);
//        when(standingOrderService.searchStandingOrder()).thenReturn(paymentMap);
//
//        CollectionResponse response = resource.getStandingOrders(STANDING_ORDER_ID, null);
//
//        final Collection standingOrders = response.data;
//        assertThat(standingOrders.size(), is(1));
//
//        final StandingOrderMultiVersionDTO dto = (StandingOrderMultiVersionDTO) standingOrders.iterator().next();
//        assertThat(dto.standingOrderId, is(STANDING_ORDER_ID));
//        assertThat(dto.versions.size(), is(2));
//    }
//
//    @Test
//    public void testGetStandingOrdersForApproval() throws Exception {
//        List<Payment> paymentList = new ArrayList<>();
//        paymentList.add(createPayment(PaymentType.INTERNATIONAL_PAYMENT));
//
//        when(standingOrderService.searchStandingOrderForApproval()).thenReturn(paymentList);
//
//        CollectionResponse response = resource.getStandingOrdersForApproval();
//
//        assertThat(response.data.size(), is(1));
//    }
//
//    @Test
//    public void testUpdateStandingOrderForeignPayment() throws Exception {
//        ForeignPaymentDTO foreignPaymentDto = new ForeignPaymentDTO();
//        foreignPaymentDto.setPaymentID(1L);
//        foreignPaymentDto.setInvoiceCurrency("NOK");
//        foreignPaymentDto.setDueDate("01.06.2016");
//        foreignPaymentDto.setRecipient(createForeignRecipientDTO());
//        StandingOrderDTO standingOrderDto = createStandingOrderDto();
//        foreignPaymentDto.setStandingOrder(standingOrderDto);
//
//
//        StandingOrderKey newKey = new StandingOrderKey(STANDING_ORDER_ID, 10L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(PENDING_APPROVAL));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(ForeignPaymentDTO.class))).thenReturn(Respond.badRequest()); // No errors
//
//        ForeignPaymentDTO response = (ForeignPaymentDTO) resource.updateStandingOrderForeignPayment(foreignPaymentDto).getEntity();
//
//        assertThat(response.getStandingOrder().standingOrderId, is(STANDING_ORDER_ID));
//        assertThat(response.getStandingOrder().version, is(10L));
//
//        verify(auditHelper, times(1)).update(standingOrderDto, INTERNATIONAL_PAYMENT);
//        verify(auditHelper, times(1)).grant();
//
//        verify(bicInfoService).setBicInfo(any(ForeignPaymentDTO.class), any(Payment.class));
//    }
//
//    @Test
//    public void testUpdateStandingOrderForeignPaymentWithError() throws Exception {
//        ForeignPaymentDTO foreignPaymentDto = new ForeignPaymentDTO();
//        foreignPaymentDto.setInvoiceCurrency("NOK");
//        foreignPaymentDto.setDueDate("01.06.2016");
//        foreignPaymentDto.setRecipient(createForeignRecipientDTO());
//        foreignPaymentDto.setStandingOrder(createStandingOrderDto());
//
//
//        StandingOrderKey newKey = new StandingOrderKey(STANDING_ORDER_ID, 10L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(VALIDATED));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(ForeignPaymentDTO.class))).thenReturn(Respond.badRequest()); // No errors
//        Response response = resource.updateStandingOrderForeignPayment(foreignPaymentDto);
//
//        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR));
//        assertThat(response.getEntity(), is("Failed to confirm changes"));
//
//        verify(auditHelper, times(1)).update(foreignPaymentDto.getStandingOrder(), INTERNATIONAL_PAYMENT);
//        verify(auditHelper, times(1)).transactionalDeny();
//    }
//
//    @Test
//    public void testUpdateStandingOrderTransfer() throws Exception {
//        TransferDTO transferDto = new TransferDTO();
//        transferDto.invoiceCurrency = "NOK";
//        transferDto.dueDate = "01.06.2016";
//        transferDto.standingOrder = createStandingOrderDto();
//
//
//        StandingOrderKey newKey = new StandingOrderKey(STANDING_ORDER_ID, 20L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(PENDING_APPROVAL));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(TransferDTO.class))).thenReturn(Respond.badRequest()); // No errors
//
//        TransferDTO updatedDto = (TransferDTO) resource.updateStandingOrderTransfer(transferDto).getEntity();
//
//        assertThat(updatedDto.standingOrder.standingOrderId, is(STANDING_ORDER_ID));
//        assertThat(updatedDto.standingOrder.version, is(20L));
//
//        verify(auditHelper, times(1)).update(transferDto.standingOrder, TRANSFER);
//        verify(auditHelper, times(1)).grant();
//
//        verify(bicInfoService).setBicInfo(any(TransferDTO.class), any(Payment.class));
//    }
//
//    @Test
//    public void testUpdateStandingOrderTransferWithError() throws Exception {
//        TransferDTO transferDto = new TransferDTO();
//        transferDto.invoiceCurrency = "NOK";
//        transferDto.dueDate = "01.06.2016";
//        transferDto.standingOrder = createStandingOrderDto();
//
//
//        StandingOrderKey newKey = new StandingOrderKey(2L, 20L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(VALIDATED));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(TransferDTO.class))).thenReturn(Respond.badRequest()); // No errors
//
//        Response response = resource.updateStandingOrderTransfer(transferDto);
//
//        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR));
//        assertThat(response.getEntity(), is("Failed to confirm changes"));
//
//        verify(auditHelper, times(1)).update(transferDto.standingOrder, TRANSFER);
//        verify(auditHelper, times(1)).transactionalDeny();
//    }
//
//    @Test
//    public void testUpdateStandingOrderDomesticPayment() throws Exception {
//        DomesticPaymentDTO domesticPayment = new DomesticPaymentDTO();
//        domesticPayment.paymentType = PaymentType.DOMESTIC_CID_OR_MESSAGE;
//        domesticPayment.standingOrder = createStandingOrderDto();
//        domesticPayment.fromAccount = "fromAccount";
//        domesticPayment.amount = new BigDecimal(10);
//        domesticPayment.internalNote = "internalNote";
//        domesticPayment.confidential = false;
//        domesticPayment.recipient = createDomesticRecipient();
//        domesticPayment.dueDate = "01.07.2016";
//        domesticPayment.message = "message";
//
//
//        StandingOrderKey newKey = new StandingOrderKey(STANDING_ORDER_ID, 30L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(PENDING_APPROVAL));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(DomesticPaymentDTO.class))).thenReturn(Respond.badRequest()); // No errors
//
//        DomesticPaymentDTO response = (DomesticPaymentDTO) resource.updateStandingOrderDomesticPayment(domesticPayment).getEntity();
//
//        assertThat(response.standingOrder.standingOrderId, is(STANDING_ORDER_ID));
//        assertThat(response.standingOrder.version, is(30L));
//
//        verify(auditHelper, times(1)).update(domesticPayment.standingOrder, domesticPayment.paymentType);
//        verify(auditHelper, times(1)).grant();
//    }
//
//
//    @Test
//    public void testUpdateStandingOrderDomesticPaymentWithError() throws Exception {
//        DomesticPaymentDTO domesticPayment = new DomesticPaymentDTO();
//        domesticPayment.paymentType = PaymentType.DOMESTIC_CID_OR_MESSAGE;
//        domesticPayment.standingOrder = createStandingOrderDto();
//        domesticPayment.fromAccount = "fromAccount";
//        domesticPayment.amount = new BigDecimal(10);
//        domesticPayment.internalNote = "internalNote";
//        domesticPayment.confidential = false;
//        domesticPayment.recipient = createDomesticRecipient();
//        domesticPayment.dueDate = "01.07.2016";
//        domesticPayment.message = "message";
//
//
//        StandingOrderKey newKey = new StandingOrderKey(3L, 30L);
//        when(standingOrderService.updateStandingOrderStatus(any(StandingOrderUpdateStatusRequest.class))).thenReturn(createStatusResponse(VALIDATED));
//
//        StandingOrderResponse standingOrderResponse = new StandingOrderResponse();
//        standingOrderResponse.setKey(newKey);
//        when(standingOrderService.updateStandingOrder(any(Payment.class))).thenReturn(standingOrderResponse);
//
//        when(standingOrderValidator.validate(any(DomesticPaymentDTO.class))).thenReturn(Respond.badRequest()); // No errors
//
//        Response response = resource.updateStandingOrderDomesticPayment(domesticPayment);
//
//        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR));
//        assertThat(response.getEntity(), is("Failed to confirm changes"));
//
//        verify(auditHelper, times(1)).update(domesticPayment.standingOrder, domesticPayment.paymentType);
//        verify(auditHelper, times(1)).transactionalDeny();
//    }
//
//    @Test
//    public void testDeleteStandingOrderForeignPayment() throws Exception {
//        when(standingOrderService.deleteStandingOrder(any(StandingOrderKey.class))).thenReturn(createStatusResponse(CLOSED));
//
//        Response responseForeignPayment = resource.deleteStandingOrderForeignPayment(STANDING_ORDER_ID, VERSION);
//        assertThat(responseForeignPayment.getStatus(), is(NO_CONTENT));
//
//        verify(auditHelper, times(1)).delete(eq(STANDING_ORDER_ID), eq(VERSION));
//        verify(auditHelper, times(1)).grant();
//        verify(registeredPaymentsModel, times(1)).remove(STANDING_ORDER_ID);
//    }
//
//    @Test
//    public void testDeleteStandingOrderTransfer() {
//        when(standingOrderService.deleteStandingOrder(any(StandingOrderKey.class))).thenReturn(createStatusResponse(CLOSED));
//
//        Response responseTransfer = resource.deleteStandingOrderTransfer(STANDING_ORDER_ID, VERSION);
//        assertThat(responseTransfer.getStatus(), is(NO_CONTENT));
//
//        verify(auditHelper, times(1)).delete(eq(STANDING_ORDER_ID), eq(VERSION));
//        verify(auditHelper, times(1)).grant();
//        verify(registeredPaymentsModel, times(1)).remove(STANDING_ORDER_ID);
//    }
//
//    @Test
//    public void testDeleteStandingOrderDomesticPayment() {
//        when(standingOrderService.deleteStandingOrder(any(StandingOrderKey.class))).thenReturn(createStatusResponse(CLOSED));
//
//        Response responseDomestic = resource.deleteStandingOrderDomesticPayment(STANDING_ORDER_ID, VERSION);
//        assertThat(responseDomestic.getStatus(), is(NO_CONTENT));
//
//        verify(auditHelper, times(1)).delete(eq(STANDING_ORDER_ID), eq(VERSION));
//        verify(auditHelper, times(1)).grant();
//        verify(registeredPaymentsModel, times(1)).remove(STANDING_ORDER_ID);
//    }
//
//    @Test
//    public void testDeleteStandingOrderWithError() throws Exception {
//        when(standingOrderService.deleteStandingOrder(any(StandingOrderKey.class))).thenThrow(new RuntimeException());
//
//        Response responseForeignPayment = resource.deleteStandingOrderForeignPayment(STANDING_ORDER_ID, VERSION);
//        assertThat(responseForeignPayment.getStatus(), is(INTERNAL_SERVER_ERROR));
//
//        Response responseTransfer = resource.deleteStandingOrderTransfer(STANDING_ORDER_ID, VERSION);
//        assertThat(responseTransfer.getStatus(), is(INTERNAL_SERVER_ERROR));
//
//        Response responseDomestic = resource.deleteStandingOrderDomesticPayment(STANDING_ORDER_ID, VERSION);
//        assertThat(responseDomestic.getStatus(), is(INTERNAL_SERVER_ERROR));
//    }
//
//    @Test
//    public void updateStandingOrderForeignPayment_invalidForeignPayment_givesBadRequest() {
//        Respond respond = Respond.badRequest().addErrorMessage(new MessageKey("", ""));
//        when(standingOrderValidator.validate(any(ForeignPaymentDTO.class))).thenReturn(respond);
//
//        Response response = resource.updateStandingOrderForeignPayment(new ForeignPaymentDTO());
//        assertThat(response.getStatus(), is(BAD_REQUEST));
//    }
//
//    @Test
//    public void updateStandingOrderTransfer_invalidTransfer_givesBadRequest() {
//        Respond respond = Respond.badRequest().addErrorMessage(new MessageKey("", ""));
//        when(standingOrderValidator.validate(any(TransferDTO.class))).thenReturn(respond);
//
//        Response response = resource.updateStandingOrderTransfer(new TransferDTO());
//        assertThat(response.getStatus(), is(BAD_REQUEST));
//    }
//
//    @Test
//    public void updateStandingOrderDomesticPayment_invalidDomesticPayment_givesBadRequest() {
//        Respond respond = Respond.badRequest().addErrorMessage(new MessageKey("", ""));
//        when(standingOrderValidator.validate(any(DomesticPaymentDTO.class))).thenReturn(respond);
//
//        Response response = resource.updateStandingOrderDomesticPayment(new DomesticPaymentDTO());
//        assertThat(response.getStatus(), is(BAD_REQUEST));
//    }
//
//    @Test
//    public void approveStandingOrdersWithSuccessfulSigning() throws Exception {
//
//        long id1 = 10L;
//        long id2 = 20L;
//        long version = 1L;
//
//        mockGettingSigningIntent(SigningIntent.POLL);
//
//        Set<StandingOrderKey> standingOrderKeys = new HashSet<>();
//        standingOrderKeys.add(new StandingOrderKey(id1, version));
//        standingOrderKeys.add(new StandingOrderKey(id2, version));
//
//        when(approveSigningService.sign(SigningIntent.POLL, standingOrderKeys))
//                .thenReturn(new SigningStatusResult(SigningStatusResult.Outcome.SIGNING_SUCCESS));
//
//        when(standingOrderService.approveStandingOrders(standingOrderKeys))
//                .thenReturn(updateStatusResponseData(activeStandingOrder(id1, version), activeStandingOrder(id2, version)));
//
//
//        Response response = resource.approve(dtos(dtoData(id1, version), dtoData(id2, version)));
//
//        assertThat(response.getStatus(), is(OK));
//
//        verify(registeredPaymentsModel, times(1)).remove(id1);
//        verify(registeredPaymentsModel, times(1)).remove(id2);
//
//    }
//
//    @Test
//    public void approveStandingOrdersWithErrorSigning() throws Exception {
//
//        long id1 = 10L;
//        long id2 = 20L;
//        long version = 1L;
//
//        mockGettingSigningIntent(SigningIntent.POLL);
//
//        Set<StandingOrderKey> standingOrderKeys = new HashSet<>();
//        standingOrderKeys.add(new StandingOrderKey(id1, version));
//        standingOrderKeys.add(new StandingOrderKey(id2, version));
//
//        when(approveSigningService.sign(SigningIntent.POLL, standingOrderKeys))
//                .thenReturn(new SigningStatusResult(SigningStatusResult.Outcome.ERROR));
//
//
//        resource.approve(dtos(dtoData(id1, version), dtoData(id2, version)));
//
//        verify(signingErrorResponder, times(1)).respond(any());
//
//        verify(registeredPaymentsModel, times(0)).remove(id1);
//        verify(registeredPaymentsModel, times(0)).remove(id2);
//
//    }
//
//
//    @Test
//    public void approveStandingOrdersWithCancelledSigning() throws Exception {
//
//        long id1 = 10L;
//        long id2 = 20L;
//        long version = 1L;
//
//        mockGettingSigningIntent(SigningIntent.POLL);
//
//        Set<StandingOrderKey> standingOrderKeys = new HashSet<>();
//        standingOrderKeys.add(new StandingOrderKey(id1, version));
//        standingOrderKeys.add(new StandingOrderKey(id2, version));
//
//        when(approveSigningService.sign(SigningIntent.POLL, standingOrderKeys))
//                .thenReturn(new SigningStatusResult(SigningStatusResult.Outcome.CANCELLED));
//
//
//        Response response = resource.approve(dtos(dtoData(id1, version), dtoData(id2, version)));
//        assertThat(response.getStatus(), is(PRECONDITION_FAILED));
//
//        verify(registeredPaymentsModel, times(0)).remove(id1);
//        verify(registeredPaymentsModel, times(0)).remove(id2);
//
//    }
//
//    private ApproveStandingOrdersDTO dtos(ApproveStandingOrderDTO... approveStandingOrderDTOs) {
//        ApproveStandingOrdersDTO dtos = new ApproveStandingOrdersDTO();
//
//        for (ApproveStandingOrderDTO a : approveStandingOrderDTOs) {
//            dtos.standingOrderIds.add(a);
//        }
//
//        return dtos;
//    }
//
//    private StandingOrderUpdateStatusResponse updateStatusResponseData(StandingOrderResponse... responses) {
//        StandingOrderUpdateStatusResponse updateStatusResponse = new StandingOrderUpdateStatusResponse();
//
//        for (StandingOrderResponse r : responses) {
//            updateStatusResponse.addResponse(r);
//        }
//
//        return updateStatusResponse;
//    }
//
//    private void mockGettingSigningIntent(SigningIntent intent) {
//        when(signingAuthDataMapper.getSigningIntent(httpRequest)).thenReturn(intent);
//    }
//
//    private StandingOrderResponse activeStandingOrder(Long id, Long version) {
//        StandingOrderResponse response = new StandingOrderResponse();
//        response.setKey(new StandingOrderKey(id, version));
//        response.setStandingOrderStatus(ACTIVE);
//        return response;
//    }
//
//    private ApproveStandingOrderDTO dtoData(Long id, Long version) {
//        ApproveStandingOrderDTO dto = new ApproveStandingOrderDTO();
//        dto.standingOrderId = id;
//        dto.version = version;
//        return dto;
//    }
//
//    @Override
//    protected DtoWithId buildDTO(PaymentType paymentType) {
//        return null;
//    }
//
//    private StandingOrderResponse createStatusResponse(StandingOrderStatus status) {
//        StandingOrderResponse response = new StandingOrderResponse();
//        response.setStandingOrderStatus(status);
//        return response;
//    }
//}
//
