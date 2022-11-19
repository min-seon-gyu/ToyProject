package com.WebProject.Store;

import com.WebProject.Member.Member;
import com.WebProject.Member.MemberResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    public StoreResponse(Long id, String name, String address, String tell, String operating_time, String type, String representative_menu, Double lat, Double lon, Double score) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tell = tell;
        this.operating_time = operating_time;
        this.type = type;
        this.representative_menu = representative_menu;
        this.lat = lat;
        this.lon = lon;
        this.score = score;
    }

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
