package com.bozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bozhi.bean.Area;
import com.bozhi.bean.User;
import com.bozhi.common.base.BuildResult;
import com.bozhi.common.base.MessageCode;
import com.bozhi.common.base.Result;
import com.bozhi.service.AreaService;
import com.bozhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    /**
     * 根据area的父节点，查找出所有子节点
     * @param area 传入一个area对象
     * @return 若成功则返回SuccessResult携带List格式的Area数据，若失败则返回FailResult
     */
    @GetMapping("/selectProvince")
    public Result selectProvince(Area area){
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",area.getParentId());
        List<Area> list=areaService.list(queryWrapper);
        if(list!=null){
            return BuildResult.buildSuccess(list);
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }

    /**
     * 登陆，根据传入的User对象与数据库中进行对比
     * @return 若成功则返回SuccessResult并携带该User对象，若失败则返回FailResult
     */
    @PostMapping("/login")
    public Result login(User user){
        if(userService.login(user)){
            return BuildResult.buildSuccess(user);
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }

    /**
     * 登陆，根据传入的User对象与数据库中进行对比
     * @return 若成功则返回SuccessResult并携带该User对象，若失败则返回FailResult
     */
    @PostMapping("/cookieLogin")
    public Result cookieLogin(User user){
        if(userService.cookieLogin(user)){
            return BuildResult.buildSuccess(user);
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }
}
