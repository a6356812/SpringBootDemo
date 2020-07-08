package com.bozhi.runner;

import com.bozhi.bean.User;
import com.bozhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class ApplicationRunnerImpl implements ApplicationRunner{
    @Autowired
    private UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.saveAdmin();
    }
}
