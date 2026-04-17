package com.example;

public class GradeClassifier {

    public String classify(int[] scores) {
        if (scores == null || scores.length == 0) {
            throw new IllegalArgumentException("Danh sách điểm không được rỗng");
        }

        int total = 0;
        for (int score : scores) {
            total += score;
        }

        double average = (double) total / scores.length;

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
