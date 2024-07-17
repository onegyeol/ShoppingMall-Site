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
    private Long id;
    private int count;

}
