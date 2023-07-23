package com.WebProject.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class CommentRemoveRequest {
    @ApiModelProperty(example = "ano")
    private Long ano;
}
