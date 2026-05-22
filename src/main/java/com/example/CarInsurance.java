package com.example;

/**
 * Bài 2 – Bảo hiểm ô tô: tính phí bảo hiểm theo giới tính và tuổi.
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
 * │ Action: phí ($)   │ 500  │ 500  │ 3000 │ 1000 │ 1500 │
 * └───────────────────┴──────┴──────┴──────┴──────┴──────┘
 * R1: nữ < 25 → $500
 * R2: nữ 25–64 → $500
 * R3: nam < 25 → $3000
 * R4: nam 25–64 → $1000
 * R5: bất kỳ >= 65 → $1500
 */
public class CarInsurance {

    public enum Gender { MALE, FEMALE }

    /**
     * @param gender giới tính lái xe
     * @param age    tuổi lái xe (>= 0)
     * @return phí bảo hiểm hàng năm (USD)
     */
    public int calculatePremium(Gender gender, int age) {
        if (gender == null) throw new IllegalArgumentException("Gender must not be null");
        if (age < 0)        throw new IllegalArgumentException("Age must not be negative");

        if (age >= 65) return 1500;                  // R5
        if (gender == Gender.FEMALE) return 500;     // R1, R2
        if (age < 25)  return 3000;                  // R3
        return 1000;                                 // R4: male 25–64
    }
}
