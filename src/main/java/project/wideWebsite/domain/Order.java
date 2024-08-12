package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id; // 주문 고유 식별자

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate; // 주문 날짜 및 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 (DEPART, MOVING, ARRIVE)

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // orderItem과 일대다 관계
    private List<OrderItem> orderItems = new ArrayList<>();

    // 추가적인 편의 메소드
    public void addOrderItem(OrderItem orderItem) { //주문 항목 추가
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) { //주문 항목 제거
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}