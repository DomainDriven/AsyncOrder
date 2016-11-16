package ymyoo.order.domain;

/**
 * 주문 아이템 배송 유형
 * Created by 유영모 on 2016-10-19.
 */
public enum OrderItemDeliveryType {
    /** 자사 배송(a.k.a 직배) */
    DIRECTING,
    /** 배송 대행(a.k.a 제 3자 배송) */
    AGENCY
}
