import pytest
from grade_classifier import classify


# Thực thi: if not scores + raise ValueError
def test_empty_list_raises():
    with pytest.raises(ValueError):
        classify([])


# Thực thi: total=0, for-loop, total+=score, average, return "Giỏi"
def test_gioi():
    assert classify([90, 80]) == "Giỏi"


# Thực thi: return "Khá"
def test_kha():
    assert classify([70, 75]) == "Khá"


# Thực thi: return "Trung bình"
def test_trung_binh():
    assert classify([50, 60]) == "Trung bình"


# Thực thi: return "Yếu"
def test_yeu():
    assert classify([30, 40]) == "Yếu"
