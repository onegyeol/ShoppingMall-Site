package project.wideWebsite.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("save item test")
    public void createItemList(){
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setName("test product" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("test product detail" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }


    }

    @Test
    @DisplayName("find product name")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByName("test product1");
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }
}

