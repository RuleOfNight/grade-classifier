import pytest
from grade_classifier import classify


# P1: vào → if not scores (rỗng) → raise
def test_p1_empty_raises():
    with pytest.raises(ValueError):
        classify([])


# P2: vào → loop 1 lần → avg >= 85 → "Giỏi"
def test_p2_loop_once_gioi():
    assert classify([90]) == "Giỏi"


# P3: vào → loop nhiều lần → avg >= 85 → "Giỏi"
def test_p3_loop_multiple_gioi():
    assert classify([80, 90, 95]) == "Giỏi"


# P4: vào → loop → 70 <= avg < 85 → "Khá"
def test_p4_kha():
    assert classify([70, 80]) == "Khá"


# P5: vào → loop → 50 <= avg < 70 → "Trung bình"
def test_p5_trung_binh():
    assert classify([50, 65]) == "Trung bình"


# P6: vào → loop → avg < 50 → "Yếu"
def test_p6_yeu():
    assert classify([20, 40]) == "Yếu"
