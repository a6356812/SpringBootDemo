package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bozhi.bean.Area;

import java.util.List;
import java.util.Map;

public interface AreaService extends IService<Area> {
    Map<String,Object> listFullArea(Long id);
    public List<Area> listTreeArea(Long id);
}
