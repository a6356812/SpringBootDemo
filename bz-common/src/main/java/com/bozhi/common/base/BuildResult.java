package com.bozhi.common.base;


/**
 * 快速构建Result
 **/
public class BuildResult {

    /**
     * 构建成功不带数据
     * @return
     */
    public static Result buildSuccess(){
     return buildSuccess(null);
    }
    /**
     * 带数据
     * @param data
     * @return
     */
    public static Result buildSuccess(Object data){
      Result result=new Result();
      result.setCode(20000);
      result.setStatus("success");
      result.setData(data);
      return result;
    }

    /**
     * 通过枚举构建失败信息
     * @param messageCode
     * @return
     */
    public static Result buildFail(MessageCode messageCode){
        return  buildFail(messageCode.getCode(),messageCode.getMessage());
    }


    /**
     * 构建失败
     * @param code
     * @param message
     * @return
     */
    public static Result buildFail(Integer code, String message){
        Result result=new Result();
        result.setStatus("fail");
        result.setMessage(message);
        result.setCode(code);
        return result;
    }



}
