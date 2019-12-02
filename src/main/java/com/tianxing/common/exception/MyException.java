package com.tianxing.common.exception;

/**
 * 自定义异常类
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-11-27 11:31
 **/

public class MyException extends RuntimeException {

    private static final long serialVersionUID = 6234758836404631385L;

    /**
     * 异常状态码
     */
    private Integer code;

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }
}
