package com.shopproject.response;

public class CommonReturnType {
    //对请求返回的请求处理结果"success"或"fail"
    private String status;

    //若status=success,则data返回前端需要的json数据,fail则返回公用的错误码格式
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType creat(Object result){
        return CommonReturnType.creat(result,"success");
    }
    public static CommonReturnType creat(Object result,String status){
        CommonReturnType type=new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
