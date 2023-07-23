package com.WebProject.Store;

import com.WebProject.comment.CommentResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DetailStoreResponse {

    @ApiModelProperty(example = "상점 데이터")
    private StoreResponse storeResponse;
    @ApiModelProperty(example = "코멘트 리스트")
    private List<CommentResponse> list;

}
