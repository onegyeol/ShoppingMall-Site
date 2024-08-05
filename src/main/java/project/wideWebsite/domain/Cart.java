package project.wideWebsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // JPA 엔티티 클래스임을 나타냅니다.
@Table(name = "CART") // 데이터베이스에서 매핑될 테이블의 이름을 지정합니다.
@Getter // Lombok 어노테이션으로 모든 필드에 대한 getter 메서드를 자동으로 생성합니다.
@Setter // Lombok 어노테이션으로 모든 필드에 대한 setter 메서드를 자동으로 생성합니다.
public class Cart {

    @Id // 이 필드가 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue // 기본 키 값이 자동으로 생성됨을 나타냅니다.
    @Column(name="CART_ID") // 데이터베이스 테이블의 열 이름을 지정합니다.
    private Long id; // 장바구니의 고유 식별자

    @OneToOne // 일대일 매핑
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
