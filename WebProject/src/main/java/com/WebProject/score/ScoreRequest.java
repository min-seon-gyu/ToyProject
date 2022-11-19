package com.WebProject.score;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequest {

    @ApiModelProperty(example = "상점 id")
    private Long id;
    @ApiModelProperty(example = "평점")
    private Double score;
}
