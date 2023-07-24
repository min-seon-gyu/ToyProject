package com.WebProject.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "비밀번호")
    private String password;
}