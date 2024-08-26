package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.atn.SemanticContext;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate; // 주문 날짜 및 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 (DEPART, MOVING, ARRIVE)

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // orderItem과 일대다 관계, 모든 CASCADE 적용, 고아객체 제거
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

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;

        // 각 상품마다 TotalPrice를 구하고 모두 더함
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    public void cancelOrder(){
        this.status = OrderStatus.CANCEL; // 주문 취소 시 상태 변경

        for(OrderItem orderItem : orderItems){
            orderItem.cancel(); //OrderItem.cancel() 호출
        }
    }
}