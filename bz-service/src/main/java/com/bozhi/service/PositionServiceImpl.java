package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Position;
import com.bozhi.mapper.PositionMapper;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

}
