package com.yd.constant;

/**
 * 状态码
 */
public enum StatusCodeEnum {

	SUCCESS("200", "成功"),
	
    SYS_ERROR("000", "系统错误"),
    INVALID_PARAM("001", "参数错误"),
    USERNAME_OR_PASSWORD_ERROR("002","用户名或密码错误"),
	NOT_LOGIN("003","未登录"),
	PERMISSION_DENIED("004","权限不足"),
	INVALID_TOKEN("005","token无效"),
	MANAGER_REMOTE_LOGIN("006","异地登录！请重新登录"),
	;

    private String code;
    private String message;

    StatusCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCodeEnum getByCode(String code) {
        for (StatusCodeEnum statusCode : StatusCodeEnum.values()) {
            if (statusCode.getCode().equals(code)) {
                return statusCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
