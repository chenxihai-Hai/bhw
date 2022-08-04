package com.bhw.common.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {


private T data;
//状态编码
private int retCode;
//状态说明
private String message;

private Page page;

}
