package TeaclubAppServer.output;

import org.springframework.stereotype.Component;
import TeaclubAppServer.models.ShopCart;
import TeaclubAppServer.models.Item;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Outputer {
    public List<ItemResponse> toItemResponseList(List<Item> items) {
        return items.stream().map(item -> {
            ItemResponse response = new ItemResponse();
            response.setId(item.getId());
            response.setNameOfItem(item.getNameOfItem());
            response.setPriceOfItem(item.getPriceOfItem());
            response.setNumOfItem(item.getNumOfItem());
            return response;
        }).collect(Collectors.toList());
    }

    public ShopCartResponse toShopCartResponse(ShopCart shopCart) {
        ShopCartResponse response = new ShopCartResponse();
        response.setId(shopCart.getId());
        response.setItems(toItemResponseList(shopCart.getItems()));
        return response;
    }

    public List<ShopCartResponse> toShopCartResponseList(List<ShopCart> shopCarts) {
        //Опять же на будущее, если захочу для себя/владельца ресторана, какую-то статистику делать
        return shopCarts.stream()
                .map(this::toShopCartResponse)
                .collect(Collectors.toList());
    }
}