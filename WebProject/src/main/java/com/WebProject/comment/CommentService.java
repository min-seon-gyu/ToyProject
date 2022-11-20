package com.WebProject.comment;

import com.WebProject.Member.MemberResponse;
import com.WebProject.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse addComment(CommentAddRequest commentRequest){
        if(commentRepository.existByStoreId(commentRequest.getId()) == 0){
            throw new BadRequestException("존재하지 않는 상점 id 입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분");
        String formattedNow = now.format(formatter);

        Comment comment = Comment.builder()
                .id(commentRequest.getId())
                .content(commentRequest.getContent())
                .email(commentRequest.getEmail())
                .write_time(formattedNow)
                .build();

        comment = commentRepository.save(comment);
        return CommentResponse.of(comment);
    }

    @Modifying
    @Transactional
    public void removeComment(CommentRemoveRequest commentRemoveRequest){
        if(commentRepository.findById(commentRemoveRequest.getAno()).get() == null){
            throw new BadRequestException("존재하지 않는 댓글 id 입니다.");
        }
        commentRepository.deleteById(commentRemoveRequest.getAno());
    }

    @Transactional(readOnly = true)
    public List<Comment> getLstComment(Long id){
        return commentRepository.getLstComment(id);
    }

}
