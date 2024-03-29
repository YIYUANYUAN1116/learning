package com.xht.kettle;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.StringWriter;
import java.util.*;

@SpringBootTest
@Slf4j
public class FreeMarkerTest {

    @Test
    public void test2(){
        String path = "D:\\Development\\Tool\\Kettle\\file\\job1.kjb";

        File file = new File(path);
        if (file.exists()){
            log.info("存在");
        }else {
            log.info("不存在");
        }
    }

    @Test
    public void test3(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        System.out.println(JSON.toJSON(arrayList));
    }

    @Test
    public void test(){
        try {
            //1.获取数据
            List<Student> students = getStudent();

            //2.// 配置Freemarker

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setClassForTemplateLoading(this.getClass(), "/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);

            // 从配置中获取Freemarker模板
            Template temp = cfg.getTemplate("student.ftl");
            Map<String, List<Student>> root = new HashMap<>();
            root.put("students", students);


            // 使用Freemarker合并数据模型和模板
            StringWriter out = new StringWriter();
            temp.process(root, out);

            log.info(out.toString());
            // 使用Gson转换StringWriter的内容到JSON字符串
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(out.toString());

            // 输出JSON字符串
            System.out.println(jsonOutput);
            log.info(jsonOutput);
        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }


    private static  List<Student>  getStudent() {

        Student student1 = new Student();
        student1.setStudentNo("1001");
        student1.setName("张");
        student1.setSex("女");
        student1.setSubjects(Arrays.asList(new Subject("语文", 90), new Subject("数学", 92)));
        student1.setHobbys(Arrays.asList("唱歌", "跳舞"));
        // ...添加更多学生

        Student student2 = new Student();
        student2.setStudentNo("1002");
        student2.setName("李");
        student2.setSex("男");
        student2.setSubjects(Arrays.asList(new Subject("英语", 90), new Subject("地理", 92)));
        student2.setHobbys(Arrays.asList("抽烟", "喝酒"));
        // ...添加更多学生

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        return students;
    }
}
