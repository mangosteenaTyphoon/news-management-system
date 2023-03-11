package com.news.core.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//生成有参数的构造方法
@NoArgsConstructor//生成无参
public class MyException extends RuntimeException{

    private Integer code;//状态码

    private String name;//异常信息
}
