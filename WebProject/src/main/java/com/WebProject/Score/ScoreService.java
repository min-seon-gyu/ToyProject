package com.WebProject.Score;

import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final  ScoreRepository scoreRepository;
    @Transactional
    public void addScore(ScoreRequest scoreRequest){
        if(scoreRepository.existByStoreId(scoreRequest.getId()) == 0){
            throw new BadRequestException("존재하지 않는 상점 id입니다.");
        }
        int score = scoreRequest.getScore();
        if(score > 5  || score < 0 ){
            throw new BadRequestException("올바른 점수가 아닙니다.");
        }
        scoreRepository.save(Score.builder().id(scoreRequest.getId()).score(scoreRequest.getScore()).build());
    }

}
