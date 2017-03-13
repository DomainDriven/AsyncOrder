package ymyoo.app.order.domain.query.dto;

/**
 * Created by 유영모 on 2017-03-13.
 */
public class CustomerPurchaseOrder {
    private String orderId;
    private String productId;
    private int orderQty;
    private int orderAmount;
    private String creditCardNo;

    public CustomerPurchaseOrder(String orderId, String productId, int orderQty, int orderAmount, String creditCardNo) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderQty = orderQty;
        this.orderAmount = orderAmount;
        this.creditCardNo = creditCardNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
}
