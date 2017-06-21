package ymyoo.infra.messaging;

/**
 * Created by 유영모 on 2016-12-08.
 */
public class MessageChannels {
    public static final String INVENTORY_REQUEST = "INVENTORY-REQUEST";
    public static final String INVENTORY_REPLY = "INVENTORY-REPLY";

    public static final String PAYMENT_AUTH_APP_REQUEST = "PAYMENT-AUTH-APP-REQUEST";
    public static final String PAYMENT_AUTH_APP_REPLY = "PAYMENT-AUTH-APP-REPLY";

    public static final String PURCHASE_ORDER_CREATED = "PURCHASE-ORDER-CREATED";

    public static final String MESSAGE_STORE = "MESSAGE-STORE";
    public static final String MESSAGE_STORE_TYPE_ORDER_STATUS = "ORDER-STATUS";
    public static final String MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY = "INCOMPLETE-BUSINESS-ACTIVITY";
}
