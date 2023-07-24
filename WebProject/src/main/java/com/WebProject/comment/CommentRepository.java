package com.WebProject.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select ano, id, email, content from comment where id = :id", nativeQuery = true)
    List<Comment> getLstComment(@Param("id") Long id);
}
