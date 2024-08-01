package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.wideWebsite.dto.MemberFormDto;

//회원정보 저장하는 Member 생성

@Entity
@Table(name = "MEMBER")
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id; //회원 고유 식별자

    private String password; //암호화된 비밀번호
    private String name; //회원 이름
    private String address; //회원 주소

    @Column(unique = true)
    private String email; //회원 이메일

    @Enumerated(EnumType.STRING)
    private Role role; //회원 상태 (ADMIN, USER)

    // MemberFormDto를 사용하여 Member 객체를 생성하고 비밀번호를 암호화
    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER); //기본 역할을 USER로 설정

        return member;
    }

}
