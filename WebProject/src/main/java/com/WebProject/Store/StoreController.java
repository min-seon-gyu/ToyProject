package com.WebProject.Store;

import com.WebProject.Member.MemberDetails;
import com.WebProject.comment.CommentResponse;
import com.WebProject.comment.CommentService;
import com.WebProject.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"상점관련 API"})
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final CommentService commentService;

    @ApiOperation(value = "상점 정보 기능", notes = "상점 정보 API")
    @GetMapping("/store")
    public DetailStoreResponse getStoreById(
            @ApiParam(value = "상점 Id", required = true)
            @RequestParam Long id,
            @AuthenticationPrincipal MemberDetails memberDetails){
        String email = "None";
        if(memberDetails != null){
            email = memberDetails.getMember().getEmail();
        }
        Store store = storeService.findStoreById(id).orElseThrow(() -> new BadRequestException("상점을 찾을 수 없습니다."));
        return DetailStoreResponse.builder().storeResponse(StoreResponse.of(store)).list(CommentResponse.of(commentService.getLstComment(id), email)).build();
    }

    @ApiOperation(value = "상점 전체 리스트 기능", notes = "상점 전체 리스트 API")
    @GetMapping("/store/all")
    public ListStoreResponse getStoreAll(){
        List<Store> list = storeService.getListAll();
        Long count = storeService.totalCount();
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(count).build();
    }

    @ApiOperation(value = "상점 구별 리스트 기능", notes = "상점 구별 리스트 API")
    @GetMapping("/store/address")
    public ListStoreResponse getStoreByAddress(
            @ApiParam(value = "동구 : Dong_gu, 서구 : Seo_gu, 중구 : Jung_gu, 유성구 : Yuseong_gu, 대덕구 : Daedeok_gu", required = true)
            @RequestParam AddressType type){
        List<Store> list = storeService.getListByAddress(type);
        Long count = storeService.getListByAddressCount(type);
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(count).build();
    }

    @ApiOperation(value = "상점 타입별 리스트 기능", notes = "상점 타입별 리스트 API")
    @GetMapping("/store/type")
    public ListStoreResponse getStoreByType(
            @ApiParam(value = "한식 : Korean, 양식 : Western, 중식 : Chinese, 일식 : Japanese, 디저트 : Dessert, 분식 : Snack", required = true)
            @RequestParam StoreType type){
        List<Store> list = storeService.getListByType(type);
        Long count = storeService.getListByTypeCount(type);
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(count).build();
    }

    @ApiOperation(value = "상점 이름 검색 기능", notes = "상점 이름 검색 API")
    @GetMapping("/store/name")
    public ListStoreResponse getStoreByName(
            @ApiParam(value = "상점 이름", required = true)
            @RequestParam String name){
        List<Store> list = storeService.getListByName(name);
        Long count = storeService.getListByNameCount(name);
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(count).build();
    }
}
