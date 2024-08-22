package project.wideWebsite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.wideWebsite.domain.Item;
import project.wideWebsite.dto.ItemSearchDto;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
