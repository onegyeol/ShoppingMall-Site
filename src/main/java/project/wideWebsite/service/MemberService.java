package project.wideWebsite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.wideWebsite.domain.Member;
import project.wideWebsite.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        return memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalStateException("already exists as a member");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        Member member = memberOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword()) // 암호화된 비밀번호 사용
                .roles(member.getRole().name()) // Enum을 String으로 변환
                .build();
    }
}
