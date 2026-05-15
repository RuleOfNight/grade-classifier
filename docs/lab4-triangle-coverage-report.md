# Lab 4 — Kiểm thử Triangle: Statement & Branch Coverage

## 1. Mã nguồn cần kiểm thử

**File:** `src/main/java/com/example/Triangle.java`

```java
public static String classify(int a, int b, int c) {
    if (a <= 0 || b <= 0 || c <= 0)               // D1
        return "Invalid";
    if (a + b <= c || a + c <= b || b + c <= a)   // D2
        return "Not a triangle";
    if (a == b && b == c)                          // D3
        return "Equilateral";
    else if (a == b || b == c || a == c)           // D4
        return "Isosceles";
    else
        return "Scalene";
}
```

---

## 2. CFG và Cyclomatic Complexity

```
       [N1] a<=0 || b<=0 || c<=0
       /  \
  (T) /    \ (F)
  [N2]    [N3] a+b<=c || a+c<=b || b+c<=a
"Invalid"  /  \
       (T)/    \(F)
        [N4]  [N5] a==b && b==c
    "Not a"  /  \
         (T)/    \(F)
          [N6]  [N7] a==b || b==c || a==c
      "Equil."  /  \
           (T) /    \ (F)
            [N8]   [N9]
        "Isosceles" "Scalene"
```

**CC = số quyết định + 1 = 4 + 1 = 5**

---

## 3. Bộ test P1–P5 (từ Lab 3)

| TC | Input (a, b, c) | Expected | D1 | D2 | D3 | D4 |
|----|-----------------|----------|:--:|:--:|:--:|:--:|
| P1 | (-1, 5, 5) | `Invalid` | T | — | — | — |
| P2 | (1, 2, 10) | `Not a triangle` | F | T | — | — |
| P3 | (5, 5, 5) | `Equilateral` | F | F | T | — |
| P4 | (5, 5, 3) | `Isosceles` | F | F | F | T |
| P5 | (3, 4, 5) | `Scalene` | F | F | F | F |

### 3.1 Statement Coverage — P1–P5

| Statement | Phủ bởi |
|-----------|---------|
| `return "Invalid"` | P1 |
| `return "Not a triangle"` | P2 |
| `return "Equilateral"` | P3 |
| `return "Isosceles"` | P4 |
| `return "Scalene"` | P5 |

**→ Statement Coverage = 100%** (5/5 câu lệnh)

### 3.2 Branch Coverage (Decision-level) — P1–P5

| Decision | Nhánh T | Nhánh F |
|----------|---------|---------|
| D1 | P1 ✅ | P2 ✅ |
| D2 | P2 ✅ | P3 ✅ |
| D3 | P3 ✅ | P4 ✅ |
| D4 | P4 ✅ | P5 ✅ |

**→ Decision Branch Coverage = 100%** (8/8 nhánh)

### 3.3 Branch Coverage (Condition-level / JaCoCo) — P1–P5

JaCoCo đo nhánh ở mức bytecode — mỗi toán tử `||` / `&&` tạo nhánh riêng:

| Điều kiện con | Nhánh T | Nhánh F | Trạng thái |
|---|:---:|:---:|:---:|
| D1: `a <= 0` | P1 ✅ | P2–P5 ✅ | ✅ |
| D1: `b <= 0` | ❌ | P2–P5 ✅ | ❌ |
| D1: `c <= 0` | ❌ | P2–P5 ✅ | ❌ |
| D2: `a+b <= c` | P2 ✅ | P3–P5 ✅ | ✅ |
| D2: `a+c <= b` | ❌ | P3–P5 ✅ | ❌ |
| D2: `b+c <= a` | ❌ | P3–P5 ✅ | ❌ |
| D3: `a == b` (&&) | P3,P4 ✅ | P5 ✅ | ✅ |
| D3: `b == c` | P3 ✅ | P4 ✅ | ✅ |
| D4: `a == b` (\|\|) | P4 ✅ | P5 ✅ | ✅ |
| D4: `b == c` | ❌ | P5 ✅ | ❌ |
| D4: `a == c` | ❌ | P5 ✅ | ❌ |

**→ JaCoCo Branch Coverage (P1–P5) ≈ 73%** (16/22 nhánh bytecode)

**Nguyên nhân:** Java sử dụng **short-circuit evaluation** với `||` và `&&`. Khi điều kiện đầu tiên đã xác định kết quả, các điều kiện sau không được thực thi → JaCoCo không tính các nhánh đó là đã phủ.

---

## 4. Bổ sung P6–P11 để đạt 100% Branch Coverage

| TC | Input (a, b, c) | Expected | Mục đích |
|----|-----------------|----------|----------|
| P6 | (5, **-1**, 5) | `Invalid` | D1: `b <= 0` = T |
| P7 | (5, 5, **-1**) | `Invalid` | D1: `c <= 0` = T |
| P8 | (**10**, 1, 2) | `Not a triangle` | D2: `b+c <= a` = T |
| P9 | (1, **10**, 2) | `Not a triangle` | D2: `a+c <= b` = T |
| P10 | (3, **5, 5**) | `Isosceles` | D4: `b == c` = T (a≠b) |
| P11 | (**5**, 3, **5**) | `Isosceles` | D4: `a == c` = T (a≠b, b≠c) |

---

## 5. Kết quả kiểm thử thực tế

**Công cụ:** JUnit 5 + JaCoCo 0.8.11, chạy qua Maven  
**File test:** `src/test/java/com/example/TriangleCoverageTest.java`

```
Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Báo cáo JaCoCo — phương thức `classify(int, int, int)`

| Metric | Missed | Covered | Coverage |
|--------|-------:|--------:|---------:|
| Instructions | 0 | 46 | **100%** |
| Branches | 0 | 22 | **100%** |
| Lines | 0 | 9 | **100%** |

JaCoCo HTML xác nhận từng dòng:

| Dòng | Điều kiện | Kết quả JaCoCo |
|------|-----------|----------------|
| 7 | `a<=0 \|\| b<=0 \|\| c<=0` | All 6 branches covered ✅ |
| 11 | `a+b<=c \|\| a+c<=b \|\| b+c<=a` | All 6 branches covered ✅ |
| 15 | `a==b && b==c` | All 4 branches covered ✅ |
| 17 | `a==b \|\| b==c \|\| a==c` | All 6 branches covered ✅ |

---

## 6. Tổng kết

| Bộ test | Statement Coverage | Branch Coverage (JaCoCo) |
|---------|:-----------------:|:------------------------:|
| P1–P5 (Lab 3) | **100%** | **~73%** (16/22) |
| P1–P11 (Lab 4) | **100%** | **100%** (22/22) |

### Giải thích

- **P1–P5 đạt 100% Statement Coverage** vì mỗi câu lệnh `return` đều được thực thi qua ít nhất một test case.
- **P1–P5 chưa đạt 100% Branch Coverage (JaCoCo)** vì short-circuit evaluation khiến các điều kiện con `b<=0`, `c<=0`, `a+c<=b`, `b+c<=a`, `b==c` (D4), `a==c` không bao giờ được đánh giá khi điều kiện đầu trong `||` đã là `true`.
- **P6–P11 được thêm vào** để "mở" đường đến từng điều kiện con còn thiếu, đảm bảo mỗi sub-condition đều có nhánh True được thực thi.
- **Kết quả cuối:** P1–P11 đạt **100% Statement Coverage** và **100% Branch Coverage** theo JaCoCo.
