package TeaclubAppServer.services;

import TeaclubAppServer.repositories.ItemRepository;
import TeaclubAppServer.repositories.ShopCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import TeaclubAppServer.output.ItemToShopCartRequest;
import TeaclubAppServer.models.ShopCart;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopCartService {
    private final ShopCartRepository shopCartRepository;
    private final ItemRepository itemRepository;
    // private final EmailService emailService;

    @Transactional
    public void publish(ItemToShopCartRequest request) {
        Long shopCartId = request.getShopCartId();
        ShopCart shopCart = shopCartRepository.findByShopCartId(shopCartId);
        if (shopCart != null) {
            return;
        }
        shopCart = new ShopCart();
        shopCart.setId(request.getShopCartId());
        shopCartRepository.save(shopCart);
        /*
        emailService.send(
                "ShopCart save",
                shopCart.getShopCartId() + " has been saved to the database."
        );
        */
    }

    @Transactional
    public <T> T takeAllShopCarts(Function<List<ShopCart>, T> tooutput) {//На всякий случай, мб пригодится
        List<ShopCart> shopCarts = shopCartRepository.findAll();
        return tooutput.apply(shopCarts);
    }

    @Transactional
    public List<ShopCart> getAllShopCarts() {
        return shopCartRepository.findAll();
    }

    @Transactional
    public <T> T takeShopCartById(long id, Function<ShopCart, T> tooutput) {
        Optional<ShopCart> shopCart = shopCartRepository.findById(id);
        if (shopCart.isEmpty()) {
            return null;
        }
        return tooutput.apply(shopCart.get());
    }
}