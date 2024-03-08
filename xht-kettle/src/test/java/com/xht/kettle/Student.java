package com.xht.kettle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String studentNo;
    private String name;
    private String sex;
    private List<Subject> subjects = new ArrayList<>();
    private List<String> hobbys;

    // 构造函数、getter和setter略去...
}


