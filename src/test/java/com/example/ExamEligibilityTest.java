package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases dựa trên Bảng quyết định – Bài 3: Kiểm tra điều kiện dự thi
 *
 * Bảng quyết định – Giai đoạn 1 (xét điều kiện dự thi):
 * ┌──────────────────────────┬────┬────┬────┬────┬────┬────┬────┬────┐
 * │ Condition                │ R1 │ R2 │ R3 │ R4 │ R5 │ R6 │ R7 │ R8 │
 * ├──────────────────────────┼────┼────┼────┼────┼────┼────┼────┼────┤
 * │ Đi học >= 80%            │  Y │  Y │  Y │  Y │  N │  N │  N │  N │
 * │ Điểm giữa kì >= 1        │  Y │  Y │  N │  N │  Y │  Y │  N │  N │
 * │ Điểm bài tập lớn >= 1    │  Y │  N │  Y │  N │  Y │  N │  Y │  N │
 * ├──────────────────────────┼────┼────┼────┼────┼────┼────┼────┼────┤
 * │ Action                   │ Đủ │ Ko │ Ko │ Ko │ Ko │ Ko │ Ko │ Ko │
 * └──────────────────────────┴────┴────┴────┴────┴────┴────┴────┴────┘
 *
 * Bảng quyết định – Giai đoạn 2 (xét miễn thi, chỉ khi R1 = Đủ điều kiện):
 * ┌────────────────────────┬─────┬─────┬─────┬─────┐
 * │ Condition              │ R1a │ R1b │ R1c │ R1d │
 * ├────────────────────────┼─────┼─────┼─────┼─────┤
 * │ Điểm giữa kì = 10      │  Y  │  Y  │  N  │  N  │
 * │ Điểm bài tập lớn = 10  │  Y  │  N  │  Y  │  N  │
 * ├────────────────────────┼─────┼─────┼─────┼─────┤
 * │ Action                 │Miễn │ Dự  │ Dự  │ Dự  │
 * └────────────────────────┴─────┴─────┴─────┴─────┘
 */
class ExamEligibilityTest {

    private final ExamEligibility exam = new ExamEligibility();

    // ── Giai đoạn 1: xét điều kiện dự thi ───────────────────────────────────

    /**
     * R1: Đi học 80%, GK=5, BTL=5 → Đủ điều kiện dự thi
     * Input: attendanceRate=0.80, midterm=5, assignment=5
     */
    @Test
    void r1_allConditionsMet_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.80, 5, 5));
    }

    /**
     * R2: Đi học >= 80%, GK >= 1, BTL = 0 → Không đủ điều kiện
     * Input: attendanceRate=0.85, midterm=5, assignment=0
     */
    @Test
    void r2_assignmentBelow1_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.85, 5, 0));
    }

    /**
     * R3: Đi học >= 80%, GK = 0, BTL >= 1 → Không đủ điều kiện
     * Input: attendanceRate=0.85, midterm=0, assignment=5
     */
    @Test
    void r3_midtermBelow1_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.85, 0, 5));
    }

    /**
     * R4: Đi học >= 80%, GK = 0, BTL = 0 → Không đủ điều kiện
     * Input: attendanceRate=0.85, midterm=0, assignment=0
     */
    @Test
    void r4_midtermAndAssignmentBelow1_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.85, 0, 0));
    }

    /**
     * R5: Đi học < 80%, GK >= 1, BTL >= 1 → Không đủ điều kiện
     * Input: attendanceRate=0.79, midterm=5, assignment=5
     */
    @Test
    void r5_attendanceBelow80_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.79, 5, 5));
    }

    /**
     * R6: Đi học < 80%, GK >= 1, BTL = 0 → Không đủ điều kiện
     */
    @Test
    void r6_attendanceBelow80_assignmentBelow1_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.70, 5, 0));
    }

    /**
     * R7: Đi học < 80%, GK = 0, BTL >= 1 → Không đủ điều kiện
     */
    @Test
    void r7_attendanceBelow80_midtermBelow1_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.70, 0, 5));
    }

    /**
     * R8: Tất cả điều kiện đều không đạt → Không đủ điều kiện
     */
    @Test
    void r8_allConditionsFail_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.50, 0, 0));
    }

    // ── Giai đoạn 2: xét miễn thi ────────────────────────────────────────────

    /**
     * R1a: Đủ điều kiện, GK=10, BTL=10 → Được miễn thi
     */
    @Test
    void r1a_eligible_midterm10_assignment10_exempt() {
        assertEquals(ExamEligibility.Result.EXEMPT,
                exam.check(0.90, 10, 10));
    }

    /**
     * R1b: Đủ điều kiện, GK=10, BTL<10 → Dự thi (không miễn)
     */
    @Test
    void r1b_eligible_midterm10_assignmentNot10_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.90, 10, 8));
    }

    /**
     * R1c: Đủ điều kiện, GK<10, BTL=10 → Dự thi (không miễn)
     */
    @Test
    void r1c_eligible_midtermNot10_assignment10_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.90, 8, 10));
    }

    /**
     * R1d: Đủ điều kiện, GK<10, BTL<10 → Dự thi (không miễn)
     */
    @Test
    void r1d_eligible_neitherScore10_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.90, 7, 7));
    }

    // ── Biên – giá trị ranh giới ─────────────────────────────────────────────

    /** Đi học đúng 80% → đủ điều kiện */
    @Test
    void boundary_attendanceExactly80_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.80, 5, 5));
    }

    /** Đi học 79% → không đủ */
    @Test
    void boundary_attendance79_notEligible() {
        assertEquals(ExamEligibility.Result.NOT_ELIGIBLE,
                exam.check(0.79, 5, 5));
    }

    /** GK đúng = 1 → đủ điều kiện */
    @Test
    void boundary_midtermExactly1_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.80, 1, 5));
    }

    /** BTL đúng = 1 → đủ điều kiện */
    @Test
    void boundary_assignmentExactly1_eligible() {
        assertEquals(ExamEligibility.Result.ELIGIBLE,
                exam.check(0.80, 5, 1));
    }

    // ── Lỗi đầu vào ──────────────────────────────────────────────────────────

    /** Tỉ lệ đi học > 1.0 → ném IllegalArgumentException */
    @Test
    void invalid_attendanceOver1_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> exam.check(1.1, 5, 5));
    }

    /** Điểm giữa kì âm → ném IllegalArgumentException */
    @Test
    void invalid_negativeMidterm_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> exam.check(0.80, -1, 5));
    }

    /** Điểm bài tập lớn > 10 → ném IllegalArgumentException */
    @Test
    void invalid_assignmentOver10_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> exam.check(0.80, 5, 11));
    }
}
