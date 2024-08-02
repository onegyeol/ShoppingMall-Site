package project.wideWebsite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.wideWebsite.domain.Member;
import project.wideWebsite.domain.Role;
import project.wideWebsite.dto.MemberFormDto;
import project.wideWebsite.service.MemberService;

@Controller
@RequestMapping("/members") // /members 경로로 들어오는 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class MemberController {

    private final MemberService memberService; // 회원 관련 서비스
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 PasswordEncoder


    // 회원 가입 페이지를 보여주는 메서드
    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto()); // 빈 MemberFormDto 객체를 모델에 추가
        return "member/memberForm"; // 회원 가입 폼 뷰를 반환
    }

    // 회원 가입 폼 제출을 처리하는 메서드
    @PostMapping("/new")
    public String newMember(@Valid MemberFormDto memberFormDto,
                            BindingResult bindingResult, Model model){
        // 폼 검증 오류가 있는 경우 다시 폼 페이지로 이동
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try{
            /*// Member 객체 생성 및 저장
            Member member = Member.createMember(memberFormDto, passwordEncoder);*/

            Member member = new Member();
            member.setEmail(memberFormDto.getEmail());
            member.setPassword(memberFormDto.getPassword());
            member.setName(memberFormDto.getName());
            member.setAddress(memberFormDto.getAddress());
            member.setRole(Role.USER);

            memberService.saveMember(member); // 회원 정보 저장
        }catch(IllegalStateException e){
            // 예외가 발생한 경우 오류 메시지를 모델에 추가하고 다시 폼 페이지로 이동
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        // 회원 가입이 성공한 경우 메인 페이지로 리다이렉트
        return "redirect:/main";
    }

    // 로그인 페이지를 보여주는 메서드
    @GetMapping("/login")
    public String login(){
        return "member/login"; // 로그인 뷰를 반환
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("LoginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }
}
