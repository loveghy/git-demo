package com.qn.computersell.util;

import lombok.Data;

@Data
public class JsonResult<T> {
    //状态码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer code) {
        super();
        this.code = code;
    }

    public JsonResult(Throwable e) {
        super();
        this.message = e.getMessage();
    }

    public JsonResult(Integer code,T data) {
        super();
        this.code=code;
        this.data = data;
    }
}
