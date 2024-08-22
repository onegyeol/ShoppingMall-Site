package project.wideWebsite.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.wideWebsite.domain.Item;
import project.wideWebsite.domain.ItemSellStatus;
import project.wideWebsite.domain.QItem;
import project.wideWebsite.dto.ItemSearchDto;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private JPAQueryFactory queryFactory;

    // 생성자 DI를 이용한 EntityManager 주입
    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if ("all".equals(searchDateType) || searchDateType == null) {
            return null;
        } else if ("id".equals(searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if ("1w".equals(searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if ("1m".equals(searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if ("6m".equals(searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    // 상품 상태에 대한 조회 조건
    private BooleanExpression searchSellStatus(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    //상품명에 대한 조회 조건
private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if("itemNm".equals(searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        }

        return null;
}
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        // queryfactory 이용해 querydsl 쿼리문 생성
        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatus(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 조회 대상 리스트 결과
        List<Item> content = results.getResults();

        // 조회 대상 리스트 개수
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
