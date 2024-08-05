package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wideWebsite.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
