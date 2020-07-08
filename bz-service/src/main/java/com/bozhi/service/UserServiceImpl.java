package com.bozhi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Area;
import com.bozhi.bean.Position;
import com.bozhi.bean.User;
import com.bozhi.bean.dto.UserDto;
import com.bozhi.common.util.RedisUtil;
import com.bozhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PositionService positionService;

    @Autowired
    private AreaService areaService;

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Autowired
    private RedisUtil redisUtil;

    public static final String LOGIN_INFO="login_info";


    @Override
    public List<UserDto> listUserDto() {
        List<UserDto> list = userMapper.listUserDto();
        list.forEach(item->{
            item.setAreaName((String) areaService.listFullArea(item.getArea()).get("areaName"));
            item.setAreaList((List<Area>) areaService.listFullArea(item.getArea()).get("areaList"));
        });
        return list;
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
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");//设置日期格式
            SimpleDateFormat value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            redisUtil.setString(LOGIN_INFO+realUser.getId()+"-"+df.format(new Date()),value.format(new Date()));
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean cookieLogin(User user) {
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

    @Override
    public List<String> getLoginInfo(Long id) {
        Set<String> loginInfoSet=redisUtil.getStringRedisTemplate().keys(LOGIN_INFO+id.toString()+"-"+"*");
        List<String> list=new LinkedList<>();
        if(loginInfoSet==null){
            return null;
        }
        loginInfoSet.forEach(item->{
            list.add(redisUtil.getString(item));
        });
        return list;
    }

    @Override
    public boolean saveAdmin() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","admin");
        User user=userMapper.selectOne(queryWrapper);
        if(user==null){
            QueryWrapper<Position> queryWrapperPosition = new QueryWrapper<>();
            queryWrapperPosition.eq("name","admin");
            Position position=positionService.getOne(queryWrapperPosition);
            if(position==null){
                position=new Position();
                position.setName("admin");
                positionService.save(position);
            }
            user=new User();
            user.setName("admin");
            user.setPassword("Aa123456");
            user.setEmail("admin@admin.com");
            user.setArea(3L);
            user.setPosition(position.getId());
            userMapper.insert(user);
            return true;
        }

        return false;
    }

    @Override
    public List<User> listByPosition(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position",id);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public UserDto selectUserDtoById(Long id) {
        UserDto userDto=userMapper.selectUserDtoById(id);
        userDto.setAreaName((String) areaService.listFullArea(userDto.getArea()).get("areaName"));
        userDto.setAreaList((List<Area>) areaService.listFullArea(userDto.getArea()).get("areaList"));
        return userDto;
    }

    @Override
    public UserDto selectUserDtoByName(String name) {
        UserDto userDto=userMapper.selectUserDtoByName(name);
        if(userDto!=null){
            userDto.setAreaName((String) areaService.listFullArea(userDto.getArea()).get("areaName"));
            userDto.setAreaList((List<Area>) areaService.listFullArea(userDto.getArea()).get("areaList"));
        }
        return userDto;
    }

    public boolean updateUser(User user,QueryWrapper queryWrapper){
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            if(userMapper.update(user,queryWrapper)>0){
                dataSourceTransactionManager.commit(transactionStatus);//提交
                return true;
            }else {
                //回滚
                dataSourceTransactionManager.rollback(transactionStatus);
                return false;
            }
        }catch (Exception e){
            //回滚
            dataSourceTransactionManager.rollback(transactionStatus);
            return false;
        }
    }
}
