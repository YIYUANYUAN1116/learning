package com.xht.html;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test123 {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        System.out.println(format);

        String a = "data-w-e-is-void=123 " +"data-w-e-is-void";
        System.out.println(a.replaceAll("data-w-e-is-void",""));
    }
}
