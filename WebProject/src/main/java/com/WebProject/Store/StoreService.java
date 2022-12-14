package com.WebProject.Store;

import com.WebProject.Member.FindEmailRequest;
import com.WebProject.Member.Member;
import com.WebProject.Member.MemberResponse;
import com.WebProject.comment.Comment;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Optional<Store> findStoreById(Long id){
        return storeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Store> getListAll(){
        return storeRepository.getListAll();
    }

    @Transactional(readOnly = true)
    public Long totalCount(){
        return storeRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Store> getListByAddress(String address){
        String result = null;
        if(address.equals("Jung-gu")){
            result = "중구";
        }
        else if(address.equals("Yuseong-gu")){
            result = "유성구";
        }
        else if(address.equals("Daedeok-gu")){
            result = "대덕구";
        }
        else if(address.equals("Dong-gu")){
            result = "동구";
        }
        else if(address.equals("Seo-gu")){
            result = "서구";
        }else {
            throw new BadRequestException("주소 양식이 잘못되었습니다.");
        }

        return storeRepository.getListByAddress(result);
    }

    @Transactional(readOnly = true)
    public Long getListByAddressCount(String address){
        String result = null;
        if(address.equals("Jung-gu")){
            result = "중구";
        }
        else if(address.equals("Yuseong-gu")){
            result = "유성구";
        }
        else if(address.equals("Daedeok-gu")){
            result = "대덕구";
        }
        else if(address.equals("Dong-gu")){
            result = "동구";
        }
        else if(address.equals("Seo-gu")){
            result = "서구";
        }else {
            throw new BadRequestException("주소 양식이 잘못되었습니다.");
        }
        return storeRepository.getListByAddressCount(result);
    }

    @Transactional(readOnly = true)
    public List<Store> getListByType(String type){
        String result = null;
        if(type.equals("Korean")){
            result = "한식";
        }
        else if(type.equals("Western")){
            result = "양식";
        }
        else if(type.equals("Chinese")){
            result = "중식";
        }
        else if(type.equals("Japanese")){
            result = "일식";
        }
        else if(type.equals("Dessert")){
            result = "디저트";
        }
        else if(type.equals("Snack")){
            result = "분식";
        }
        else {
            throw new BadRequestException("타입 양식이 잘못되었습니다.");
        }
        return storeRepository.getListByType(result);
    }

    @Transactional(readOnly = true)
    public Long getListByTypeCount(String type){
        String result = null;
        if(type.equals("Korean")){
            result = "한식";
        }
        else if(type.equals("Western")){
            result = "양식";
        }
        else if(type.equals("Chinese")){
            result = "중식";
        }
        else if(type.equals("Japanese")){
            result = "일식";
        }
        else if(type.equals("Dessert")){
            result = "디저트";
        }
        else if(type.equals("Snack")){
            result = "분식";
        }
        else {
            throw new BadRequestException("타입 양식이 잘못되었습니다.");
        }
        return storeRepository.getListByTypeCount(result);
    }

    @Transactional(readOnly = true)
    public List<Store> getListByName(String name){
        return storeRepository.getListByName(name);
    }
    @Transactional(readOnly = true)
    public Long getListByNameCount(String name){
        return storeRepository.getListByNameCount(name);
    }
}
