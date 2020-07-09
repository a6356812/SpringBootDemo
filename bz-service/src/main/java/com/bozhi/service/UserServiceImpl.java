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
    /**
     * 手动事务支持
     */
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Autowired
    private RedisUtil redisUtil;

    public static final String LOGIN_INFO="login_info";

    /**
     * 以List<UserDto>形式返回所有用户信息
     * @return 返回用户
     */
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

    /**
     * 用于cookie登陆，相比正常登陆减少了记录redis的代码
     * @param user
     * @return 若登陆成功则返回true,反之则返回false
     */
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

    /**
     * 根据id，查找redis中该用户所有的登录记录
     * @param id
     * @return 若没有记录则返回null,有记录则以List<String>形式返回
     */
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

    /**
     * 储存admin用户以及admin职位，若为空，则自动新增
     * @return 若新增成功则返回true,若没有新增则返回false
     */
    @Override
    public boolean saveAdmin() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","admin");
        //查找是否有名为admin的用户
        User user=userMapper.selectOne(queryWrapper);
        if(user==null){
            QueryWrapper<Position> queryWrapperPosition = new QueryWrapper<>();
            queryWrapperPosition.eq("name","admin");
            //查找是否有名为admin的职位
            Position position=positionService.getOne(queryWrapperPosition);
            if(position==null){
                //新增职位，赋予初始值
                position=new Position();
                position.setName("admin");
                positionService.save(position);
            }
            //新增用户，赋予初始值
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

    /**
     * 根据id查询所有的职位
     * @param id
     * @return 若查询到了则返回List<User>,若没有则返回null
     */
    @Override
    public List<User> listByPosition(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position",id);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 根据id查询单个UserDto
     * @param id
     * @return 若查询到了则返回UserDto,若没有则返回null
     */
    @Override
    public UserDto selectUserDtoById(Long id) {
        UserDto userDto=userMapper.selectUserDtoById(id);
        userDto.setAreaName((String) areaService.listFullArea(userDto.getArea()).get("areaName"));
        userDto.setAreaList((List<Area>) areaService.listFullArea(userDto.getArea()).get("areaList"));
        return userDto;
    }

    /**
     * 根据名称查找用户
     * @param name
     * @return 若查询到了则返回UserDto,若没有则返回null
     */
    @Override
    public UserDto selectUserDtoByName(String name) {
        UserDto userDto=userMapper.selectUserDtoByName(name);
        if(userDto!=null){
            userDto.setAreaName((String) areaService.listFullArea(userDto.getArea()).get("areaName"));
            userDto.setAreaList((List<Area>) areaService.listFullArea(userDto.getArea()).get("areaList"));
        }
        return userDto;
    }

    /**
     * 根据User以及条件QueryWrapper更新用户
     * @param user
     * @param queryWrapper
     * @return 若更新成功则返回true,若失败则返回false
     */
    public boolean updateUser(User user,QueryWrapper queryWrapper){
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            userMapper.update(user,queryWrapper);
            //提交
            dataSourceTransactionManager.commit(transactionStatus);
            return true;
        }catch (Exception e){
            //回滚
            dataSourceTransactionManager.rollback(transactionStatus);
            return false;
        }
    }
}
