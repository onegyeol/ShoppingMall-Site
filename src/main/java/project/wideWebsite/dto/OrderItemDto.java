package project.wideWebsite.dto;

import lombok.Getter;
import lombok.Setter;
import project.wideWebsite.domain.OrderItem;

@Getter @Setter
public class OrderItemDto {

    private String itemNm;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getPrice();
        this.imgUrl = imgUrl;
    }
}
