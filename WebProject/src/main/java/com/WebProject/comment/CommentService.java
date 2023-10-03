package com.WebProject.comment;

import com.WebProject.Store.Store;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager em;

    @Transactional
    public CommentResponse addComment(CommentRequest commentRequest){
        Store store = em.find(Store.class, commentRequest.getId());
        if(store == null) throw new BadRequestException("존재하지 않는 상점 id 입니다.");

        Comment comment = Comment.builder()
                .id(commentRequest.getId())
                .content(commentRequest.getContent())
                .email(commentRequest.getEmail())
                .build();

        commentRepository.save(comment);
        return CommentResponse.of(comment, commentRequest.getEmail());
    }

    @Modifying
    @Transactional
    public boolean removeComment(long id){
        Comment comment = em.find(Comment.class, id);
        if(!Objects.isNull(comment)){
            em.remove(comment);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Comment> getLstComment(Long id){
        return commentRepository.getLstComment(id);
    }

}
