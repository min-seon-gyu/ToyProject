package com.WebProject.score;

import com.WebProject.Store.StoreRepository;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

    private final  ScoreRepository scoreRepository;
    @Transactional
    public void addScore(ScoreRequest scoreRequest){
        if(!scoreRepository.existsById(scoreRequest.getId())){
            throw new BadRequestException("존재하지 않는 상점 id입니다.");
        }
        if(scoreRequest.getScore() > 5  || scoreRequest.getScore() < 0 ){
            throw new BadRequestException("올바른 점수가 아닙니다.");
        }
        scoreRepository.save(Score.builder().id(scoreRequest.getId()).score(scoreRequest.getScore()).build());
    }

}
