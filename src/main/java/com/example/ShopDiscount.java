package com.example;

/**
 * Bài 1 – Website bán hàng: tính giảm giá theo loại khách hàng.
 *
 * Bảng quyết định:
 * ┌──────────────────────┬────┬────┬──────────┬──────────┐
 * │ Condition            │ R1 │ R2 │    R3    │    R4    │
 * ├──────────────────────┼────┼────┼──────────┼──────────┤
 * │ Khách mới            │  Y │  N │    N     │    N     │
 * │ Khách cũ             │  N │  Y │    N     │    N     │
 * │ Có thẻ giảm giá      │  N │  N │    Y     │    N     │
 * ├──────────────────────┼────┼────┼──────────┼──────────┤
 * │ Action: giảm giá (%) │ 10 │ 15 │    25    │     0    │
 * └──────────────────────┴────┴────┴──────────┴──────────┘
 */
public class ShopDiscount {

    public enum CustomerType {
        NEW,         // Khách mới
        OLD,         // Khách cũ
        CARD_HOLDER, // Có thẻ giảm giá
        NONE         // Không thuộc loại nào
    }

    /** @return tỉ lệ giảm giá (0.0 – 1.0) */
    public double getDiscountRate(CustomerType type) {
        if (type == null) throw new IllegalArgumentException("CustomerType must not be null");
        switch (type) {
            case NEW:         return 0.10;
            case OLD:         return 0.15;
            case CARD_HOLDER: return 0.25;
            default:          return 0.00;
        }
    }

    /** @return giá sau khi áp dụng khuyến mãi */
    public double calculateFinalPrice(double originalPrice, CustomerType type) {
        if (originalPrice < 0) throw new IllegalArgumentException("Price must not be negative");
        return originalPrice * (1 - getDiscountRate(type));
    }
}
