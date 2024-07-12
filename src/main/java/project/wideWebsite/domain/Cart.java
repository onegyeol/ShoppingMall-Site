package project.wideWebsite.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CART")
@Getter @Setter
public class Cart {

    @Id @GeneratedValue
    @Column(name="CART_ID")
    private Long id;
}
