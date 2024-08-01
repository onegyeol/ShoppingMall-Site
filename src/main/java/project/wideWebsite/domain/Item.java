package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "ITEM")
@Getter @Setter
@ToString
public class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id; //상품의 고유 식별자

    @Column(nullable = false, length = 50)
    private String name; //상품 이름

    @Column(nullable = false)
    private int price; //상품 가격

    @Column(nullable = false)
    private int stockNumber; //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 정보

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private LocalDateTime regTime; //상품 등록 시간

    private LocalDateTime updateTime; //상품 수정 시간
}
