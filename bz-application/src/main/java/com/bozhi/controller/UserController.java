package com.bozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bozhi.bean.Hobby;
import com.bozhi.bean.User;
import com.bozhi.bean.dto.UserDto;
import com.bozhi.common.base.BuildResult;
import com.bozhi.common.base.MessageCode;
import com.bozhi.common.base.Result;
import com.bozhi.service.AreaService;
import com.bozhi.service.HobbyService;
import com.bozhi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HobbyService hobbyService;
    @Autowired
    private AreaService areaService;

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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",user.getName());
        if(userService.list(queryWrapper).size()>0){
            return BuildResult.buildFail(MessageCode.SAVE_USER_FAILED);
        }
        if(userService.save(user)){
            return BuildResult.buildSuccess();
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }


    @GetMapping("/selectUserByName")
    public Result saveUser(String name){
        UserDto userDto=userService.selectUserDtoByName(name);
        if(userDto!=null){
            return BuildResult.buildSuccess(userDto);
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
    public Result listUser(Integer page,Integer size){
        PageHelper.startPage(page,size);
        List<UserDto> list=userService.listUserDto();
        PageInfo pageInfo=new PageInfo<>(list);
        if(list!=null){
            return BuildResult.buildSuccess(pageInfo);
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }


    @GetMapping("/selectUserDtoById")
    public Result selectUserDtoById(Long id){
        UserDto userDto=userService.selectUserDtoById(id);
        if(userDto!=null){
            return BuildResult.buildSuccess(userDto);
        }
        else{
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }

    @PostMapping("/updateUser")
    public Result updateUser(User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",user.getId());

        if(userService.updateUser(user,queryWrapper)){
            return BuildResult.buildSuccess();
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

    @GetMapping("/listLoginInfo")
    public Result listLoginInfo(Long id){
        List<String> list=userService.getLoginInfo(id);
        return BuildResult.buildSuccess(list);
    }


}
