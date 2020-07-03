package com.jack.api;

public enum ResponseCode {

    SUCCESS("成功",20000),
    FALSE("成功",10000),
    URL_NOT_FIND("URL没有发现", 404),//这个只在全局异常里面使用，查询数据为空接口也要返回SUCCESS
    REQUEST_METHOD_NOT_SUPPORTED("Request method不支持", 405),
    SERVER_ERROR("程序异常", 1001),
    PARAMETER_ERROR("参数错误",1002),
    TOKEN_ERROR("token不存在或失效",50012),
    REMOTE_SERVER_ERROR("远程服务不可用",1004),
    CODE_FALSE("验证码错误",1005),
    USER_NAME_PASSWORD_FALSE("用户名密码错误",1006),
    USER_NOINTO("用户未登录",1007),
    USER_UNDEFINED("用户不存在",1008),
    USER_NOT_ACTIVE("用户未激活,请联系管理员进行激活！",1009),
    PASSWORD_AND_USERNAME_FAIL("用户名或密码不正确！",1010);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ResponseCode(String name, int index) {
        this.name = name;
        this.index = index;
    }
    //覆盖方法
    @Override
    public String toString() {
        return this.index+"_"+this.name;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
