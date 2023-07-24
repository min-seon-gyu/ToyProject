package com.WebProject.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class CommentAddRequest {
    @ApiModelProperty(example = "상점 id")
    private long id;
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "내용")
    private String content;
}
