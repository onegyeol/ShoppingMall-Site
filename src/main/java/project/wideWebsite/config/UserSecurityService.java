package project.wideWebsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.wideWebsite.domain.Member;
import project.wideWebsite.domain.MemberRepository;
import project.wideWebsite.domain.Role;
import project.wideWebsite.domain.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOptional = this.memberRepository.findByEmail(email);

        if(memberOptional.isEmpty()){
            throw new UsernameNotFoundException("not find user");
        }

        Member member = memberOptional.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(member.getRole())){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else{
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }

        return new User(member.getName(), member.getPassword(), authorities);
    }
}
