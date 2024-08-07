package project.wideWebsite.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import project.wideWebsite.repository.MemberRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // 테스트 전 데이터베이스 초기화
        memberRepository.deleteAll();

        // 테스트 사용자 데이터 생성 및 저장
        Member member = new Member();
        member.setName("Test User");
        member.setEmail("test@example.com");
        member.setAddress("123 Test Street");
        member.setPassword(passwordEncoder.encode("testpassword")); // 비밀번호 암호화
        member.setRole(Role.USER);

        // 데이터베이스에 사용자 저장
        memberRepository.save(member);
    }

    @Test
    public void loginWithCorrectCredentials_shouldSucceed() throws Exception {
        mockMvc.perform(post("/members/login")
                        .param("email", "test@example.com")  // 올바른 이메일
                        .param("password", "testpassword")        // 올바른 비밀번호
                        .with(csrf()))                                // CSRF 토큰 포함
                .andExpect(status().is3xxRedirection())                // 리디렉션 발생
                .andExpect(redirectedUrl("/main"));                    // 성공 시 리디렉션 URL 확인
    }

    @Test
    public void loginWithIncorrectCredentials_shouldFail() throws Exception {
        mockMvc.perform(post("/members/login")
                        .param("email", "correctEmail@example.com")  // 올바른 이메일
                        .param("password", "wrongPassword")          // 잘못된 비밀번호
                        .with(csrf()))                                // CSRF 토큰 포함
                .andExpect(status().is3xxRedirection())                // 리디렉션 발생
                .andExpect(redirectedUrl("/members/login/error"));     // 실패 시 리디렉션 URL 확인
    }

}