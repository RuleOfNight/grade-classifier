package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * CFG:
 *
 *        [N1] a<=0 || b<=0 || c<=0
 *        /  \
 *   (T) /    \ (F)
 *   [N2]    [N3] a+b<=c || a+c<=b || b+c<=a
 * "Invalid"  /  \
 *        (T)/    \(F)
 *         [N4]  [N5] a==b && b==c
 *      "Not a"  /  \
 *          (T) /    \ (F)
 *            [N6]  [N7] a==b || b==c || a==c
 *        "Equil."  /  \
 *             (T) /    \ (F)
 *              [N8]   [N9]
 *          "Isosceles" "Scalene"
 *
 * CC = 4 decisions + 1 = 5
 *
 * Independent paths:
 *   P1: N1(T) -> N2                          -> "Invalid"
 *   P2: N1(F) -> N3(T) -> N4                 -> "Not a triangle"
 *   P3: N1(F) -> N3(F) -> N5(T) -> N6        -> "Equilateral"
 *   P4: N1(F) -> N3(F) -> N5(F) -> N7(T)->N8 -> "Isosceles"
 *   P5: N1(F) -> N3(F) -> N5(F) -> N7(F)->N9 -> "Scalene"
 */
class TriangleTest {

    public static void main(String[] args) {
        int pass = 0, fail = 0;
        String[][] cases = {
            {"P1 invalid_nonPositiveSide",  "Invalid",       "" + Triangle.classify(-1, 5, 5)},
            {"P2 notATriangle",             "Not a triangle","" + Triangle.classify(1, 2, 10)},
            {"P3 equilateral",              "Equilateral",   "" + Triangle.classify(5, 5, 5)},
            {"P4 isosceles",                "Isosceles",     "" + Triangle.classify(5, 5, 3)},
            {"P5 scalene",                  "Scalene",       "" + Triangle.classify(3, 4, 5)},
        };
        for (String[] c : cases) {
            boolean ok = c[1].equals(c[2]);
            System.out.println((ok ? "[PASS]" : "[FAIL]") + " " + c[0]
                + " | expected: " + c[1] + " | actual: " + c[2]);
            if (ok) pass++; else fail++;
        }
        System.out.println("\n" + pass + " passed, " + fail + " failed.");
    }

    // P1 — branch N1=true (statement: return "Invalid")
    @Test
    void invalid_nonPositiveSide() {
        assertEquals("Invalid", Triangle.classify(-1, 5, 5));
    }

    // P2 — branch N1=false, N3=true (statement: return "Not a triangle")
    @Test
    void notATriangle_violatesInequality() {
        assertEquals("Not a triangle", Triangle.classify(1, 2, 10));
    }

    // P3 — branch N1=false, N3=false, N5=true (statement: return "Equilateral")
    @Test
    void equilateral_allSidesEqual() {
        assertEquals("Equilateral", Triangle.classify(5, 5, 5));
    }

    // P4 — branch N5=false, N7=true (statement: return "Isosceles")
    @Test
    void isosceles_twoSidesEqual() {
        assertEquals("Isosceles", Triangle.classify(5, 5, 3));
    }

    // P5 — branch N7=false (statement: return "Scalene")
    @Test
    void scalene_allSidesDifferent() {
        assertEquals("Scalene", Triangle.classify(3, 4, 5));
    }
}
