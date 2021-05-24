package TeaclubAppServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import TeaclubAppServer.models.ShopCart;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {
    ShopCart findByShopCartId(Long shopCartId);
    //ShopCart findByShopCart_owner(String ownerName);
}