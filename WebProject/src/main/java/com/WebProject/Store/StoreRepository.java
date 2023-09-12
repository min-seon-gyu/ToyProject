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

    @Query(value = "select id, name, address, tell, operating_time, type, representative_menu, lat, lon, (select avg(value) from score where score.id = store.id) score from store where id =:id" ,nativeQuery = true)
    Optional<Store> findById(@Param("id") Long id);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store order by score desc;", nativeQuery = true)
    List<Store> getListAll();

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store WHERE MATCH(address) AGAINST(:address in boolean mode) order by score desc", nativeQuery = true)
    List<Store> getListByAddress(@Param("address") String address);

    @Query(value = "select count(*) from store WHERE MATCH(address) AGAINST(:address in boolean mode)", nativeQuery = true)
    Long getListByAddressCount(@Param("address") String address);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store where store.name like %:name% order by score desc", nativeQuery = true)
    List<Store> getListByName(@Param("name") String name);

    @Query(value = "select count(*) from store where store.name like %:name%", nativeQuery = true)
    Long getListByNameCount(@Param("name") String name);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store WHERE MATCH(type) AGAINST(:type in boolean mode) order by score desc", nativeQuery = true)
    List<Store> getListByType(@Param("type") String type);

    @Query(value = "select count(*) from store WHERE MATCH(type) AGAINST(:type in boolean mode)", nativeQuery = true)
    Long getListByTypeCount(@Param("type") String type);

}
