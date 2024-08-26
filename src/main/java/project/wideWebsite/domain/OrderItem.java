package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDER_ITEM")
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id; // 주문 항목의 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY) //item과 다대일 관계
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //order과 다대일 관계 (양방향매핑)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int count; // 주문 상품 수량

    private int price; // 주문 상품의 가격

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return price * count;
    }
}