package com.WebProject.Store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class ListStoreResponse {
    @ApiModelProperty(example = "상점 리스트")
    private List<StoreResponse> list;
    @ApiModelProperty(example = "리스트 개수")
    private Long count;

}
