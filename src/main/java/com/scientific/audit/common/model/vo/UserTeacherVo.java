package com.scientific.audit.common.model.vo;


import lombok.Data;

@Data
public class UserTeacherVo{

    private Long id;

    private String userName;

    private byte role;

    private int directionId;

    private String directionName;


}
