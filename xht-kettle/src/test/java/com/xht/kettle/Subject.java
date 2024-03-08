package com.xht.kettle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private String subjectName;
    private int score;

    // 构造函数、getter和setter略去...
}
