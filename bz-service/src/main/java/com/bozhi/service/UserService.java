package com.bozhi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bozhi.bean.User;
import com.bozhi.bean.dto.UserDto;

import java.util.List;

/**
 * 用户的service类
 * @author chengzhongxiang
 */
public interface UserService extends IService<User> {

    public List<UserDto> listUserDto();

    public boolean login(User user);
    public boolean saveAdmin();
    public List<User> listByPosition(Long id);
    public UserDto selectUserDtoById(Long id);
    public UserDto selectUserDtoByName(String name);
    public boolean updateUser(User user, QueryWrapper queryWrapper);
    public boolean cookieLogin(User user);
    public List<String> getLoginInfo(Long id);
}
