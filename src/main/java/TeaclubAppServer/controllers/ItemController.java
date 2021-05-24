package TeaclubAppServer.controllers;

import TeaclubAppServer.models.ResponseCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import TeaclubAppServer.output.ItemToShopCartRequest;
import TeaclubAppServer.services.ShopCartService;
import TeaclubAppServer.services.ItemService;

import javax.validation.Valid;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ShopCartService shopCartService;
    private final ItemService itemService;

    @PostMapping("add")
    public String add(
            @Valid @ModelAttribute("addItem") ItemToShopCartRequest itemToShopCartRequest,
            BindingResult result
    ) {
        try {
            if (!result.hasErrors()) {
                shopCartService.publish(itemToShopCartRequest);
                itemService.publish(itemToShopCartRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(ResponseCodes.messageUnsuccessfullAdd);
        }
        return String.valueOf(ResponseCodes.messageAllRight);
    }

    @PostMapping("{itemId}/delete")
    public String delete(@PathVariable long itemId) {
        try {
            itemService.delete(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(ResponseCodes.messageUnsuccessfullDel);
        }
        return String.valueOf(ResponseCodes.messageAllRight);
    }
}