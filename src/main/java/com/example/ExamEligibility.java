package com.example;

/**
 * Bài 3 – Kiểm tra điều kiện dự thi của sinh viên.
 *
 * Bảng quyết định – Giai đoạn 1: xét điều kiện dự thi
 * ┌──────────────────────────┬────┬────┬────┬────┬────┬────┬────┬────┐
 * │ Condition                │ R1 │ R2 │ R3 │ R4 │ R5 │ R6 │ R7 │ R8 │
 * ├──────────────────────────┼────┼────┼────┼────┼────┼────┼────┼────┤
 * │ Đi học >= 80%            │  Y │  Y │  Y │  Y │  N │  N │  N │  N │
 * │ Điểm giữa kì >= 1        │  Y │  Y │  N │  N │  Y │  Y │  N │  N │
 * │ Điểm bài tập lớn >= 1    │  Y │  N │  Y │  N │  Y │  N │  Y │  N │
 * ├──────────────────────────┼────┼────┼────┼────┼────┼────┼────┼────┤
 * │ Action                   │ Đủ │ Không │ Không │ Không │ Không │ Không │ Không │ Không │
 * └──────────────────────────┴────┴────┴────┴────┴────┴────┴────┴────┘
 *
 * Bảng quyết định – Giai đoạn 2: xét miễn thi (chỉ áp dụng khi đủ điều kiện)
 * ┌────────────────────────┬─────┬─────┬─────┬─────┐
 * │ Condition              │ R1a │ R1b │ R1c │ R1d │
 * ├────────────────────────┼─────┼─────┼─────┼─────┤
 * │ Điểm giữa kì = 10      │  Y  │  Y  │  N  │  N  │
 * │ Điểm bài tập lớn = 10  │  Y  │  N  │  Y  │  N  │
 * ├────────────────────────┼─────┼─────┼─────┼─────┤
 * │ Action                 │Miễn │ Dự  │ Dự  │ Dự  │
 * └────────────────────────┴─────┴─────┴─────┴─────┘
 */
public class ExamEligibility {

    public enum Result {
        ELIGIBLE,     // Đủ điều kiện dự thi
        EXEMPT,       // Được miễn thi
        NOT_ELIGIBLE  // Không đủ điều kiện
    }

    /**
     * @param attendanceRate  tỉ lệ đi học (0.0 – 1.0), vd 0.8 = 80%
     * @param midtermScore    điểm giữa kì (0 – 10)
     * @param assignmentScore điểm bài tập lớn (0 – 10)
     * @return kết quả xét điều kiện dự thi
     */
    public Result check(double attendanceRate, double midtermScore, double assignmentScore) {
        if (attendanceRate < 0 || attendanceRate > 1)
            throw new IllegalArgumentException("attendanceRate must be in [0, 1]");
        if (midtermScore < 0 || midtermScore > 10)
            throw new IllegalArgumentException("midtermScore must be in [0, 10]");
        if (assignmentScore < 0 || assignmentScore > 10)
            throw new IllegalArgumentException("assignmentScore must be in [0, 10]");

        boolean eligible = attendanceRate >= 0.8
                        && midtermScore   >= 1
                        && assignmentScore >= 1;

        if (!eligible) return Result.NOT_ELIGIBLE;

        if (midtermScore == 10 && assignmentScore == 10) return Result.EXEMPT;

        return Result.ELIGIBLE;
    }
}
