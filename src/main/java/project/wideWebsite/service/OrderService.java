package project.wideWebsite.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.wideWebsite.domain.*;
import project.wideWebsite.dto.OrderDto;
import project.wideWebsite.dto.OrderHistDto;
import project.wideWebsite.dto.OrderItemDto;
import project.wideWebsite.repository.ItemImgRepository;
import project.wideWebsite.repository.ItemRepository;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){

        // 상품과 회원 조회 시 존재하지 않으면 예외 발생
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

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders){ // 들어온 주문들
            OrderHistDto orderHistDto = new OrderHistDto(order); //주문 정보 담음
            List<OrderItem> orderItems = order.getOrderItems(); //주문 상품들 담음
            for(OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.
                        findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl()); // 상품 주문 객체 생성
                orderHistDto.addOrderItemDto(orderItemDto); //주문 아이템 추가
            }
            orderHistDtos.add(orderHistDto); // 상품 주문 정보 리스트에 담음
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);

    }
}
