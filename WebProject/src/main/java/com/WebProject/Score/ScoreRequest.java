package com.WebProject.Score;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ScoreRequest {

    @ApiModelProperty(example = "상점 id")
    private long id;
    @ApiModelProperty(example = "평점")
    private int value;
}
