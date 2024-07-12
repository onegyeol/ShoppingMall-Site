package project.wideWebsite.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //회원가입 시 중복여부 판단 위해 이메일로 회원 검사
    Member findByEmail(String email);
}
