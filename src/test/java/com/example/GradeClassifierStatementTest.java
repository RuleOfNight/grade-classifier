package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Statement coverage: every statement in GradeClassifier is executed at least once.
 */
class GradeClassifierStatementTest {

    private final GradeClassifier classifier = new GradeClassifier();

    // Thực thi: if (null) + throw
    @Test
    void nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> classifier.classify(null));
    }

    // Thực thi: if (length==0) + throw
    @Test
    void emptyInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> classifier.classify(new int[]{}));
    }

    // Thực thi: total=0, for-loop, total+=score, average, return "Giỏi"
    @Test
    void averageGe85_returnsGioi() {
        assertEquals("Giỏi", classifier.classify(new int[]{90, 80}));
    }

    // Thực thi: return "Khá"
    @Test
    void average70to84_returnsKha() {
        assertEquals("Khá", classifier.classify(new int[]{70, 75}));
    }

    // Thực thi: return "Trung bình"
    @Test
    void average50to69_returnsTrungBinh() {
        assertEquals("Trung bình", classifier.classify(new int[]{50, 60}));
    }

    // Thực thi: return "Yếu"
    @Test
    void averageLt50_returnsYeu() {
        assertEquals("Yếu", classifier.classify(new int[]{30, 40}));
    }
}
