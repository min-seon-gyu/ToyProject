package com.WebProject.Store;

import com.WebProject.Member.FindEmailRequest;
import com.WebProject.Member.Member;
import com.WebProject.Member.MemberResponse;
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
    public List<Store> findStoreByAddress(String address){
        String result = null;
        if(address.equals("jung-gu")){
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
    public Long totalCount(){
        return storeRepository.count();
    }

}
