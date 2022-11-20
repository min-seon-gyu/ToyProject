package com.WebProject.comment;

import com.WebProject.Store.Store;
import com.WebProject.Store.StoreResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponse {

    @ApiModelProperty(example = "Id")
    private Long ano;

    @ApiModelProperty(example = "연락처")
    private String email;

    @ApiModelProperty(example = "내용")
    private String content;

    @ApiModelProperty(example = "작성시간")
    private String writeTime;

    public CommentResponse(Long ano, String email, String content, String writeTime) {
        this.ano = ano;
        this.email = email;
        this.content = content;
        this.writeTime = writeTime;
    }

    public static CommentResponse of(Comment comment){
        return new CommentResponse(
                comment.getAno(),
                comment.getEmail(),
                comment.getContent(),
                comment.getWrite_time()
        );
    }

    public static List<CommentResponse> of(List<Comment> comments){
        List<CommentResponse> lst = new ArrayList<>();
        for (Comment data:comments) {
            lst.add(CommentResponse.of(data));
        }
        return lst;
    }
}
