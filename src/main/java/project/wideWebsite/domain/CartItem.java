package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CART_ITEM")
@Getter @Setter
public class CartItem {
    @Id @GeneratedValue
    @Column(name = "CART_ITEM_ID")
    private Long id; // 장바구니 아이템의 고유 식별자

    @ManyToOne //하나의 장바구니 안에 여러 아이템 존재
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int count; // 장바구니에서의 아이템 수량
}
