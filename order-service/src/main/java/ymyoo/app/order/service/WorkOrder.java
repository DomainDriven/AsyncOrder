package ymyoo.app.order.service;

/**
 * <a href="https://en.wikipedia.org/wiki/Work_order">Work Order</a>
 * Created by yooyoung-mo on 2017. 7. 4..
 */
public class WorkOrder {
    private String customerName;
    private String customerCellPhoneNo;
    private String customerEmail;

    private String productId;
    private int orderQty;

    private int paymentAmt;
    private String paymentCardNo;


    public WorkOrder(String customerName, String customerCellPhoneNo, String customerEmail, String productId,
                     int orderQty, int paymentAmt, String paymentCardNo) {
        this.customerName = customerName;
        this.customerCellPhoneNo = customerCellPhoneNo;
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.orderQty = orderQty;
        this.paymentAmt = paymentAmt;
        this.paymentCardNo = paymentCardNo;
    }


    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerCellPhoneNo() {
        return customerCellPhoneNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getProductId() {
        return productId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public int getPaymentAmt() {
        return paymentAmt;
    }

    public String getPaymentCardNo() {
        return paymentCardNo;
    }
}
