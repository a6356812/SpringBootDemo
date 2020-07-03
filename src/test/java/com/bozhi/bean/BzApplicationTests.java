package com.bozhi.bean;

import com.bozhi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//class BzApplicationTests {
//    @Autowired
//    private UserService service;
//    @Test
//    void contextLoads() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = service.listUser();
//        for(User user:userList){
//            System.out.println(user.getName());
//        }
//    }
//
//}
