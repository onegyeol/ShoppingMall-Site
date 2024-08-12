package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wideWebsite.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
