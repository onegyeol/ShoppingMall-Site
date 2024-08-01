package project.wideWebsite.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemDto {
    private Long id; // 아이템의 고유 식별자
    private String itemNm; // 아이템의 이름
    private Integer price; // 아이템의 가격
    private String itemDetail; // 아이템의 상세 설명
    private String sellStatCd; // 판매 상태 코드 (예: 판매 중, 품절 등)
    private LocalDateTime regTime; // 아이템 등록 시간
    private LocalDateTime updateTime; // 아이템 수정 시간
}
