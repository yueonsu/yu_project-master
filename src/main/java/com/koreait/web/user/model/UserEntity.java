package com.koreait.web.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private int gender;
    private String rdt;
}
