package TeaclubAppServer.services;

import TeaclubAppServer.repositories.ItemRepository;
import TeaclubAppServer.repositories.ShopCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import TeaclubAppServer.output.ItemToShopCartRequest;
import TeaclubAppServer.models.ShopCart;
import TeaclubAppServer.models.Item;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ShopCartRepository shopCartRepository;
    private final ItemRepository itemRepository;
    // private final EmailService emailService;

    @Transactional
    public void publish(ItemToShopCartRequest request) {
        Long shopCartId = request.getShopCartId();
        ShopCart shopCart = shopCartRepository.findByShopCartId(shopCartId);
        if (shopCart == null) {
            return;
        }
        Item item = new Item();
        item.setNameOfItem(request.getNameOfItem());
        item.setPriceOfItem(request.getPriceOfItem());
        item.setNumOfItem(request.getNumOfItem());
        itemRepository.save(item);

        // Testing Criteria API
        log.info("*****Criteria API*****");
        List<Item> itemsByShopCartId = itemRepository.findItemsByShopCartId(1L);
        log.info("-Items by ShopCart ID:");
        itemsByShopCartId
                .forEach(it ->
                        log.info(it.getNameOfItem() + " " + it.getPriceOfItem() + " " + it.getNumOfItem()
                                + " -- " + it.getShopCart().getId()) //.getShopCart_id()) //.getShopCart().getId()
                );

        List<Item> itemsByItemId = itemRepository
                .findItemsByItemId(1L);
        log.info("-Items by ID:");
        itemsByItemId
                .forEach(it ->
                        log.info(it.getNameOfItem() + " " + it.getPriceOfItem() + " " + it.getNumOfItem()
                                + " -- " + it.getShopCart().getId()) //.getShopCart_id()) //.getShopCart().getId()
                );
        /*
        emailService.send(
                "Item save",
                item.getId() + " from shopCart " + item.getShopCart().getId()
                + " has been saved to the database."
        );
        */
    }

    @Transactional
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}