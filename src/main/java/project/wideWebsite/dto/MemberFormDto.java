package project.wideWebsite.dto;

//회원가입 화면으로부터 넘어오는 가입정보 담을 DTO

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.") // 이름 필드는 빈값이나 null이 아닌 값이어야 합니다.
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.") // 비밀번호 필드는 빈값이 아닌 값이어야 합니다.
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.") // 비밀번호는 최소 8자, 최대 16자여야 합니다.
    private String password;

    @NotEmpty(message = "주소는 필수 입력 값입니다.") // 주소 필드는 빈값이 아닌 값이어야 합니다.
    private String address;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.") // 이메일 필드는 빈값이 아닌 값이어야 합니다.
    @Email(message = "이메일 형식으로 입력해주세요.") // 이메일 형식이 맞는지 검사합니다.
    private String email;
}
