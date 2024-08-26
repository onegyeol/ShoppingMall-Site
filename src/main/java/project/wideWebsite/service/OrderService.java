package project.wideWebsite.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.wideWebsite.domain.Item;
import project.wideWebsite.domain.Member;
import project.wideWebsite.domain.Order;
import project.wideWebsite.domain.OrderItem;
import project.wideWebsite.dto.OrderDto;
import project.wideWebsite.repository.ItemRepository;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();

        // OrderItem.createOrderItem -> static method
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        // Order.createOrder -> static method
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
