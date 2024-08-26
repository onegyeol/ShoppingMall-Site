package project.wideWebsite.domain;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import project.wideWebsite.dto.OrderDto;
import project.wideWebsite.repository.ItemRepository;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.repository.OrderRepository;
import project.wideWebsite.service.OrderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("test product");
        item.setPrice(10000);
        item.setItemDetail("test product detail");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);

        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        // 상품 상세 페이지 화면에서 넘어오는 값 설정
        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        // 주문 객체 데이터베이스에 저장
        Long orderId = orderService.order(orderDto, member.getEmail());

        // 저장된 주문 객체 조회
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        // 1. 데이터베이스에 저장된 주문 객체에서 주문 상품 추출
        List<OrderItem> orderItems = order.getOrderItems();

        // 2. 위에서 만든 주문 상품 총 가격
        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }

}
