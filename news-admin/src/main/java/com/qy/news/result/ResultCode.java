package com.qy.news.result;

public enum ResultCode {

    SUCCESS(200, "成功"),
    ERROR(500, "服务器异常"),
    TOKEN_ERROR(999999, "没有携带token或token已被踢下线"),
    PERM_ERROR(999998, "该用户无权限"),
    ROLE_ERROR(999997, "该用户无角色"),
    USER_NO_EXIST_ERROR(001, "该用户不存在"),
    USER_OR_PASSWD_ERROR(002, "用户名或密码错误"),
    ILLEGAL_PARAMETER_ERROR(400, "参数不合法");

    private Integer code;
    private String msg;


    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }





}
