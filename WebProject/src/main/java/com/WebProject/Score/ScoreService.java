package com.WebProject.Score;

import com.WebProject.Store.Store;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final EntityManager em;
    @Transactional
    public void addScore(ScoreRequest scoreRequest){
        Store store = em.find(Store.class, scoreRequest.getId());
        if(store == null) throw new BadRequestException("존재하지 않는 상점 id입니다.");

        int value = scoreRequest.getValue();
        if(value > 5  || value < 0 ) throw new BadRequestException("올바른 점수가 아닙니다.");

        Score score = Score.builder().id(scoreRequest.getId()).value(value).build();
        em.persist(score);
    }
}
