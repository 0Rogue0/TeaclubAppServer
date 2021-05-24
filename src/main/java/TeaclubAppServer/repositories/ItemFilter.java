package TeaclubAppServer.repositories;

import TeaclubAppServer.models.Item;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ItemFilter {
    List<Item> findItemsByShopCartId(Long shopCartId);
    List<Item> findItemsByItemId(Long itemId);
    List<Item> findItemsByNameOfItem(String nameOfItem);
    List<Item> findItemsByPriceOfItem(String priceOfItem);
}