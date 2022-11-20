package com.WebProject.Store;

import com.WebProject.Member.Member;
import com.WebProject.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsById(Long id);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(score) from score where score.id = store.id) score from store where id =:id" ,nativeQuery = true)
    Optional<Store> findById(@Param("id") Long id);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(score) from score where score.id = store.id) score from store", nativeQuery = true)
    List<Store> getListAll();

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(score) from score where score.id = store.id) score from store where store.address like %:address%", nativeQuery = true)
    List<Store> getListByAddress(@Param("address") String address);

    @Query(value = "select count(*) from store where store.address like %:address%", nativeQuery = true)
    Long getListByAddressCount(@Param("address") String address);

}
