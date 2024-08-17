package project.wideWebsite.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import project.wideWebsite.repository.ItemRepository;
import project.wideWebsite.repository.MemberRepository;
import project.wideWebsite.repository.OrderItemRepository;
import project.wideWebsite.repository.OrderRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    public Item createItem(){
        Item item = new Item();
        item.setItemNm("test product");
        item.setPrice(10000);
        item.setItemDetail("details");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(10);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        return item;
    }

    public Order createOrder(){
        Order order = new Order();
        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setPrice(1000);
            orderItem.setOrder(order); // 외래키 값 지정

            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){

        // 1. Order 생성
        Order order = new Order();

        for(int i=0; i<3; i++){
            // 2. Item 생성 및 저장
            Item item = this.createItem();
            itemRepository.save(item);

            // 3. OrderItem 생성 및 초기화
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setPrice(1000);
            orderItem.setOrder(order); // 외래키 값 지정

            // 4. Order에 OrderItem 추가
            order.getOrderItems().add(orderItem);
        }

        // 5. 3개의 OrderItem 객체를 담고있는 Order 객체 저장
        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        Long orderItem_id = order.getOrderItems().get(0).getId();
        order.getOrderItems().remove(0); //객체 지향중심의 DB 작업
        em.flush(); //실제 DB에 반영
        em.clear(); // 캐시된 엔티티 제거

        // EntityManager를 사용하여 OrderItem의 존재 여부 확인
        OrderItem deletedOrderItem = em.find(OrderItem.class, orderItem_id);

        // OrderItem이 제거되어 null인지 확인
        assertEquals(null, deletedOrderItem);
    }

    @Test
    @DisplayName("즉시 로딩 테스트")
    public void eagerLoadingTest(){
        Order order = this.createOrder();
        Long orderItem_id = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItem_id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class : "+  orderItem.getOrder().getClass()); //이 때는 프록시 객체 넣어둠
        System.out.println("===========================");
        orderItem.getOrder().getOrderDate(); //실제 메소드 수행시에 디비에 쿼리문 날아가서 조회됨
        System.out.println("===========================");
    }
}
