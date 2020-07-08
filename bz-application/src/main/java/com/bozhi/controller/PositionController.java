package com.bozhi.controller;

import com.bozhi.bean.Position;
import com.bozhi.bean.User;
import com.bozhi.common.base.BuildResult;
import com.bozhi.common.base.MessageCode;
import com.bozhi.common.base.Result;
import com.bozhi.service.PositionService;
import com.bozhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionService positionService;
    @Autowired
    private UserService userService;

    @RequestMapping("/listPosition")
    public Result listPosition(){
        List<Position> list=positionService.list();
        if(list!=null){
            return BuildResult.buildSuccess(list);
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }
    @RequestMapping("/savePosition")
    public Result savePosition(Position position){
        if(positionService.save(position)){
            return BuildResult.buildSuccess();
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }

    @RequestMapping("/removePosition")
    public Result removePosition(Long id){
        List<User> list=userService.listByPosition(id);
        if(list.size()>0){
            return BuildResult.buildFail(MessageCode.REMOVE_POSITION_FAILED);
        }else if(positionService.removeById(id)){
            return BuildResult.buildSuccess();
        }
        return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
    }
}
