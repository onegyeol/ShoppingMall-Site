package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wideWebsite.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //회원가입 시 중복여부 판단 위해 이메일로 회원 검사
    Optional<Member> findByEmail(String email);
}
