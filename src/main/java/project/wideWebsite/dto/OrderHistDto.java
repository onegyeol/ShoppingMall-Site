package project.wideWebsite.dto;

import lombok.Getter;
import lombok.Setter;
import project.wideWebsite.domain.Order;
import project.wideWebsite.domain.OrderStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); //주문 정보 내에 주문 상품 정보들 담기 위함

    public OrderHistDto(Order order){ //주문 정보 담기 위한 객체 생성
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }
}
