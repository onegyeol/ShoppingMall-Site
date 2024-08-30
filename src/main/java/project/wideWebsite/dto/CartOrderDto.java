package project.wideWebsite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 장바구니 페이지에서 주문할 상품이 데이터를 위한 dto
@Getter @Setter
public class CartOrderDto {

    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;
}
