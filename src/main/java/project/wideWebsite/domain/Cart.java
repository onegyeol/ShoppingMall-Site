package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART") // 데이터베이스에서 매핑될 테이블의 이름을 지정합니다.
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue
    @Column(name="CART_ID")
    private Long id; // 장바구니의 고유 식별자

    @OneToOne // 일대일 매핑
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 회원 1명당 1개의 장바구니를 갖기에 처음 장바구니에 담을 경우 해당 회원의 장바구니 생성
    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.member = member;
        return cart;
    }


}
