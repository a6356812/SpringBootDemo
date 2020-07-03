package com.bozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bozhi.bean.Area;
import com.bozhi.bean.Hobby;
import com.bozhi.bean.User;
import com.bozhi.common.BuildResult;
import com.bozhi.common.MessageCode;
import com.bozhi.common.Result;
import com.bozhi.service.AreaService;
import com.bozhi.service.HobbyService;
import com.bozhi.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HobbyService hobbyService;

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
     * 查找所有的爱好，以List的格式返回
     * @return 若成功则返回SuccessResult携带List格式的Hobby数据，若失败则返回FailResult
     */
    @GetMapping("/listHobby")
    public Result listHobby(){
        List<Hobby> list=hobbyService.list();
        return BuildResult.buildSuccess(list);
    }

    /**
     * 根据传入的User对象，将其存入数据库的User表中
     * @return 若成功则返回SuccessResult，若失败则返回FailResult
     */
    @PostMapping("/saveUser")
    public Result saveUser(User user){
        if(userService.save(user)){
            return BuildResult.buildSuccess();
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }
    /**
     * 查找所有的用户，以List的格式返回
     * @return 若成功则返回SuccessResult携带List格式的User数据，若失败则返回FailResult
     */
    @GetMapping("/listUser")
    public Result listUser(){
        List<User> list=userService.listUser();
        if(list!=null){
            return BuildResult.buildSuccess(list);
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }
    /**
     * 删除用户，根据传入的id逻辑删除该用户
     * @return 若成功则返回SuccessResult，若失败则返回FailResult
     */
    @GetMapping("/deleteUser")
    public Result deleteUser(Long id){

        if(userService.removeById(id)){
            return BuildResult.buildSuccess();
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
}
