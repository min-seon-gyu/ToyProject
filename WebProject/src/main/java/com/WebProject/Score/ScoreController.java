package com.WebProject.Score;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"평점관련 API"})
@RestController
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @ApiOperation(value = "평점 추가 기능", notes = "평점 추가 API")
    @PostMapping("/score")
    public void addScore(
            @ApiParam(value = "ScoreRequest", required = true)
            @RequestBody ScoreRequest scoreRequest){
        scoreService.addScore(scoreRequest);
    }
}
