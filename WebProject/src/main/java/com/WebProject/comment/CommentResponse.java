package com.WebProject.comment;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponse {

    @ApiModelProperty(example = "Id")
    private Long ano;

    @ApiModelProperty(example = "이름")
    private String name;

    @ApiModelProperty(example = "내용")
    private String content;

    @ApiModelProperty(example = "작성유무")
    private Boolean canDelete;


    public static CommentResponse of(Comment comment, String email){
        Boolean canDelete = email.equals(comment.getEmail());
        return new CommentResponse(
                comment.getAno(),
                comment.getEmail(),
                comment.getContent(),
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
