package com.jobfind.stripepaymentservice;

import com.jobfind.converters.CompanyConverter;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.User;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.impl.CompanyServiceImpl;
import com.jobfind.stripepaymentservice.models.CreditCardPaymentInfo;
import com.jobfind.stripepaymentservice.models.Order;
import com.jobfind.stripepaymentservice.models.SubscriptionPlan;
import com.jobfind.stripepaymentservice.models.enums.PaymentStatus;
import com.jobfind.stripepaymentservice.repositories.CreditCardPaymentInfoRepository;
import com.jobfind.stripepaymentservice.repositories.OrderRepository;
import com.jobfind.stripepaymentservice.repositories.SubscriptionPlanRepository;
import com.jobfind.stripepaymentservice.request.OrderRequest;
import com.jobfind.stripepaymentservice.response.OrderResponse;
import com.jobfind.stripepaymentservice.response.PaymentDetailsResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService{
    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${stripe.payment.public.key}")
    private String stripePaymentPublicKey;
    @Value("${stripe.payment.secret.key}")
    private String stripePaymentSecretKey;
    @Value("${stripe.payment.retrieve.time}")
    private int STRIPE_TOTAL_RETRIEVE_TIME;
    @Value("${stripe.payment.cancel.time}")
    private int STRIPE_CANCEL_TIME;

    private final String STRIPE_PAYMENT_MOBILE_NUMBER = "mobileNumber";
    private final String STRIPE_PAYMENT_USER_ID = "userID";
    private final String STRIPE_PAYMENT_ORDER_CODE ="orderCode";
    private final String STRIPE_PAYMENT_META_DATA= "metadata";
    public  final String INTENT_UNCAPTURED = "requires_capture";
    private final String INTENT_CANCEL = "canceled";
    private final String CARD_TYPE = "MASTER_CARD";
    public final String STRIPE_PAYMENT_CARD_PREFIX = "*********";
    public final String STRIPE_PAYMENT_AMOUNT = "amount";
    private final String INTENT_CAPTURE_ERROR_MESSAGE = "Intent is Uncapture";
    private final String PAYMENT_AMOUNT_NOT_EQUAL = "PAYMENT_AMOUNT_NOT_EQUAL@@Total amount in cart is not equal to Stripe payment amount";
    private final String FILTER_VALUE_CHARACTER = "'";
    private final OrderRepository orderRepository;
    private final CreditCardPaymentInfoRepository cardPaymentInfoRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final CompanyConverter companyConverter;

    @Override
    public void updatePaymentIntentForOrderFirst(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();
        Map<String, Object> params = new HashMap<>();
        if(order != null){
            Map<String, Object> metaDataParams = new HashMap<>();
            metaDataParams.put(STRIPE_PAYMENT_MOBILE_NUMBER, order.getUser().getPhone());
            Integer userID = order.getUser().getUserId();
            metaDataParams.put(STRIPE_PAYMENT_USER_ID, userID);
            metaDataParams.put(STRIPE_PAYMENT_ORDER_CODE, order.getOrderId());
            params.put(STRIPE_PAYMENT_META_DATA, metaDataParams);
        }
        PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
        if (paymentIntent != null) {
            paymentIntent.update(params, options);
        }
    }

    @Override
    public String updatePaymentIntentForOrderSecond(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();
        Map<String, Object> params = new HashMap<>();
        Double totalPrice = order.getTotalPrice();
        orderRepository.save(order);

        params.put(STRIPE_PAYMENT_AMOUNT, Math.round(totalPrice * 100));
        PaymentIntent intent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
        if(Boolean.TRUE.equals(isIntentUnCapture(order)))
        {
            cancelPaymentIntent(order);
            return createPaymentIntent(order);
        }
        if(intent != null){
            intent.update(params, options);
            return intent.getClientSecret();
        }

        return StringUtils.EMPTY;
    }

    @Override
    public void updatePaymentInfoForOrder(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();

        CreditCardPaymentInfo cardPaymentInfo = new CreditCardPaymentInfo();
        PaymentMethod.Card card;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
        if (paymentIntent != null && StringUtils.isNotEmpty(paymentIntent.getPaymentMethod())) {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentIntent.getPaymentMethod(), options);
            card = paymentMethod.getCard();
            cardPaymentInfo.setId(paymentIntent.getId());
            cardPaymentInfo.setSubscriptionId(order.getPaymentIntentId());
            cardPaymentInfo.setUser(order.getUser());
            cardPaymentInfo.setId(paymentMethod.getId());
            cardPaymentInfo.setValidToMonth(card.getExpMonth().toString());
            cardPaymentInfo.setValidToYear(card.getExpYear().toString());
            cardPaymentInfo.setType(CARD_TYPE);
            cardPaymentInfo.setNumber(STRIPE_PAYMENT_CARD_PREFIX + card.getLast4());

            cardPaymentInfoRepository.save(cardPaymentInfo);
            order.setCreditCardPaymentInfo(cardPaymentInfo);
            orderRepository.save(order);
        }
    }

    @Override
    public void chargeForOrder(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
        paymentIntent.capture(options);
        order.setStatus(PaymentStatus.COMPLETED);
        User user = order.getUser();
        user.setVip(true);
        if(order.getSubscriptionPlan().getId() == 1){
            user.setVipLevel(1);
            user.getCompany().setCreateJobCount(order.getSubscriptionPlan().getMonthlyJobPostLimit());
        } else if(order.getSubscriptionPlan().getId() == 2){
            user.setVipLevel(2);
            user.getCompany().setCreateJobCount(order.getSubscriptionPlan().getMonthlyJobPostLimit());
        }
        user.setVipExpiryDate(order.getSubscriptionPlan().getCreatedAt().plusMonths(order.getSubscriptionPlan().getDurationMonths()));

        int retrieveTime = 0;
        Boolean isHasStripeError = Boolean.FALSE;
        StripeException stripeEx = null;
        while (retrieveTime < STRIPE_TOTAL_RETRIEVE_TIME)
        {
            try
            {
                paymentIntent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
                int retrieveBalanceTransaction = 0;
                while (retrieveBalanceTransaction < STRIPE_TOTAL_RETRIEVE_TIME)
                {
                    try{
                        String balanceTransactionId = paymentIntent.getCharges().getData().stream().findFirst().map(ch -> ch.getBalanceTransaction()).orElse(StringUtils.EMPTY);
                        if(StringUtils.isNotEmpty(balanceTransactionId)) {
                            BalanceTransaction balanceTransaction = BalanceTransaction.retrieve(balanceTransactionId, options);
                            order.setPaymentServiceFees(Double.valueOf(balanceTransaction.getFee()) / 100);
                            order.setTotalPriceLessFees(Double.valueOf(balanceTransaction.getNet()) / 100);
                            Double totalPrice = Double.valueOf(order.getTotalPriceLessFees()) + Double.valueOf(order.getPaymentServiceFees());
                            order.setTotalPrice(Double.valueOf(String.valueOf(new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP))));
                        }
                        break;
                    }catch (StripeException strEx)
                    {
                        stripeEx = strEx;
                        LOG.error("Try to retrieve Balance Transaction for Payment Intent ID {} of Order {} for {} time ", order.getPaymentIntentId(), order.getOrderId(), retrieveBalanceTransaction + 1);
                    }
                    finally {
                        retrieveBalanceTransaction++;
                        if(retrieveBalanceTransaction == STRIPE_TOTAL_RETRIEVE_TIME)
                        {
                            isHasStripeError = Boolean.TRUE;
                            LOG.error("Could not retrieve Balance Transaction Payment Intent ID {} to update Order {}, need to check !", order.getPaymentIntentId(), order.getOrderId());
                        }
                    }
                }
                break;
            }catch (StripeException ex)
            {
                stripeEx = ex;
                LOG.error("Try to retrieve Payment Intent ID {} of Order {} for {} time ", order.getPaymentIntentId(), order.getOrderId(), retrieveTime + 1);
            }
            finally {
                retrieveTime++;
                if(retrieveTime == STRIPE_TOTAL_RETRIEVE_TIME)
                {
                    isHasStripeError = Boolean.TRUE;
                    LOG.error("Could not retrieve Payment Intent ID {} to update Order {}, need to check !", order.getPaymentIntentId(), order.getOrderId());
                }
            }
        }

        if(stripeEx != null && isHasStripeError)
        {
            throw stripeEx;
        }

        orderRepository.save(order);
    }

    @Override
    public PaymentIntent getPaymentIntent(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();
        return PaymentIntent.retrieve(order.getPaymentIntentId(), options);
    }

    @Override
    public void cancelPaymentIntent(Order order) {
        RequestOptions options = buildRequestOptions();
        if (order == null || StringUtils.isEmpty(order.getPaymentIntentId())) {
            return;
        }
        final int STRIPE_TOTAL_CANCEL_TIME = STRIPE_CANCEL_TIME;
        int cancelTime = 0;
        while (cancelTime < STRIPE_TOTAL_CANCEL_TIME) {
            try {
                PaymentIntent intent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
                if (intent == null) {
                    LOG.error("Intent is null with ID {} - Order code {} ", order.getPaymentIntentId(), order.getOrderId());
                }
                if (intent != null && !INTENT_CANCEL.equals(intent.getStatus())) {
                    intent.cancel(options);
                    LOG.error("Intent with ID {} cancel successfully ", order.getPaymentIntentId());
                    break;
                }
            } catch (Exception ex) {
                LOG.error("Could not retrieve Payment Intent ID {} of Order {} ", order.getPaymentIntentId(), order.getOrderId());
            } finally {
                cancelTime++;
            }
        }
        order.setPaymentIntentId(StringUtils.EMPTY);
        order.setStatus(PaymentStatus.FAILED);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse createCardPaymentForOrder(Order order) throws Exception {
        String intentSecret;
        if (StringUtils.isNotEmpty(order.getPaymentIntentId())) {
            intentSecret = updatePaymentIntentForOrderSecond(order);
        } else {
            intentSecret = createPaymentIntent(order);
        }
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setIntentSecret(intentSecret);
        orderResponse.setPublishableKey(stripePaymentPublicKey);

        return orderResponse;
    }

    @Override
    public String createPaymentIntent(Order order){
        try {
            RequestOptions options = buildRequestOptions();
            Double totalPrice = order.getTotalPrice();
            Stripe.apiKey = stripePaymentSecretKey;
            String stripeCustomerId = getStripeCustomerId(order.getUser());

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(Math.round(totalPrice * 100))
                    .setCurrency("AUD")
                    .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                    .setCustomer(stripeCustomerId)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams, options);
            order.setPaymentIntentId(paymentIntent.getId());
            orderRepository.save(order);

            return paymentIntent.getClientSecret();
        } catch (Exception e) {
            LOG.error("Error while creating payment intent: {}", e.getMessage());
            throw new BadRequestException("Error while creating payment intent");
        }
    }

    @Override
    public boolean isIntentUnCapture(Order order) throws StripeException {
        RequestOptions options = buildRequestOptions();
        PaymentIntent intent = PaymentIntent.retrieve(order.getPaymentIntentId(), options);
        if(intent.getStatus().equals(INTENT_UNCAPTURED)){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse createOrder(OrderRequest orderRequest) throws Exception {
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new BadRequestException("User not found"));
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(orderRequest.getSubscriptionPlanId()).orElseThrow(() -> new BadRequestException("Subscription plan not found"));

        List<Order> pendingOrders = orderRepository.findByUserUserIdAndStatus(user.getUserId(), PaymentStatus.PENDING)
                .stream()
                .collect(Collectors.toList());
        for (Order order : pendingOrders) {
            cancelOrder(order);
        }

        Order order = new Order();
        order.setUser(user);
        order.setSubscriptionPlan(subscriptionPlan);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(PaymentStatus.PENDING);
        order.setTotalPrice(subscriptionPlan.getPrice());

        OrderResponse orderResponse = createCardPaymentForOrder(order);
        orderResponse.setId(order.getOrderId());
        orderResponse.setName(subscriptionPlan.getName());
        orderResponse.setDescription(subscriptionPlan.getDescription());
        orderResponse.setDurationMonths(subscriptionPlan.getDurationMonths());
        orderResponse.setCompanyDTO(companyConverter.convertToCompanyDTO(order.getUser().getCompany()));

        return orderResponse;
    }

    private void cancelOrder(Order order) {
        if (StringUtils.isNotEmpty(order.getPaymentIntentId())) {
            cancelPaymentIntent(order);
        }
        order.setStatus(PaymentStatus.FAILED);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse chargeOrder(Integer orderId) throws Exception {
        Order order = orderRepository.getOrderByOrderId(orderId);
        PaymentIntent paymentIntent = getPaymentIntent(order);

        if(!Boolean.TRUE.equals(paymentIntent.getStatus().equals(INTENT_UNCAPTURED))){
            cancelPaymentIntent(order);
            throw new Exception(INTENT_CAPTURE_ERROR_MESSAGE);
        }

        boolean isEqual = paymentIntent.getAmountCapturable() == Math.round(order.getTotalPrice() * 100);
        if(!isEqual){
            cancelPaymentIntent(order);
            throw new Exception(PAYMENT_AMOUNT_NOT_EQUAL);
        }

        updatePaymentInfoForOrder(order);
        updatePaymentIntentForOrderFirst(order);
        chargeForOrder(order);

        order.setTotalPrice(paymentIntent.getAmountCapturable() / 100.0);
        OrderResponse orderResponse = convertToOrderResponse(order);

        return orderResponse;
    }

    @Override
    public OrderResponse getOrderByUserId(Integer userId) throws Exception {
        Optional<Order> pendingOrder = orderRepository.findByUserUserIdAndStatus(userId, PaymentStatus.PENDING);
        if (pendingOrder.isPresent()) {
            Order order = pendingOrder.get();
            return convertToOrderResponse(order);
        }

        List<Order> orders = orderRepository.findLatestByUserId(userId);
        if (orders.isEmpty()) {
            throw new BadRequestException("Order not found");
        }

        Order latestOrder = orders.get(0);
        return convertToOrderResponse(latestOrder);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getOrderId());
        orderResponse.setName(order.getSubscriptionPlan().getName());
        orderResponse.setDescription(order.getSubscriptionPlan().getDescription());
        orderResponse.setDurationMonths(order.getSubscriptionPlan().getDurationMonths());
        orderResponse.setCompanyDTO(companyConverter.convertToCompanyDTO(order.getUser().getCompany()));
        orderResponse.setStatus(order.getStatus());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setIntentSecret(order.getPaymentIntentId());

        CreditCardPaymentInfo cardPaymentInfo = order.getCreditCardPaymentInfo();
        if (cardPaymentInfo != null) {
            PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
            paymentDetailsResponse.setId(cardPaymentInfo.getId());
            paymentDetailsResponse.setCardNumber(cardPaymentInfo.getNumber());
            paymentDetailsResponse.setExpiryMonth(cardPaymentInfo.getValidToMonth());
            paymentDetailsResponse.setExpiryYear(cardPaymentInfo.getValidToYear());
            paymentDetailsResponse.setSubscriptionId(cardPaymentInfo.getSubscriptionId());
            orderResponse.setPaymentInfo(paymentDetailsResponse);
        }

        return orderResponse;
    }

    @Override
    public OrderResponse changeSubscriptionPlan(Integer userId, Integer newPlanId) throws Exception {
        List<Order> pendingOrders = orderRepository.findByUserUserIdAndStatus(userId, PaymentStatus.PENDING)
                .stream()
                .collect(Collectors.toList());
        for (Order order : pendingOrders) {
            cancelPaymentIntent(order);
        }

        OrderRequest newOrderRequest = new OrderRequest();
        newOrderRequest.setUserId(userId);
        newOrderRequest.setSubscriptionPlanId(newPlanId);
        newOrderRequest.setPayByCC(true);

        return createOrder(newOrderRequest);
    }

    protected RequestOptions buildRequestOptions()
    {
        String apiKey = stripePaymentSecretKey;
        return RequestOptions.builder().setApiKey(apiKey).build();
    }

    private boolean isExistedStripeCustomer(String userID) throws StripeException {
        RequestOptions options = buildRequestOptions();
        String query = "name:";
        String formatCustomerId = FILTER_VALUE_CHARACTER.concat(userID).concat(FILTER_VALUE_CHARACTER);
        query = query.concat(formatCustomerId);
        CustomerSearchParams params = CustomerSearchParams.builder().setQuery(query).build();
        CustomerSearchResult result = Customer.search(params,options);

        if(result != null && result.getData() != null){
            return  result.getData().stream().filter(customer -> customer.getId().equals(userID)).findFirst().isPresent() ? true : false;
        }
        return  false;
    }

    private String getStripeCustomerId(User user) throws StripeException {
        boolean isExistingCustomer = isExistedStripeCustomer(String.valueOf(user.getUserId()));

        String stripeCustomerId;
        if (!isExistingCustomer) {
            stripeCustomerId = createStripeCustomer(user);
        } else {
            stripeCustomerId = String.valueOf(user.getUserId());
        }
        return stripeCustomerId;
    }

    public String createStripeCustomer(User user) throws StripeException {
        RequestOptions options = buildRequestOptions();
        Map<String, Object> params = new HashMap<>();
        params.put("id",user.getUserId());
        params.put("name",user.getUserId());
        params.put("description",user.getEmail());
        // Update meta data
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("mobileNumber", user.getPhone() != null ? user.getPhone() : StringUtils.EMPTY);
        metadata.put("email", user.getEmail() != null ? user.getEmail() : StringUtils.EMPTY);
        params.put("metadata", metadata);
        Customer customer = Customer.create(params,options);
        return  customer.getId();
    }
}
