package project.wideWebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // /main 경로로 들어오는 GET 요청을 처리하는 메서드
    @GetMapping("/main")
    public String home(){
        // view 이름을 반환 (form/main.html 템플릿을 렌더링)
        return "form/main";
    }

}
