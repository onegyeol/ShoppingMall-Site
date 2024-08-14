package project.wideWebsite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import project.wideWebsite.domain.ItemImg;

@Getter @Setter
public class ItemImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){ //ItemImg 엔티티 객체 -> ItemImgDto 객체로 변환
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
