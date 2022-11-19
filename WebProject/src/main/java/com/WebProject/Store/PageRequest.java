package com.WebProject.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private int page;
    private int size;
    private Sort.Direction direction;

    private String keyword;


    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? MAX_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public void setKeyword(String keyword){this.keyword = keyword;}

    public org.springframework.data.domain.PageRequest of(String keyword) {
        return org.springframework.data.domain.PageRequest.of(page -1, size, direction,keyword);
    }
}
