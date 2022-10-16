package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String HelloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello_template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String HelloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public List<Hello> helloApi() {

        List<Hello> aa = new ArrayList<>();
        Hello hello = new Hello();
        hello.setName("민선규");
        hello.setAge(27);
        hello.setJob("개발자");
        aa.add(hello);

        Hello hello2 = new Hello();
        hello2.setName("민선규");
        hello2.setAge(27);
        hello2.setJob("개발자");
        aa.add(hello2);
        return aa;
    }


    static class Hello {
        private String name;
        private int age;
        private String job;

            public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }
    }

}
