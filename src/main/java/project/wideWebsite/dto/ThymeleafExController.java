package project.wideWebsite.dto;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.wideWebsite.domain.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/main")
public class ThymeleafExController {

    @GetMapping
    public String thymeleafExample06(){
        return "form/main";
    }

}
