package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases dựa trên Bảng quyết định – Bài 2: Bảo hiểm ô tô
 *
 * Bảng quyết định:
 * ┌───────────────────┬──────┬──────┬──────┬──────┬──────┐
 * │ Condition         │  R1  │  R2  │  R3  │  R4  │  R5  │
 * ├───────────────────┼──────┼──────┼──────┼──────┼──────┤
 * │ Giới tính nữ      │  Y   │  Y   │  N   │  N   │  -   │
 * │ Tuổi < 25         │  Y   │  N   │  Y   │  N   │  -   │
 * │ Tuổi 25–64        │  N   │  Y   │  N   │  Y   │  -   │
 * │ Tuổi >= 65        │  N   │  N   │  N   │  N   │  Y   │
 * ├───────────────────┼──────┼──────┼──────┼──────┼──────┤
 * │ Phí bảo hiểm ($)  │ 500  │ 500  │ 3000 │ 1000 │ 1500 │
 * └───────────────────┴──────┴──────┴──────┴──────┴──────┘
 */
class CarInsuranceTest {

    private final CarInsurance insurance = new CarInsurance();

    // ── R1: Nữ, tuổi < 25 → $500 ─────────────────────────────────────────────

    @Test
    void r1_female_age20_premium500() {
        assertEquals(500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 20));
    }

    @Test
    void r1_female_age24_boundary_premium500() {
        assertEquals(500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 24));
    }

    // ── R2: Nữ, tuổi 25–64 → $500 ────────────────────────────────────────────

    @Test
    void r2_female_age25_boundary_premium500() {
        assertEquals(500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 25));
    }

    @Test
    void r2_female_age40_premium500() {
        assertEquals(500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 40));
    }

    @Test
    void r2_female_age64_boundary_premium500() {
        assertEquals(500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 64));
    }

    // ── R3: Nam, tuổi < 25 → $3000 ───────────────────────────────────────────

    @Test
    void r3_male_age18_premium3000() {
        assertEquals(3000, insurance.calculatePremium(CarInsurance.Gender.MALE, 18));
    }

    @Test
    void r3_male_age24_boundary_premium3000() {
        assertEquals(3000, insurance.calculatePremium(CarInsurance.Gender.MALE, 24));
    }

    // ── R4: Nam, tuổi 25–64 → $1000 ──────────────────────────────────────────

    @Test
    void r4_male_age25_boundary_premium1000() {
        assertEquals(1000, insurance.calculatePremium(CarInsurance.Gender.MALE, 25));
    }

    @Test
    void r4_male_age45_premium1000() {
        assertEquals(1000, insurance.calculatePremium(CarInsurance.Gender.MALE, 45));
    }

    @Test
    void r4_male_age64_boundary_premium1000() {
        assertEquals(1000, insurance.calculatePremium(CarInsurance.Gender.MALE, 64));
    }

    // ── R5: Bất kỳ, tuổi >= 65 → $1500 ──────────────────────────────────────

    @Test
    void r5_female_age65_boundary_premium1500() {
        assertEquals(1500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 65));
    }

    @Test
    void r5_male_age65_boundary_premium1500() {
        assertEquals(1500, insurance.calculatePremium(CarInsurance.Gender.MALE, 65));
    }

    @Test
    void r5_female_age80_premium1500() {
        assertEquals(1500, insurance.calculatePremium(CarInsurance.Gender.FEMALE, 80));
    }

    @Test
    void r5_male_age80_premium1500() {
        assertEquals(1500, insurance.calculatePremium(CarInsurance.Gender.MALE, 80));
    }

    // ── Biên / lỗi ───────────────────────────────────────────────────────────

    /** Tuổi âm → ném IllegalArgumentException */
    @Test
    void invalid_negativeAge_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> insurance.calculatePremium(CarInsurance.Gender.MALE, -1));
    }

    /** Gender null → ném IllegalArgumentException */
    @Test
    void invalid_nullGender_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> insurance.calculatePremium(null, 30));
    }
}
