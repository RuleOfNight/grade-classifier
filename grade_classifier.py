def classify(scores):
    """Tính điểm trung bình và phân loại học lực."""
    if not scores:
        raise ValueError("Danh sách điểm không được rỗng")

    total = 0
    for score in scores:
        total += score

    average = total / len(scores)

    if average >= 85:
        return "Giỏi"
    elif average >= 70:
        return "Khá"
    elif average >= 50:
        return "Trung bình"
    else:
        return "Yếu"
