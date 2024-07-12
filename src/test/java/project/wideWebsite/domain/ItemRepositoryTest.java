package project.wideWebsite.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("save item test")
    public void createItemTest(){
        Item item = new Item();
        item.setName("test");
        item.setPrice(10000);

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }
}

