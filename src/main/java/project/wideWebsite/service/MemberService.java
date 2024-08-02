package project.wideWebsite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.wideWebsite.domain.Member;
import project.wideWebsite.repository.MemberRepository;

import java.util.Optional;

@Service // 이 클래스가 Spring의 서비스 컴포넌트임을 나타냅니다.
@Transactional // 모든 메서드에서 트랜잭션을 지원합니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository; // Member 엔티티를 데이터베이스에서 관리하기 위한 리포지토리
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

    // 새 회원을 저장합니다.
    public Member saveMember(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        return memberRepository.save(member); // 암호화된 비밀번호와 함께 회원 저장
    }

    // 중복된 회원이 있는지 검증합니다.
    public void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalStateException("already exists as a member"); // 중복된 이메일이 발견되면 예외 발생
        }
    }

    // Spring Security의 UserDetailsService 인터페이스 구현
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 이메일로 사용자를 찾지 못하면 예외 발생
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        // UserDetails 객체를 생성하여 Spring Security에 사용자 정보를 제공합니다.
        return User.builder()
                .username(member.getEmail()) // 이메일을 사용자 이름으로 설정
                .password(member.getPassword()) // 암호화된 비밀번호를 설정
                .roles(member.getRole().name()) // 역할을 String으로 변환하여 설정
                .build();
    }
}
