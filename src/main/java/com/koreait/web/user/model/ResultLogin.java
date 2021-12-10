package com.koreait.web.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultLogin {
    private int result;
    private UserEntity loginUser;
}
