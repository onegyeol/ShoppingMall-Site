package project.wideWebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/main") // /main 경로로 들어오는 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class MainController {

    // /main 경로로 들어오는 GET 요청을 처리하는 메서드
    @GetMapping
    public String home(){
        // view 이름을 반환 (form/main.html 템플릿을 렌더링)
        return "form/main";
    }

}
