package com.WebProject.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "비밀번호")
    private String password;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "생년월일")
    private String birth;
    @ApiModelProperty(example = "핸드폰 번호")
    private String number;
}
