package com.qy.news.entity.dto;


import lombok.Data;

@Data
public class UserAuthInfoReqDTO {

    private Integer type;

    private String account;

    private String password;


}
