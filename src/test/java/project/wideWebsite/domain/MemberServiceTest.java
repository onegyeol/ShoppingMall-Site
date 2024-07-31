package project.wideWebsite.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import project.wideWebsite.dto.MemberFormDto;
import project.wideWebsite.service.MemberService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("signup test")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("duplication test")
    public void validateDuplicateMember() {
        // Given
        Member member1 = createMember();  // Create the first member
        Member member2 = createMember();  // Create the second member with the same email

        // When
        memberService.saveMember(member1);  // Save the first member

        // Then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);  // Try to save the second member which should throw an exception
        });

        // Check if the exception message is correct
        assertEquals("already exists as a member", thrown.getMessage());
    }

}
