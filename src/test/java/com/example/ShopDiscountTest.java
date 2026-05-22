package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases dựa trên Bảng quyết định – Bài 1: Website bán hàng
 *
 * Bảng quyết định:
 * ┌──────────────────────┬────┬────┬────┬────┐
 * │ Condition            │ R1 │ R2 │ R3 │ R4 │
 * ├──────────────────────┼────┼────┼────┼────┤
 * │ Khách mới            │  Y │  N │  N │  N │
 * │ Khách cũ             │  N │  Y │  N │  N │
 * │ Có thẻ giảm giá      │  N │  N │  Y │  N │
 * ├──────────────────────┼────┼────┼────┼────┤
 * │ Giảm giá (%)         │ 10 │ 15 │ 25 │  0 │
 * └──────────────────────┴────┴────┴────┴────┘
 */
class ShopDiscountTest {

    private final ShopDiscount shop = new ShopDiscount();

    // ── Tỉ lệ giảm giá ───────────────────────────────────────────────────────

    /** R1: Khách mới → giảm 10% */
    @Test
    void r1_newCustomer_discountRate10Percent() {
        assertEquals(0.10, shop.getDiscountRate(ShopDiscount.CustomerType.NEW), 1e-9);
    }

    /** R2: Khách cũ → giảm 15% */
    @Test
    void r2_oldCustomer_discountRate15Percent() {
        assertEquals(0.15, shop.getDiscountRate(ShopDiscount.CustomerType.OLD), 1e-9);
    }

    /** R3: Có thẻ giảm giá → giảm 25% */
    @Test
    void r3_cardHolder_discountRate25Percent() {
        assertEquals(0.25, shop.getDiscountRate(ShopDiscount.CustomerType.CARD_HOLDER), 1e-9);
    }

    /** R4: Không thuộc loại nào → không giảm (0%) */
    @Test
    void r4_noneType_discountRate0Percent() {
        assertEquals(0.00, shop.getDiscountRate(ShopDiscount.CustomerType.NONE), 1e-9);
    }

    // ── Giá sau giảm (input: giá gốc = 100 000) ─────────────────────────────

    /** R1: Khách mới, giá 100 000 → còn 90 000 */
    @Test
    void r1_newCustomer_finalPrice90000() {
        assertEquals(90_000.0, shop.calculateFinalPrice(100_000, ShopDiscount.CustomerType.NEW), 1e-9);
    }

    /** R2: Khách cũ, giá 100 000 → còn 85 000 */
    @Test
    void r2_oldCustomer_finalPrice85000() {
        assertEquals(85_000.0, shop.calculateFinalPrice(100_000, ShopDiscount.CustomerType.OLD), 1e-9);
    }

    /** R3: Có thẻ, giá 100 000 → còn 75 000 */
    @Test
    void r3_cardHolder_finalPrice75000() {
        assertEquals(75_000.0, shop.calculateFinalPrice(100_000, ShopDiscount.CustomerType.CARD_HOLDER), 1e-9);
    }

    /** R4: Không thuộc loại nào, giá 100 000 → vẫn 100 000 */
    @Test
    void r4_noneType_finalPriceUnchanged() {
        assertEquals(100_000.0, shop.calculateFinalPrice(100_000, ShopDiscount.CustomerType.NONE), 1e-9);
    }

    // ── Biên / lỗi ───────────────────────────────────────────────────────────

    /** Giá gốc = 0 → giá cuối = 0 */
    @Test
    void boundary_zeroPrice_returns0() {
        assertEquals(0.0, shop.calculateFinalPrice(0, ShopDiscount.CustomerType.NEW), 1e-9);
    }

    /** Giá âm → ném IllegalArgumentException */
    @Test
    void invalid_negativePrice_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> shop.calculateFinalPrice(-1, ShopDiscount.CustomerType.NEW));
    }

    /** CustomerType null → ném IllegalArgumentException */
    @Test
    void invalid_nullCustomerType_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> shop.getDiscountRate(null));
    }
}
