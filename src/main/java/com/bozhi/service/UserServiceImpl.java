package com.bozhi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Area;
import com.bozhi.bean.User;
import com.bozhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 查找所有的用户，以List的格式返回
     * @return 若成功则返回SuccessResult携带List格式的User数据，若失败则返回null
     */
    @Override
    public List<User> listUser() {
        return userMapper.selectList(null);
    }

    /**
     * 登陆，根据传入的User对象与数据库中进行对比
     * @return 若验证成功则返回true，若失败则返回false
     */
    @Override
    public boolean login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",user.getName());
        User realUser=userMapper.selectOne(queryWrapper);
        if(realUser.getPassword().equals(user.getPassword())){
            return true;
        }
        else {
            return false;
        }
    }
}
