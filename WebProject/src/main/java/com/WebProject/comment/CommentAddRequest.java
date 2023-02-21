package com.WebProject.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddRequest {
    @ApiModelProperty(example = "상점 id")
    private Long id;
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "내용")
    private String content;
}
