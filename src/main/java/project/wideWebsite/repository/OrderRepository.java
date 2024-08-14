package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wideWebsite.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
