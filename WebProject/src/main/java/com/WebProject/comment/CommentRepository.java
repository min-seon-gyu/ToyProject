package com.WebProject.comment;

import com.WebProject.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT count(*) FROM store where id=:id", nativeQuery = true)
    Long existByStoreId(@Param("id")Long id);

    @Query(value = "select ano, id, email, content, write_time from comment where id = :id", nativeQuery = true)
    List<Comment> getLstComment(@Param("id") Long id);
}
