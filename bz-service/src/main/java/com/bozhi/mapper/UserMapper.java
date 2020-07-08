package com.bozhi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bozhi.bean.User;
import com.bozhi.bean.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select u.id,u.name,u.area,u.email,u.hobby,u.created,u.position,p.name positionName from user as u,position as p\n" +
            "where u.position=p.id and u.status=0")
    public List<UserDto> listUserDto();

    @Select("select u.id,u.name,u.password,u.area,u.email,u.hobby,u.created,u.position,p.name positionName from user as u,position as p\n" +
            "where u.position=p.id and u.status=0 and u.id=#{id}")
    public UserDto selectUserDtoById(@Param(value = "id") Long id);

    @Select("select u.id,u.name,u.password,u.area,u.email,u.hobby,u.created,u.position,p.name positionName from user as u,position as p\n" +
            "where u.position=p.id and u.status=0 and u.name=#{name}")
    public UserDto selectUserDtoByName(@Param(value = "name") String name);

}
