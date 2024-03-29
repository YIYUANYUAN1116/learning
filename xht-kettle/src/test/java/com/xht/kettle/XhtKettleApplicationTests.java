package com.xht.kettle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xht.kettle.entity.Student;
import com.xht.kettle.service.KettleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class XhtKettleApplicationTests {

    @Autowired
    private KettleService kettleService;

    @Test
    void contextLoads() {
    }


    @Test
    public void test1(){
        String fileName = "D:\\Development\\Tool\\Kettle\\file\\param.ktr";
        Map<String, String> map = new HashMap<>();

        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");

        boolean runTaskKtr = kettleService.runTaskKtr(fileName, map);
        System.out.println(runTaskKtr?"成功":"失败");
    }

    @Test
    public void test3(){
        String fileName = "D:\\Development\\Tool\\Kettle\\file\\transXMLDemo.ktr";

        Map<String, String> map = new HashMap<>();

        map.put("inputFilePath","D:\\Development\\Tool\\Kettle\\xmldemo.xml");
        map.put("outputFilePath","D:/Development/Tool/Kettle/file/123test123");


        boolean runTaskKtr = kettleService.runTaskKtr(fileName, map);
        System.out.println(runTaskKtr?"成功":"失败");
    }

    @Test
    public void test2(){
        List<Student> kettleDemos = new ArrayList<>();
        Student student1 = new Student("1001", "张", "女", "语文", "80", "抽烟烫头");
        Student student2 = new Student("1001", "张", "女", "数学", "90", "抽烟烫头");
        Student student3 = new Student("1002", "李", "女", "语文", "81", "唱歌跳舞");
        Student student4 = new Student("1002", "李", "女", "数学", "82", "唱歌跳舞");
        kettleDemos.add(student1);
        kettleDemos.add(student2);
        kettleDemos.add(student3);
        kettleDemos.add(student4);




        JSONArray students = new JSONArray();

        JSONObject student = new JSONObject();
        student.put("studentNo","1001");
        student.put("name","张");
        student.put("sex","女");


        JSONArray subjects = new JSONArray();
        JSONObject subject1 = new JSONObject();
        subject1.put("subjectName","语文");
        subject1.put("score",80);
        JSONObject subject2 = new JSONObject();
        subject2.put("subjectName","数学");
        subject2.put("score",87);

        subjects.add(subject1);
        subjects.add(subject2);

        student.put("subject",subjects);

        students.add(student);

        System.out.println(students);
    }

}
