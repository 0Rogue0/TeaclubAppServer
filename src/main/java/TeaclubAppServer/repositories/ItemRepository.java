package TeaclubAppServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import TeaclubAppServer.models.ShopCart;
import TeaclubAppServer.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemFilter {
    void deleteFromShopCart(ShopCart shopCart, Item id);
}