package com.WebProject.Store;

import com.WebProject.exception.BadRequestException;
import com.WebProject.score.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"상점관련 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @ApiOperation(value = "[Id] 상점 찾기 기능", notes = "[Id] 상점 찾기 API")
    @GetMapping("/store/id")
    public StoreResponse getStoreById(
            @ApiParam(value = "상점 Id", required = true)
            @RequestParam (name="id") Long id){
        Store store = storeService.findStoreById(id).orElseThrow(() -> new BadRequestException("상점을 찾을 수 없습니다."));
        return StoreResponse.of(store);
    }

    @ApiOperation(value = "[Address] 상점 찾기 기능", notes = "[Address] 상점 찾기 API")
    @GetMapping("/store/address")
    public List<StoreResponse> getStoreByAddress(
            @ApiParam(value = "상점 address", required = true)
            @RequestParam (name = "address") String address){
        List<Store> list = storeService.findStoreByAddress(address);
        return StoreResponse.of(list);
    }

    @ApiOperation(value = "상점 전체 수 가져오기 기능", notes = "상점 전체 수 가져오기 API")
    @GetMapping("/store/count")
    public Long getTotalCount(){
        return storeService.totalCount();
    }

   // @ApiOperation(value = "[Page] 상점 찾기 기능", notes = "[Page]상점 찾기 API")
   // @GetMapping("/store/list")
  //  public List<StoreResponse> getStoresByPage(
     //       @ApiParam(value = "PageRequest", required = true)
      //      @RequestBody PageRequest pageRequest){
      //  return StoreResponse.of(storeService.getStoreByPage(pageRequest).getContent());
    //}
}
