package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bozhi.bean.User;

import java.util.List;

/**
 * 用户的service类
 * @author chengzhongxiang
 */
public interface UserService extends IService<User> {
    /**
     * 查找列表中所有用户
     * @return 返回一个List集合，集合中为User类
     */
    public List<User> listUser();

    public boolean login(User user);
}
