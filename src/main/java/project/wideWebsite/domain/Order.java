package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.Lint;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id; //주문 고유 식별자

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LocalDateTime date; //주문 날짜 및 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 (DEPART, MOVING, ARRIVE)

    @OneToMany(mappedBy = "order") // ORDER_ITEM 테이블의 order 필드에 매핑
    private List<OrderItem> orderItems = new ArrayList<>();

}
