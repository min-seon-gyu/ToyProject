package com.WebProject.Store;

import com.WebProject.comment.CommentResponse;
import com.WebProject.comment.CommentService;
import com.WebProject.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"상점관련 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final CommentService commentService;

    @ApiOperation(value = "상점 정보 기능", notes = "상점 정보 API")
    @GetMapping("/store/id")
    public DetailStoreResponse getStoreById(
            @ApiParam(value = "상점 Id", required = true)
            @RequestParam (name="id") Long id){
        Store store = storeService.findStoreById(id).orElseThrow(() -> new BadRequestException("상점을 찾을 수 없습니다."));
        return DetailStoreResponse.builder().storeResponse(StoreResponse.of(store)).list(CommentResponse.of(commentService.getLstComment(id))).build();
    }

    @ApiOperation(value = "상점 전체 리스트 기능", notes = "상점 전체 리스트 API")
    @GetMapping("/store/all")
    public ListStoreResponse getStoreAll(){
        List<Store> list = storeService.getListAll();
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(storeService.totalCount()).build();
    }

    @ApiOperation(value = "상점 구별 리스트 기능", notes = "상점 구별 리스트 API")
    @GetMapping("/store/address")
    public ListStoreResponse getStoreByAddress(
            @ApiParam(value = "상점 주소(구)", required = true)
            @RequestParam (name = "address") String address){
        List<Store> list = storeService.getListByAddress(address);
        return ListStoreResponse.builder().list(StoreResponse.of(list)).count(storeService.getListByAddressCount(address)).build();
    }

}
