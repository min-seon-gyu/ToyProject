package com.WebProject.Score;

import com.WebProject.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT count(*) FROM store where id=:id limit 1", nativeQuery = true)
    Long existByStoreId(@Param("id")Long id);
}
