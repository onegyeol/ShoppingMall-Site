package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;
import project.wideWebsite.domain.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId); // 이미지가 잘 저장되었는지 확인 위해
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn); // 구매 내역 페이지에서 주문 상품의 대표 이미지를 위한 조회
}
