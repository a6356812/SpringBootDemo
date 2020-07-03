package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Hobby;
import com.bozhi.mapper.HobbyMapper;
import org.springframework.stereotype.Service;

@Service
public class HobbyServiceImpl extends ServiceImpl<HobbyMapper,Hobby> implements HobbyService {
}
