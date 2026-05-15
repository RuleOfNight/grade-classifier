package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Lab 4 — Statement & Branch Coverage for Triangle.classify()
 *
 * Source under test:
 *   if (a <= 0 || b <= 0 || c <= 0)          // D1
 *       return "Invalid";
 *   if (a + b <= c || a + c <= b || b + c <= a)  // D2
 *       return "Not a triangle";
 *   if (a == b && b == c)                     // D3
 *       return "Equilateral";
 *   else if (a == b || b == c || a == c)      // D4
 *       return "Isosceles";
 *   else
 *       return "Scalene";
 *
 * ── PART 1: bộ test P1-P5 từ Lab 3 ─────────────────────────────────────
 *
 *  TC | Input           | Expected        | D1 | D2 | D3 | D4
 * ────┼─────────────────┼─────────────────┼────┼────┼────┼────
 *  P1 | (-1, 5, 5)      | Invalid         |  T |  - |  - |  -
 *  P2 | (1, 2, 10)      | Not a triangle  |  F |  T |  - |  -
 *  P3 | (5, 5, 5)       | Equilateral     |  F |  F |  T |  -
 *  P4 | (5, 5, 3)       | Isosceles       |  F |  F |  F |  T
 *  P5 | (3, 4, 5)       | Scalene         |  F |  F |  F |  F
 *
 * Statement coverage đạt được với P1-P5: 100%
 * Branch coverage (decision-level) đạt được với P1-P5: 100%
 *
 * Branch coverage (condition-level / JaCoCo bytecode):
 *   D1 (||):  a<=0 → T/F ✓ | b<=0 → T ✗ | c<=0 → T ✗
 *   D2 (||):  a+b<=c → T/F ✓ | a+c<=b → T ✗ | b+c<=a → T ✗
 *   D3 (&&):  a==b → T/F ✓ | b==c → T/F ✓  (fully covered)
 *   D4 (||):  a==b → T/F ✓ | b==c → T ✗ | a==c → T ✗
 *
 * → JaCoCo báo ~73% branch coverage vì short-circuit làm bỏ lỡ các
 *   nhánh con TRUE của b<=0, c<=0, a+c<=b, b+c<=a, b==c, a==c.
 *
 * ── PART 2: bổ sung test case để đạt 100% condition coverage ────────────
 *
 *  TC  | Input           | Expected        | Mục đích
 * ─────┼─────────────────┼─────────────────┼──────────────────────────────
 *  P6  | (5, -1, 5)      | Invalid         | D1: b<=0 = T
 *  P7  | (5, 5, -1)      | Invalid         | D1: c<=0 = T
 *  P8  | (10, 1, 2)      | Not a triangle  | D2: b+c<=a = T  (thay vì a+b<=c)
 *  P9  | (1, 10, 2)      | Not a triangle  | D2: a+c<=b = T
 *  P10 | (3, 5, 5)       | Isosceles       | D4: b==c = T  (a!=b, b==c)
 *  P11 | (5, 3, 5)       | Isosceles       | D4: a==c = T  (a!=b, b!=c, a==c)
 */
class TriangleCoverageTest {

    // ═══════════════════════════════════════════════════════
    //  PART 1 — bộ test P1-P5 (từ Lab 3)
    // ═══════════════════════════════════════════════════════

    // P1 — D1=T: cạnh âm -> Invalid
    @Test
    void p1_invalid_negativeA() {
        assertEquals("Invalid", Triangle.classify(-1, 5, 5));
    }

    // P2 — D1=F, D2=T: vi phạm bất đẳng thức tam giác
    @Test
    void p2_notATriangle_aPlusBLeC() {
        assertEquals("Not a triangle", Triangle.classify(1, 2, 10));
    }

    // P3 — D1=F, D2=F, D3=T: tam giác đều
    @Test
    void p3_equilateral() {
        assertEquals("Equilateral", Triangle.classify(5, 5, 5));
    }

    // P4 — D1=F, D2=F, D3=F, D4=T: tam giác cân (a==b)
    @Test
    void p4_isosceles_aEqualsB() {
        assertEquals("Isosceles", Triangle.classify(5, 5, 3));
    }

    // P5 — D1=F, D2=F, D3=F, D4=F: tam giác thường
    @Test
    void p5_scalene() {
        assertEquals("Scalene", Triangle.classify(3, 4, 5));
    }

    // ═══════════════════════════════════════════════════════
    //  PART 2 — bổ sung để đạt 100% condition coverage
    // ═══════════════════════════════════════════════════════

    // P6 — D1: b<=0 = T  (a>0 để short-circuit qua a<=0)
    @Test
    void p6_invalid_negativeB() {
        assertEquals("Invalid", Triangle.classify(5, -1, 5));
    }

    // P7 — D1: c<=0 = T  (a>0, b>0)
    @Test
    void p7_invalid_negativeC() {
        assertEquals("Invalid", Triangle.classify(5, 5, -1));
    }

    // P8 — D2: b+c<=a = T  (a+b>c, a+c>b để qua hai điều kiện đầu)
    @Test
    void p8_notATriangle_bPlusCLeA() {
        assertEquals("Not a triangle", Triangle.classify(10, 1, 2));
    }

    // P9 — D2: a+c<=b = T  (a+b>c để qua điều kiện đầu)
    @Test
    void p9_notATriangle_aPlusCLeB() {
        assertEquals("Not a triangle", Triangle.classify(1, 10, 2));
    }

    // P10 — D4: b==c = T, a!=b  (tránh D3=T)
    @Test
    void p10_isosceles_bEqualsC() {
        assertEquals("Isosceles", Triangle.classify(3, 5, 5));
    }

    // P11 — D4: a==c = T, a!=b, b!=c
    @Test
    void p11_isosceles_aEqualsC() {
        assertEquals("Isosceles", Triangle.classify(5, 3, 5));
    }
}
