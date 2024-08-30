package project.wideWebsite.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import project.wideWebsite.domain.Cart;
import project.wideWebsite.domain.CartItem;
import project.wideWebsite.domain.Item;
import project.wideWebsite.domain.Member;
import project.wideWebsite.dto.CartDetailDto;
import project.wideWebsite.dto.CartItemDto;
import project.wideWebsite.dto.CartOrderDto;
import project.wideWebsite.dto.OrderDto;
import project.wideWebsite.repository.CartItemRepository;
import project.wideWebsite.repository.CartRepository;
import project.wideWebsite.repository.ItemRepository;
import project.wideWebsite.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email){

        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findByMemberId(member.getId());

        // 장바구니가 없다면 생성
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // 해당 상품이 장바구니에 존재하지 않는다면 생성 후 추가
        if(cartItem == null){
            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
        } else{ // 해당 상품 존재 시 갯수만 증가
            cartItem.addCount(cartItemDto.getCount());
        }

        return cartItem.getId();
    }

    // 현재 로그인한 회원의 장바구니 상품들 조회
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new); // email 주소로 회원 찾음
        Cart cart = cartRepository.findByMemberId(member.getId()); // 회원 아이디로 장바구니 조회

        if(cart == null) return cartDetailDtoList;

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }

    //현재 로그인한 회원과 해당 장바구니 상품을 저장한 회원이 동일한지 검사
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){

        // 현재 로그인한 회원
        Member curMember = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        // 수량 변경 요청이 들어온 장바구니 상품의 회원
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    // 장바구니 상품 번호를 파라미터로 받아 삭제
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    // OrderDtoList 생성 + OrderService.orders() 호출
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        // CartOrderDto 객체를 이용해 cartItem 객체 조회
        // cartItem.itemId, cartItem.count 이용해 OrderDto 객체 생성
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);

        // 주문한 상품 제거
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
}
