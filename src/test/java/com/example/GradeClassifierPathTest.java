package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Path coverage: every distinct control-flow path through GradeClassifier is exercised.
 *
 * P1: scores == null        -> throw
 * P2: scores.length == 0    -> throw
 * P3: loop x1, avg >= 85   -> "Giỏi"
 * P4: loop xN, avg >= 85   -> "Giỏi"
 * P5: loop -> 70 <= avg < 85 -> "Khá"
 * P6: loop -> 50 <= avg < 70 -> "Trung bình"
 * P7: loop -> avg < 50      -> "Yếu"
 */
class GradeClassifierPathTest {

    private final GradeClassifier classifier = new GradeClassifier();

    // P1: null -> throw
    @Test
    void p1_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> classifier.classify(null));
    }

    // P2: empty -> throw
    @Test
    void p2_empty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> classifier.classify(new int[]{}));
    }

    // P3: loop executes exactly once, avg >= 85 -> "Giỏi"
    @Test
    void p3_singleElement_gioi() {
        assertEquals("Giỏi", classifier.classify(new int[]{90}));
    }

    // P4: loop executes multiple times, avg >= 85 -> "Giỏi"
    @Test
    void p4_multipleElements_gioi() {
        assertEquals("Giỏi", classifier.classify(new int[]{80, 90, 95}));
    }

    // P5: loop -> 70 <= avg < 85 -> "Khá"
    @Test
    void p5_kha() {
        assertEquals("Khá", classifier.classify(new int[]{70, 80}));
    }

    // P6: loop -> 50 <= avg < 70 -> "Trung bình"
    @Test
    void p6_trungBinh() {
        assertEquals("Trung bình", classifier.classify(new int[]{50, 65}));
    }

    // P7: loop -> avg < 50 -> "Yếu"
    @Test
    void p7_yeu() {
        assertEquals("Yếu", classifier.classify(new int[]{20, 40}));
    }
}
