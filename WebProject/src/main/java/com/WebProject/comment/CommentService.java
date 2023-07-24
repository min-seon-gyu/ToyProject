package com.WebProject.comment;

import com.WebProject.Member.MemberResponse;
import com.WebProject.Store.Store;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager em;

    @Transactional
    public void addComment(CommentAddRequest commentRequest){
        Store store = em.find(Store.class, commentRequest.getId());
        if(store == null) throw new BadRequestException("존재하지 않는 상점 id 입니다.");

        Comment comment = Comment.builder()
                .id(commentRequest.getId())
                .content(commentRequest.getContent())
                .email(commentRequest.getEmail())
                .build();

        commentRepository.save(comment);
    }

    @Modifying
    @Transactional
    public void removeComment(long id){
        Comment comment = em.find(Comment.class, id);
        if(comment != null) em.remove(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getLstComment(Long id){
        return commentRepository.getLstComment(id);
    }

}
