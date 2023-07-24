package com.WebProject.Store;


import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
    public List<Store> getListByAddress(AddressType type){
        String result = getAddressString(type);
        return storeRepository.getListByAddress(result);
    }

    @Transactional(readOnly = true)
    public Long getListByAddressCount(AddressType type){
        String result = getAddressString(type);
        return storeRepository.getListByAddressCount(result);
    }

    @Transactional(readOnly = true)
    public List<Store> getListByType(StoreType type){
        String result = getTypeString(type);
        return storeRepository.getListByType(result);
    }

    @Transactional(readOnly = true)
    public Long getListByTypeCount(StoreType type){
        String result = getTypeString(type);
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

    private String getAddressString(AddressType type){
        String result = null;
        if(type.equals(AddressType.Jung_gu)){
            result = "중구";
        }
        else if(type.equals(AddressType.Yuseong_gu)){
            result = "유성구";
        }
        else if(type.equals(AddressType.Daedeok_gu)){
            result = "대덕구";
        }
        else if(type.equals(AddressType.Dong_gu)){
            result = "동구";
        }
        else if(type.equals(AddressType.Seo_gu)){
            result = "서구";
        }else {
            throw new BadRequestException("주소 양식이 잘못되었습니다.");
        }
        return result;
    }

    private String getTypeString(StoreType type){
        String result = null;
        if(type.equals(StoreType.Korean)){
            result = "한식";
        }
        else if(type.equals(StoreType.Western)){
            result = "양식";
        }
        else if(type.equals(StoreType.Chinese)){
            result = "중식";
        }
        else if(type.equals(StoreType.Japanese)){
            result = "일식";
        }
        else if(type.equals(StoreType.Dessert)){
            result = "디저트";
        }
        else if(type.equals(StoreType.Snack)){
            result = "분식";
        }
        else {
            throw new BadRequestException("타입 양식이 잘못되었습니다.");
        }
        return result;
    }
}
