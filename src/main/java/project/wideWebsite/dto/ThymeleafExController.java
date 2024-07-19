package project.wideWebsite.dto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.wideWebsite.domain.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("product detail");
        itemDto.setItemNm("test product1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String thymeleafExample02(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("product detail" + i);
            itemDto.setItemNm("test product" + i);
            itemDto.setPrice(10000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx02";
    }
    @GetMapping("/ex03")
    public String thymeleafExample03(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("product detail" + i);
            itemDto.setItemNm("test product" + i);
            itemDto.setPrice(10000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String thymeleafExample04(){
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String thymeleafExample05(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping("/ex06")
    public String thymeleafExample06(){
        return "thymeleafEx/thymeleafEx06";
    }

}
