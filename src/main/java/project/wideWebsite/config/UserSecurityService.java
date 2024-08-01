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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository; // MemberRepository 주입

    // 이메일로 사용자 정보를 조회하고 UserDetails를 반환하는 메서드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 사용자를 조회합니다.
        Optional<Member> memberOptional = this.memberRepository.findByEmail(email);

        // 사용자를 찾지 못한 경우 예외를 던집니다.
        if(memberOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 조회된 사용자 정보를 가져옵니다.
        Member member = memberOptional.get();

        // 권한 리스트를 생성합니다.
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 역할에 따라 권한을 설정합니다.
        if("admin".equals(member.getRole())){
            // 역할이 "admin"인 경우 ADMIN 권한을 추가합니다.
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            // 그렇지 않은 경우 USER 권한을 추가합니다.
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }

        // UserDetails 객체를 생성하여 반환합니다.
        // 사용자의 이름, 비밀번호, 권한 리스트를 설정합니다.
        return new User(member.getName(), member.getPassword(), authorities);
    }
}
