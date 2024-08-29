package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.wideWebsite.domain.CartItem;
import project.wideWebsite.dto.CartDetailDto;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // 장바구니 페이지로 전달할 CartDetailDto 리스트를 쿼리 하나로 조회
    @Query("select new project.wideWebsite.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.item.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}
