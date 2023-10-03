package com.WebProject.comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"댓글관련 API"})
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @ApiOperation(value = "댓글 추가 기능", notes = "댓글 추가 API")
    @PostMapping("/comment")
    public CommentResponse addScore(
            @ApiParam(value = "CommentRequest", required = true)
            @RequestBody CommentRequest commentRequest){
        return commentService.addComment(commentRequest);
    }

    @ApiOperation(value = "댓글 삭제 기능", notes = "댓글 삭제 API")
    @DeleteMapping("/comment")
    public boolean removeScore(
            @ApiParam(value = "CommentRequest", required = true)
            @RequestParam long id){
        return commentService.removeComment(id);
    }
}
