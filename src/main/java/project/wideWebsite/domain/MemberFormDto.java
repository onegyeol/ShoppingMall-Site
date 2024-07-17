package project.wideWebsite.domain;

//회원가입 화면으로부터 넘어오는 가입정보 담을 DTO

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto {
    private String name;
    private String password;
    private String address;
    private String email;
}
