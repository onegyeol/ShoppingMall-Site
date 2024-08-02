package project.wideWebsite.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import project.wideWebsite.dto.MemberFormDto;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void loginWithIncorrectEmail_shouldFail() {
        // Arrange
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("correct@example.com");
        memberFormDto.setName("Test User");
        memberFormDto.setAddress("123 Test Street");
        memberFormDto.setPassword("correctPassword");

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberRepository.save(member);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            memberService.loadUserByUsername("wrong@example.com");
        });
    }

    @Test
    void loginWithIncorrectPassword_shouldFail() {
        // Arrange
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("correct@example.com");
        memberFormDto.setName("Test User");
        memberFormDto.setAddress("123 Test Street");
        memberFormDto.setPassword("correctPassword");

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberRepository.save(member);

        // Act
        UserDetails userDetails = memberService.loadUserByUsername(memberFormDto.getEmail());

        // Assert
        assertTrue(!passwordEncoder.matches("wrongPassword", userDetails.getPassword()));
    }

}
