package com.example.demo.controller;

import com.example.demo.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @RequestMapping(value = "/login", produces = "application/json; charset=utf8", method = RequestMethod.POST)
    @ResponseBody
    public boolean login(@RequestBody User user){
        if(user.getEmail().equals("김회윤") && user.getPassword().equals("1234")){
            System.out.println("true");
            return true;
        }else {
            System.out.println("false");
            return false;
        }
    }

    @RequestMapping(value = "/signup", produces = "application/json; charset=utf8", method = RequestMethod.POST)
    @ResponseBody
    public void create(@RequestBody User user ){
        System.out.println(user.getEmail());
        System.out.println(user.getBirth());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getNumber());
    }

}
