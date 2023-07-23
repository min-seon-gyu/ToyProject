package com.WebProject.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PasswordUpdateRequest {
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "비밀번호")
    private String password;
}