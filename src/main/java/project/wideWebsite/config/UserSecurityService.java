package project.wideWebsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.wideWebsite.domain.Member;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.domain.Role;
import project.wideWebsite.service.UserDetailsService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository; // MemberRepository 주입

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 사용자를 조회합니다.
        Optional<Member> memberOptional = this.memberRepository.findByEmail(email);

        // 사용자를 찾지 못한 경우 예외를 던집니다.
        if (memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 조회된 사용자 정보를 가져옵니다.
        Member member = memberOptional.get();

        // 사용자의 역할에 따라 권한을 설정합니다.
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
        );

        // UserDetails 객체를 생성하여 반환합니다.
        return new User(member.getEmail(), member.getPassword(), authorities);
    }
}
