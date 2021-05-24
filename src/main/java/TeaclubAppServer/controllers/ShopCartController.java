package TeaclubAppServer.controllers;

import TeaclubAppServer.models.ResponseCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import TeaclubAppServer.output.Outputer;
import TeaclubAppServer.services.ShopCartService;

import java.util.Map;

@Controller
@RequestMapping("/shopCart")
@RequiredArgsConstructor
public class ShopCartController {
    private final Outputer outputer;
    private final ShopCartService shopCartService;

    @GetMapping
    public String getShopcarts(Map<String, Object> model) {
        try {
            model.put(
                    "shopCarts",
                    shopCartService.takeAllShopCarts(outputer::toShopCartResponseList)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(ResponseCodes.getMessageUnsuccessfullShopCartGet);
        }
        return String.valueOf(ResponseCodes.messageAllRight);
    }

    @PostMapping("{shopCartId}")
    public String getShopCart(
            @PathVariable long shopCartId,
            Map<String, Object> model
    ) {
        try {
            model.put(
                    "shopCart",
                    shopCartService.takeShopCartById(shopCartId, outputer::toShopCartResponse)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(ResponseCodes.getMessageUnsuccessfullShopCartIdGet);
        }
        return String.valueOf(ResponseCodes.messageAllRight);
    }
}