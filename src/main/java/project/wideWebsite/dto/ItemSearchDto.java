package project.wideWebsite.dto;

import lombok.Getter;
import lombok.Setter;
import project.wideWebsite.domain.ItemSellStatus;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";

}
