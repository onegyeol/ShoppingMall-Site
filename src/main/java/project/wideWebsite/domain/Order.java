package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id; //주문 고유 식별자

    private LocalDateTime date; //주문 날짜 및 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 (DEPART, MOVING, ARRIVE)
}
