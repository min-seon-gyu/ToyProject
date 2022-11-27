package com.WebProject.comment;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponse {

    @ApiModelProperty(example = "Id")
    private Long ano;

    @ApiModelProperty(example = "이름")
    private String name;

    @ApiModelProperty(example = "내용")
    private String content;

    @ApiModelProperty(example = "작성시간")
    private String writeTime;

    @ApiModelProperty(example = "작성유무")
    private Boolean canDelete;

    public CommentResponse(Long ano, String name, String content, String writeTime, Boolean canDelete) {
        this.ano = ano;
        this.name = name;
        this.content = content;
        this.writeTime = writeTime;
        this.canDelete = canDelete;
    }

    public static CommentResponse of(Comment comment, String email){
        Boolean canDelete = email.equals(comment.getEmail());
        return new CommentResponse(
                comment.getAno(),
                comment.getEmail(),
                comment.getContent(),
                comment.getWrite_time(),
                canDelete
        );
    }

    public static List<CommentResponse> of(List<Comment> comments, String email){
        List<CommentResponse> lst = new ArrayList<>();
        for (Comment data:comments) {
            lst.add(CommentResponse.of(data, email));
        }
        return lst;
    }
}
