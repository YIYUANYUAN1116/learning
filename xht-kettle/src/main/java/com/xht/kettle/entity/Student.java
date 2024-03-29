package com.xht.kettle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String studentNo;
    private String name;
    private String sex;
    private String subjectName;
    private String score;
    private String hobbies;


}
