package com.WebProject.Store;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
public class StoreResponse {
    @ApiModelProperty(example = "Id")
    private Long id;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "주소")
    private String address;
    @ApiModelProperty(example = "연락처")
    private String tell;
    @ApiModelProperty(example = "운영시간")
    private String operating_time;
    @ApiModelProperty(example = "분류")
    private String type;
    @ApiModelProperty(example = "대표메뉴")
    private String representative_menu;
    @ApiModelProperty(example = "위도")
    private Double lat;
    @ApiModelProperty(example = "경도")
    private Double lon;
    @ApiModelProperty(example = "평점")
    private Double score;


    public static StoreResponse of(Store store) {
        if(store.getScore() == null){
            store.setScore(0.0);
        }
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getTell(),
                store.getOperating_time(),
                store.getType(),
                store.getRepresentative_menu(),
                store.getLat(),
                store.getLon(),
                store.getScore()
                );
    }

    public static List<StoreResponse> of(List<Store> stores){
        List<StoreResponse> lst = new ArrayList<>();
        for (Store data:stores) {
            lst.add(StoreResponse.of(data));
        }
        return lst;
    }
}
