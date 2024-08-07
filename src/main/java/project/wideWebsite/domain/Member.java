package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.wideWebsite.dto.MemberFormDto;

import java.util.Collection;
import java.util.Collections;

//회원정보 저장하는 Member 생성

@Entity
@Table(name = "MEMBER")
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id; //회원 고유 식별자

    private String password; //암호화된 비밀번호
    private String name; //회원 이름
    private String address; //회원 주소

    @Column(unique = true)
    private String email; //회원 이메일

    @Enumerated(EnumType.STRING)
    private Role role; //회원 상태 (ADMIN, USER)

    // Spring Security의 UserDetailsService와 호환되도록 설정
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

}
