package project.wideWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;
import project.wideWebsite.domain.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId); // 이미지가 잘 저장되었는지 확인 위해
}
