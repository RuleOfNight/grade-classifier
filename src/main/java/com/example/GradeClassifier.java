package com.example;

public class GradeClassifier {

    /**
     * Tính điểm trung bình từ mảng điểm và phân loại học lực.
     * Ném IllegalArgumentException nếu mảng null hoặc rỗng.
     */
    public String classify(int[] scores) {
        if (scores == null || scores.length == 0) {
            throw new IllegalArgumentException("Scores must not be null or empty");
        }

        int sum = 0;
        for (int score : scores) {
            sum += score;
        }

        double average = (double) sum / scores.length;

        if (average >= 85) {
            return "Giỏi";
        } else if (average >= 70) {
            return "Khá";
        } else if (average >= 50) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}
