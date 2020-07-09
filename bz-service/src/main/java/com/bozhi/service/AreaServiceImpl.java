package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Area;
import com.bozhi.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    /**
     * 根据传入的id，查询出该地址的完整路径，以及拼接的字符串
     * @param id
     * @return 以Map的形式返回，areaName为完整路径字符串，areaList为完整路径链表
     */
    @Override
    public Map<String,Object> listFullArea(Long id) {
        List<Area> list = areaMapper.selectFullArea(id);
        StringBuffer area=new StringBuffer();
        list.forEach(item->{
            area.append(item.getAreaName());
        });
        Map<String,Object> map=new HashMap<>();
        map.put("areaName",area.toString());
        map.put("areaList",list);
        return map;
    }
}
