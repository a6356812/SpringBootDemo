package com.bozhi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bozhi.bean.Position;
import com.bozhi.bean.User;
import com.bozhi.common.base.MessageCode;
import com.bozhi.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private UserService userService;

    /**
     * 根据传入的id删除职位，若有用户正属于该职位则不允许删除
     * @param id
     * @return 若成功则返回20000，若已存在用户则返回3100，若异常则返回3000
     */
    @Override
    public Integer removePosition(Long id) {
        List<User> list= userService.listByPosition(id);
        try {
            if(list!=null&&list.size()>0){
                return MessageCode.REQUEST_SUCCESS.getCode();
            }
            else {
                return MessageCode.REMOVE_POSITION_FAILED.getCode();
            }
        }catch (Exception e){
            return MessageCode.REQUEST_FAILED.getCode();
        }


    }
}
