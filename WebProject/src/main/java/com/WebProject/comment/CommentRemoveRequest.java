package com.WebProject.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRemoveRequest {
    @ApiModelProperty(example = "Id")
    private Long ano;
}
