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

    /**
     * 查找所有的职位，以list的格式返回
     * @return 若成功则返回SuccessResult携带List<Position>，若失败则返回FailResult
     */
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

    /**
     * 新增职位，参数为传入的positon属性
     * @return 若成功则返回SuccessResult，若失败则返回FailResult
     */
    @RequestMapping("/savePosition")
    public Result savePosition(Position position){
        if(positionService.save(position)){
            return BuildResult.buildSuccess();
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }
    }

    /**
     * 根据传入的id，删除职位
     * @return 若成功则返回SuccessResult，若已存在拥有该职位的用户则返回REMOVE_POSITION_FAILED
     *  若失败则返回FailResult
     */
    @RequestMapping("/removePosition")
    public Result removePosition(Long id){
        Integer code=positionService.removePosition(id);
        if(code==MessageCode.REQUEST_SUCCESS.getCode()) {
            return BuildResult.buildSuccess();
        }
        else if(code==MessageCode.REMOVE_POSITION_FAILED.getCode()){
            return BuildResult.buildFail(MessageCode.REMOVE_POSITION_FAILED);
        }
        else {
            return BuildResult.buildFail(MessageCode.REQUEST_FAILED);
        }

    }
}
