package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CART_ITEM")
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CART_ITEM_ID")
    private Long id; // 장바구니 아이템의 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY) //하나의 장바구니 안에 여러 아이템 존재
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int count; // 장바구니에서의 아이템 수량

    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);

        return cartItem;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count) {this.count = count;}
}
