package com.WebProject.Member;

import com.WebProject.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
    Optional<Member> findByNameAndRrn(String name, String rrn);
    boolean existsByEmailAndNameAndRrn(String email, String name, String rrn);
}