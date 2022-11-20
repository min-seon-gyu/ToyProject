package com.WebProject.comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"댓글관련 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @ApiOperation(value = "댓글 추가 기능", notes = "댓글 추가 API")
    @PostMapping("/comment/add")
    public CommentResponse addScore(
            @ApiParam(value = "CommentRequest", required = true)
            @RequestBody CommentAddRequest commentAddRequest){
        return commentService.addComment(commentAddRequest);
    }

    @ApiOperation(value = "댓글 삭제 기능", notes = "댓글 삭제 API")
    @PostMapping("/comment/remove")
    public void removeScore(
            @ApiParam(value = "CommentRequest", required = true)
            @RequestBody CommentRemoveRequest commentRemoveRequest){
        commentService.removeComment(commentRemoveRequest);
    }
}
